package salary.statics.service.request;

public class MonthlySalaryDTO {
	private int month; // 귀속월 (1~12)
	private long totalSalaryThousand; // 전체 급여액 (천원)
	private double empCount; // 인원 (명)
	private Double salaryGrowthRate; // 전월 대비 급여 증가율 (%)
	private Double countGrowthRate; // 전월 대비 인원 증가율 (%)

	public MonthlySalaryDTO() {
	}

	public MonthlySalaryDTO(int month, long totalSalaryThousand, double empCount) {
		this.month = month;
		this.totalSalaryThousand = totalSalaryThousand;
		this.empCount = empCount;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public long getTotalSalaryThousand() {
		return totalSalaryThousand;
	}

	public void setTotalSalaryThousand(long totalSalaryThousand) {
		this.totalSalaryThousand = totalSalaryThousand;
	}

	public double getEmpCount() {
		return empCount;
	}

	public void setEmpCount(double empCount) {
		this.empCount = empCount;
	}

	public Double getSalaryGrowthRate() {
		return salaryGrowthRate;
	}

	public void setSalaryGrowthRate(Double salaryGrowthRate) {
		this.salaryGrowthRate = salaryGrowthRate;
	}

	public Double getCountGrowthRate() {
		return countGrowthRate;
	}

	public void setCountGrowthRate(Double countGrowthRate) {
		this.countGrowthRate = countGrowthRate;
	}
}