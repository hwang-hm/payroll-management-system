package salary.statics.service.request;

public class EmpMonthlySalaryDTO {

	private int month; // 귀속월 (1~12)
	private long totalPayThousand; // 월급여액 (총지급액, 천원)
	private long totalDeductThousand; // 공제액 (천원)
	private long netPayThousand; // 실지급액 (천원)

	public EmpMonthlySalaryDTO() {
	}

	public EmpMonthlySalaryDTO(int month, long totalPayThousand, long totalDeductThousand, long netPayThousand) {
		this.month = month;
		this.totalPayThousand = totalPayThousand;
		this.totalDeductThousand = totalDeductThousand;
		this.netPayThousand = netPayThousand;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public long getTotalPayThousand() {
		return totalPayThousand;
	}

	public void setTotalPayThousand(long totalPayThousand) {
		this.totalPayThousand = totalPayThousand;
	}

	public long getTotalDeductThousand() {
		return totalDeductThousand;
	}

	public void setTotalDeductThousand(long totalDeductThousand) {
		this.totalDeductThousand = totalDeductThousand;
	}

	public long getNetPayThousand() {
		return netPayThousand;
	}

	public void setNetPayThousand(long netPayThousand) {
		this.netPayThousand = netPayThousand;
	}
}