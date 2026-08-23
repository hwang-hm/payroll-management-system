package salary.statics.service.request;

/**
 * 전체 연도별 급여 통계 DTO
 * 全体年度別給与統計DTO
 */
public class YearlySalaryDTO {

	// 귀속년도
	// 帰属年度
	private int year;

	// 연도 전체 급여액 (단위: 천 원)
	// 年度全体給与額（単位：千円）
	private long totalSalaryThousand;

	// 연평균 인원수 (단위: 명)
	// 年平均人数（単位：名）
	private double avgEmpCount;

	// 전년 대비 급여 증가율 (%)
	// 前年比給与増加率 (%)
	private Double salaryGrowthRate;

	// 전년 대비 인원 증가율 (%)
	// 前年比人数増加率 (%)
	private Double countGrowthRate;

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public YearlySalaryDTO() {
	}

	/**
	 * 필드 초기화 생성자
	 * フィールド初期化コンストラクタ
	 */
	public YearlySalaryDTO(int year, long totalSalaryThousand, double avgEmpCount) {
		this.year = year;
		this.totalSalaryThousand = totalSalaryThousand;
		this.avgEmpCount = avgEmpCount;
	}

	// 귀속년도 Getter / Setter
	// 帰属年度 Getter / Setter
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// 연도 전체 급여액 Getter / Setter
	// 年度全体給与額 Getter / Setter
	public long getTotalSalaryThousand() {
		return totalSalaryThousand;
	}

	public void setTotalSalaryThousand(long totalSalaryThousand) {
		this.totalSalaryThousand = totalSalaryThousand;
	}

	// 연평균 인원수 Getter / Setter
	// 年平均人数 Getter / Setter
	public double getAvgEmpCount() {
		return avgEmpCount;
	}

	public void setAvgEmpCount(double avgEmpCount) {
		this.avgEmpCount = avgEmpCount;
	}

	// 전년 대비 급여 증가율 Getter / Setter
	// 前年比給与増加率 Getter / Setter
	public Double getSalaryGrowthRate() {
		return salaryGrowthRate;
	}

	public void setSalaryGrowthRate(Double salaryGrowthRate) {
		this.salaryGrowthRate = salaryGrowthRate;
	}

	// 전년 대비 인원 증가율 Getter / Setter
	// 前年比人数増加率 Getter / Setter
	public Double getCountGrowthRate() {
		return countGrowthRate;
	}

	public void setCountGrowthRate(Double countGrowthRate) {
		this.countGrowthRate = countGrowthRate;
	}
}