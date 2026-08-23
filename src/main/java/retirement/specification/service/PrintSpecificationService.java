package retirement.specification.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import employee.dao.EmpDao;
import employee.dto.EmployeeDetailData;
import jdbc.connection.ConnectionProvider;
import retirement.dao.OtherTaxableIncomeDao;
import retirement.dao.SeverancePayDao;
import retirement.model.OtherTaxableIncome;
import retirement.model.SeverancePay;
import retirement.severancepay.service.SeveranceCalculateService;
import retirement.severancepay.service.SeveranceSalaryData;
import retirement.severancepay.service.SeveranceSalaryResult;

public class PrintSpecificationService {
	private SeverancePay severancePay = new SeverancePay();
	private EmpDao empDao = new EmpDao();
	private SeverancePayDao severancePayDao = new SeverancePayDao();
	private OtherTaxableIncomeDao otherTaxableIncomeDao = new OtherTaxableIncomeDao();
	
	private SeveranceCalculateService severanceCalculateService = new SeveranceCalculateService();

	public PrintSpecificationData getSpecificationData(int empId) {

		EmployeeDetailData emp = selectEmp(empId);
		SeveranceSalaryResult salaryResult = severanceCalculateService.loadSalary(empId);
		List<SpecificationSalaryData> salaryList = new ArrayList<>();

		for (SeveranceSalaryData salary : salaryResult.getSalaryList()) {
			SpecificationSalaryData data = new SpecificationSalaryData(salary.getPayYear(), salary.getPayMonth(),
					salary.getCalculationStartDate(), salary.getCalculationEndDate(), salary.getCalculationDays(),
					salary.getCalculatedPay());

			salaryList.add(data);
		}

		SeverancePay severancePay = selectSeverancePay(empId);
		List<OtherTaxableIncome> otherIncomeList = selectOtherTaxableIncome(empId);

		int totalCalculationDays = salaryResult.getTotalCalculationDays();
		long totalSalary = salaryResult.getSalaryTotal();
		long totalOtherIncomeThreeMonths = 0;
		if (otherIncomeList != null) {

			for (OtherTaxableIncome income : otherIncomeList) {

				if (income.getAmount() != null) {
					totalOtherIncomeThreeMonths += income.getAmount() * 3 / 12;
				}
			}
		}

		long threeMonthTotal = totalSalary + totalOtherIncomeThreeMonths;
		long dailyAverageWage = totalCalculationDays > 0 ? threeMonthTotal / totalCalculationDays : 0;
		long totalDeduction = 0;

		if (severancePay != null) {
			long retirementIncomeTax = severancePay.getRetirementIncomeTax() != null
					? severancePay.getRetirementIncomeTax()
					: 0;

			long localIncomeTax = severancePay.getLocalIncomeTax() != null ? severancePay.getLocalIncomeTax() : 0;
			long otherDeduction = severancePay.getOtherDeduction() != null ? severancePay.getOtherDeduction() : 0;
			totalDeduction = retirementIncomeTax + localIncomeTax + otherDeduction;
		}

		return new PrintSpecificationData(emp, salaryList, totalCalculationDays, totalSalary, otherIncomeList,
				totalOtherIncomeThreeMonths, dailyAverageWage, severancePay, totalDeduction);
	}

	public EmployeeDetailData selectEmp(int empId) {

		try (Connection conn = ConnectionProvider.getConnection()) {
			return empDao.selectByIdJoin(conn, empId);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public SeverancePay selectSeverancePay(int empId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return severancePayDao.selectById(conn, empId);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<OtherTaxableIncome> selectOtherTaxableIncome(int empId) {

		try (Connection conn = ConnectionProvider.getConnection()) {

			SeverancePay severancePay = severancePayDao.selectById(conn, empId);
			if (severancePay == null) {
				return new ArrayList<>();
			}

			int severancePayId = severancePay.getSeverancePayId();

			return otherTaxableIncomeDao.selectById(conn, severancePayId);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
