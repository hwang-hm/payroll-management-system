package salary.ledger.viewdto;

// 급여대장 목록 한 행 (귀속연월·차수별 집계)
// 給与台帳一覧1行 (帰属年月・回数別集計)
public class SalaryLedgerRow {

	private int salaryDateId;
	private int payYear;
	private int payMonth;
	private int paySeq;
	private String startDate;
	private String endDate;
	private String payDate;
	private int empCount;
	private long totalPay;
	private long totalDeduct;
	private long netPay;

	public int getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(int salaryDateId) {
		this.salaryDateId = salaryDateId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public int getEmpCount() {
		return empCount;
	}

	public void setEmpCount(int empCount) {
		this.empCount = empCount;
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
}
