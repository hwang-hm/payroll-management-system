package retirement.severancepay.service;

import java.sql.Date;

public class SeveranceSalaryData {
	 private int salaryId;
	    private int salaryDateId;
	    private int empId;
	    private long totalPay;

	    private int payYear;
	    private int payMonth;

	    private Date startDate;
	    private Date endDate;
	    
	    private Date calculationStartDate;   // 실제 산정 시작일
	    private Date calculationEndDate;     // 실제 산정 종료일
	    private int calculationDays;         // 실제 산정일수
	    private long calculatedPay;           // 산정기간에 반영할 급여액

	    

		public SeveranceSalaryData() {
		}
		
		

		public SeveranceSalaryData(int salaryId, int salaryDateId, int empId, long totalPay, int payYear, int payMonth,
				Date startDate, Date endDate) {
			super();
			this.salaryId = salaryId;
			this.salaryDateId = salaryDateId;
			this.empId = empId;
			this.totalPay = totalPay;
			this.payYear = payYear;
			this.payMonth = payMonth;
			this.startDate = startDate;
			this.endDate = endDate;
		}



		public int getSalaryId() {
			return salaryId;
		}

		public void setSalaryId(int salaryId) {
			this.salaryId = salaryId;
		}

		public int getSalaryDateId() {
			return salaryDateId;
		}

		public void setSalaryDateId(int salaryDateId) {
			this.salaryDateId = salaryDateId;
		}

		public int getEmpId() {
			return empId;
		}

		public void setEmpId(int empId) {
			this.empId = empId;
		}

		public long getTotalPay() {
			return totalPay;
		}

		public void setTotalPay(long totalPay) {
			this.totalPay = totalPay;
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

		public Date getCalculationStartDate() {
			return calculationStartDate;
		}

		public void setCalculationStartDate(Date calculationStartDate) {
			this.calculationStartDate = calculationStartDate;
		}

		public Date getCalculationEndDate() {
			return calculationEndDate;
		}

		public void setCalculationEndDate(Date calculationEndDate) {
			this.calculationEndDate = calculationEndDate;
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

		public void setCalculatedPay(long calculatedPay2) {
			this.calculatedPay = calculatedPay2;
		}

	  
	    
	    
}
