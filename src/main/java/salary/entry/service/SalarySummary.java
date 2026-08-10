package salary.entry.service;

//급여입력/관리 페이지 아랫부분의 년,월별 급여합계 view용 데이터
//SalaryEntryData에 넣어줌
public class SalarySummary {

	private int count;
	private long totalPay;
	private long totalDeduct;
	private long netPay;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
