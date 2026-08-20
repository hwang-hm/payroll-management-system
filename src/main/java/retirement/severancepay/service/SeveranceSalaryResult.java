package retirement.severancepay.service;

import java.util.List;

public class SeveranceSalaryResult {
	private List<SeveranceSalaryData> salaryList;
    private int totalCalculationDays;
    private long salaryTotal;

    public SeveranceSalaryResult(
            List<SeveranceSalaryData> salaryList,
            int totalCalculationDays,
            long salaryTotal) {

        this.salaryList = salaryList;
        this.totalCalculationDays = totalCalculationDays;
        this.salaryTotal = salaryTotal;
    }

	public List<SeveranceSalaryData> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<SeveranceSalaryData> salaryList) {
		this.salaryList = salaryList;
	}

	public int getTotalCalculationDays() {
		return totalCalculationDays;
	}

	public void setTotalCalculationDays(int totalCalculationDays) {
		this.totalCalculationDays = totalCalculationDays;
	}

	public long getSalaryTotal() {
		return salaryTotal;
	}

	public void setSalaryTotal(long salaryTotal) {
		this.salaryTotal = salaryTotal;
	}

    
}
