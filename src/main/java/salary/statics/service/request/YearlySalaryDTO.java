package salary.statics.service.request;

public class YearlySalaryDTO {
	private int year; // 귀속년도
	private long totalSalaryThousand; // 전체 급여액 (천원 단위)
	private double avgEmpCount; // 인원 (명)
	private Double salaryGrowthRate; // 급여 증가율 (%)
	private Double countGrowthRate; // 인원 증가율 (%)

	public YearlySalaryDTO() {
	}

	public YearlySalaryDTO(int year, long totalSalaryThousand, double avgEmpCount) {
		this.year = year;
		this.totalSalaryThousand = totalSalaryThousand;
		this.avgEmpCount = avgEmpCount;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getTotalSalaryThousand() {
		return totalSalaryThousand;
	}

	public void setTotalSalaryThousand(long totalSalaryThousand) {
		this.totalSalaryThousand = totalSalaryThousand;
	}

	public double getAvgEmpCount() {
		return avgEmpCount;
	}

	public void setAvgEmpCount(double avgEmpCount) {
		this.avgEmpCount = avgEmpCount;
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