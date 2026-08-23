package retirement.specification.service;

import java.sql.Date;

public class SpecificationSalaryData {

    private int payYear;
    private int payMonth;

    private Date startDate;
    private Date endDate;

    private int calculationDays;
    private long calculatedPay;

    public SpecificationSalaryData(
            int payYear,
            int payMonth,
            Date startDate,
            Date endDate,
            int calculationDays,
            long calculatedPay) {

        this.payYear = payYear;
        this.payMonth = payMonth;
        this.startDate = startDate;
        this.endDate = endDate;
        this.calculationDays = calculationDays;
        this.calculatedPay = calculatedPay;
    }

	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCalculationDays() {
		return calculationDays;
	}

	public void setCalculationDays(int calculationDays) {
		this.calculationDays = calculationDays;
	}

	public long getCalculatedPay() {
		return calculatedPay;
	}

	public void setCalculatedPay(long calculatedPay) {
		this.calculatedPay = calculatedPay;
	}

   
}
