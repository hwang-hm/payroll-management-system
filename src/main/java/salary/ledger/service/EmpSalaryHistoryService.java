package salary.ledger.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.ledger.dao.EmpSalaryHistoryDao;
import salary.ledger.viewdto.EmpSalaryHistoryData;
import salary.ledger.viewdto.EmpSalaryHistoryDept;
import salary.ledger.viewdto.EmpSalaryHistoryEmployee;
import salary.ledger.viewdto.EmpSalaryHistoryRow;

public class EmpSalaryHistoryService {

	private final EmpSalaryHistoryDao empSalaryHistoryDao = new EmpSalaryHistoryDao();

	public EmpSalaryHistoryData getHistoryData(int startYear, int startMonth, int endYear, int endMonth, int empId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int[] period = normalizePeriod(startYear, startMonth, endYear, endMonth);

			EmpSalaryHistoryData data = new EmpSalaryHistoryData();
			data.setStartYear(period[0]);
			data.setStartMonth(period[1]);
			data.setEndYear(period[2]);
			data.setEndMonth(period[3]);
			data.setEmpId(empId);
			data.setYearOptions(buildYearOptions(period[0], period[2]));
			data.setDepartments(empSalaryHistoryDao.selectDepartments(conn));

			if (empId <= 0) {
				data.setEmpName("");
				data.setRows(new ArrayList<>());
				data.setSummary(emptySummary());
				return data;
			}

			String empName = empSalaryHistoryDao.selectEmpNameById(conn, empId);
			data.setEmpName(empName == null ? "" : empName);

			int startYm = period[0] * 100 + period[1];
			int endYm = period[2] * 100 + period[3];
			List<EmpSalaryHistoryRow> rows = empSalaryHistoryDao.selectHistoryByEmpAndPeriod(conn, empId, startYm,
					endYm);
			data.setRows(rows);
			data.setSummary(toSummary(rows));
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<EmpSalaryHistoryEmployee> searchEmployees(String keyword, Integer deptId, String status) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return empSalaryHistoryDao.selectEmployees(conn, keyword, deptId, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<EmpSalaryHistoryDept> getDepartments() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return empSalaryHistoryDao.selectDepartments(conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private int[] normalizePeriod(int startYear, int startMonth, int endYear, int endMonth) {
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH) + 1;

		if (startYear <= 0) {
			startYear = currentYear;
		}
		if (startMonth <= 0) {
			startMonth = 1;
		}
		if (endYear <= 0) {
			endYear = currentYear;
		}
		if (endMonth <= 0) {
			endMonth = currentMonth;
		}
		if (startMonth > 12) {
			startMonth = 12;
		}
		if (endMonth > 12) {
			endMonth = 12;
		}

		int startYm = startYear * 100 + startMonth;
		int endYm = endYear * 100 + endMonth;
		if (startYm > endYm) {
			return new int[] { endYear, endMonth, startYear, startMonth };
		}
		return new int[] { startYear, startMonth, endYear, endMonth };
	}

	private List<Integer> buildYearOptions(int startYear, int endYear) {
		List<Integer> years = new ArrayList<>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int max = Math.max(currentYear, Math.max(startYear, endYear));
		for (int y = max; y >= max - 4; y--) {
			years.add(y);
		}
		if (!years.contains(startYear)) {
			years.add(startYear);
		}
		if (!years.contains(endYear)) {
			years.add(endYear);
		}
		years.sort((a, b) -> Integer.compare(b, a));
		return years;
	}

	private EmpSalaryHistoryRow toSummary(List<EmpSalaryHistoryRow> rows) {
		EmpSalaryHistoryRow summary = emptySummary();
		for (EmpSalaryHistoryRow row : rows) {
			summary.setBasicPay(summary.getBasicPay() + row.getBasicPay());
			summary.setTotalPay(summary.getTotalPay() + row.getTotalPay());
			summary.setTotalDeduct(summary.getTotalDeduct() + row.getTotalDeduct());
			summary.setNetPay(summary.getNetPay() + row.getNetPay());
			summary.setNationalPension(summary.getNationalPension() + row.getNationalPension());
			summary.setHealthInsurance(summary.getHealthInsurance() + row.getHealthInsurance());
			summary.setLongTermCare(summary.getLongTermCare() + row.getLongTermCare());
			summary.setEmploymentInsurance(summary.getEmploymentInsurance() + row.getEmploymentInsurance());
			summary.setIncomeTax(summary.getIncomeTax() + row.getIncomeTax());
			summary.setResidentTax(summary.getResidentTax() + row.getResidentTax());
		}
		return summary;
	}

	private EmpSalaryHistoryRow emptySummary() {
		return new EmpSalaryHistoryRow();
	}
}
