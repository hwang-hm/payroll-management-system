package salary.statics.service.request;

public class EmpYearlySalaryDTO {
	
	private int year; // 귀속년도
	private long totalPayThousand; // 연봉액 (총지급액, 천원)
	private long totalDeductThousand; // 공제금액 (천원)
	private long netPayThousand; // 실지급액 (천원)
	private Double salaryGrowthRate; // 연봉 증가율 (%)

	public EmpYearlySalaryDTO() {
	}

	public EmpYearlySalaryDTO(int year, long totalPayThousand, long totalDeductThousand, long netPayThousand) {
		this.year = year;
		this.totalPayThousand = totalPayThousand;
		this.totalDeductThousand = totalDeductThousand;
		this.netPayThousand = netPayThousand;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public Double getSalaryGrowthRate() {
		return salaryGrowthRate;
	}

	public void setSalaryGrowthRate(Double salaryGrowthRate) {
		this.salaryGrowthRate = salaryGrowthRate;
	}
}