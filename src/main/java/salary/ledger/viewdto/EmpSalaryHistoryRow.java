package salary.ledger.viewdto;

// 사원별 급여내역 한 줄 (월·차수별)
// 社員別給与明細1行 (月・回数別)
public class EmpSalaryHistoryRow {

	private int salaryId;
	private int payYear;
	private int payMonth;
	private int paySeq;
	private long basicPay;
	private long totalPay;
	private long totalDeduct;
	private long netPay;
	private long nationalPension;
	private long healthInsurance;
	private long longTermCare;
	private long employmentInsurance;
	private long incomeTax;
	private long residentTax;

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
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

	public int getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(int paySeq) {
		this.paySeq = paySeq;
	}

	public long getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(long basicPay) {
		this.basicPay = basicPay;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}

	public long getNationalPension() {
		return nationalPension;
	}

	public void setNationalPension(long nationalPension) {
		this.nationalPension = nationalPension;
	}

	public long getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(long healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public long getLongTermCare() {
		return longTermCare;
	}

	public void setLongTermCare(long longTermCare) {
		this.longTermCare = longTermCare;
	}

	public long getEmploymentInsurance() {
		return employmentInsurance;
	}

	public void setEmploymentInsurance(long employmentInsurance) {
		this.employmentInsurance = employmentInsurance;
	}

	public long getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(long incomeTax) {
		this.incomeTax = incomeTax;
	}

	public long getResidentTax() {
		return residentTax;
	}

	public void setResidentTax(long residentTax) {
		this.residentTax = residentTax;
	}
}
