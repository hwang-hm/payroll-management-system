package salary.statics.service.request;

/**
 * 전체 월별 급여 통계 DTO
 * 全体月別給与統計DTO
 */
public class MonthlySalaryDTO {

	// 귀속월 (1~12)
	// 帰属月 (1〜12)
	private int month;

	// 월 전체 급여액 (단위: 천 원)
	// 月全体給与額（単位：千円）
	private long totalSalaryThousand;

	// 월 인원수 (단위: 명)
	// 月人数（単位：名）
	private double empCount;

	// 전월 대비 급여 증가율 (%)
	// 前月比給与増加率 (%)
	private Double salaryGrowthRate;

	// 전월 대비 인원 증가율 (%)
	// 前月比人数増加率 (%)
	private Double countGrowthRate;

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public MonthlySalaryDTO() {
	}

	/**
	 * 필드 초기화 생성자
	 * フィールド初期化コンストラクタ
	 */
	public MonthlySalaryDTO(int month, long totalSalaryThousand, double empCount) {
		this.month = month;
		this.totalSalaryThousand = totalSalaryThousand;
		this.empCount = empCount;
	}

	// 귀속월 Getter / Setter
	// 帰属月 Getter / Setter
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// 월 전체 급여액 Getter / Setter
	// 月全体給与額 Getter / Setter
	public long getTotalSalaryThousand() {
		return totalSalaryThousand;
	}

	public void setTotalSalaryThousand(long totalSalaryThousand) {
		this.totalSalaryThousand = totalSalaryThousand;
	}

	// 월 인원수 Getter / Setter
	// 月人数 Getter / Setter
	public double getEmpCount() {
		return empCount;
	}

	public void setEmpCount(double empCount) {
		this.empCount = empCount;
	}

	// 전월 대비 급여 증가율 Getter / Setter
	// 前月比給与増加率 Getter / Setter
	public Double getSalaryGrowthRate() {
		return salaryGrowthRate;
	}

	public void setSalaryGrowthRate(Double salaryGrowthRate) {
		this.salaryGrowthRate = salaryGrowthRate;
	}

	// 전월 대비 인원 증가율 Getter / Setter
	// 前月比人数増加率 Getter / Setter
	public Double getCountGrowthRate() {
		return countGrowthRate;
	}

	public void setCountGrowthRate(Double countGrowthRate) {
		this.countGrowthRate = countGrowthRate;
	}
}