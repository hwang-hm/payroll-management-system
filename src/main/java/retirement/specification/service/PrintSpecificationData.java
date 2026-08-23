package retirement.specification.service;

import java.util.List;

import employee.dto.EmployeeDetailData;
import retirement.model.OtherTaxableIncome;
import retirement.model.SeverancePay;

public class PrintSpecificationData {

    private EmployeeDetailData emp;
    private List<SpecificationSalaryData> salaryList;
    private int totalCalculationDays;
    private long totalSalary;
    private List<OtherTaxableIncome> otherTaxableIncome;
    private long totalOtherIncomeThreeMonths;
    private long dailyAverageWage;
    private SeverancePay severancePay;
    private long totalDeduction;
	
    public PrintSpecificationData() {
	}

	public PrintSpecificationData(EmployeeDetailData emp, List<SpecificationSalaryData> salaryList,
			int totalCalculationDays, long totalSalary, List<OtherTaxableIncome> otherTaxableIncome,
			long totalOtherIncomeThreeMonths, long dailyAverageWage, SeverancePay severancePay, long totalDeduction) {
		
		this.emp = emp;
		this.salaryList = salaryList;
		this.totalCalculationDays = totalCalculationDays;
		this.totalSalary = totalSalary;
		this.otherTaxableIncome = otherTaxableIncome;
		this.totalOtherIncomeThreeMonths = totalOtherIncomeThreeMonths;
		this.dailyAverageWage = dailyAverageWage;
		this.severancePay = severancePay;
		this.totalDeduction = totalDeduction;
	}

	public EmployeeDetailData getEmp() {
		return emp;
	}

	public void setEmp(EmployeeDetailData emp) {
		this.emp = emp;
	}

	public List<SpecificationSalaryData> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<SpecificationSalaryData> salaryList) {
		this.salaryList = salaryList;
	}

	public int getTotalCalculationDays() {
		return totalCalculationDays;
	}

	public void setTotalCalculationDays(int totalCalculationDays) {
		this.totalCalculationDays = totalCalculationDays;
	}

	public long getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(long totalSalary) {
		this.totalSalary = totalSalary;
	}

	public List<OtherTaxableIncome> getOtherTaxableIncome() {
		return otherTaxableIncome;
	}

	public void setOtherTaxableIncome(List<OtherTaxableIncome> otherTaxableIncome) {
		this.otherTaxableIncome = otherTaxableIncome;
	}

	public long getTotalOtherIncomeThreeMonths() {
		return totalOtherIncomeThreeMonths;
	}

	public void setTotalOtherIncomeThreeMonths(long totalOtherIncomeThreeMonths) {
		this.totalOtherIncomeThreeMonths = totalOtherIncomeThreeMonths;
	}

	public long getDailyAverageWage() {
		return dailyAverageWage;
	}

	public void setDailyAverageWage(long dailyAverageWage) {
		this.dailyAverageWage = dailyAverageWage;
	}

	public SeverancePay getSeverancePay() {
		return severancePay;
	}

	public void setSeverancePay(SeverancePay severancePay) {
		this.severancePay = severancePay;
	}

	public long getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(long totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
    
}
