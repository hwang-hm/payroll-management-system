package salary.statics.service.request;

/**
 * 개인별 연도별 급여 통계 DTO
 * 個人別年度別給与統計DTO
 */
public class EmpYearlySalaryDTO {

	// 귀속년도
	// 帰属年度
	private int year;

	// 연봉액 (총지급액, 단위: 천 원)
	// 年収額（支給総額、単位：千円）
	private long totalPayThousand;

	// 공제금액 (단위: 천 원)
	// 控除金額（単位：千円）
	private long totalDeductThousand;

	// 실지급액 (단위: 천 원)
	// 手取り額（単位：千円）
	private long netPayThousand;

	// 연봉 증가율 (%)
	// 年収増加率 (%)
	private Double salaryGrowthRate;

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public EmpYearlySalaryDTO() {
	}

	/**
	 * 필드 초기화 생성자
	 * フィールド初期化コンストラクタ
	 */
	public EmpYearlySalaryDTO(int year, long totalPayThousand, long totalDeductThousand, long netPayThousand) {
		this.year = year;
		this.totalPayThousand = totalPayThousand;
		this.totalDeductThousand = totalDeductThousand;
		this.netPayThousand = netPayThousand;
	}

	// 귀속년도 Getter / Setter
	// 帰属年度 Getter / Setter
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// 연봉액 Getter / Setter
	// 年収額 Getter / Setter
	public long getTotalPayThousand() {
		return totalPayThousand;
	}

	public void setTotalPayThousand(long totalPayThousand) {
		this.totalPayThousand = totalPayThousand;
	}

	// 공제금액 Getter / Setter
	// 控除金額 Getter / Setter
	public long getTotalDeductThousand() {
		return totalDeductThousand;
	}

	public void setTotalDeductThousand(long totalDeductThousand) {
		this.totalDeductThousand = totalDeductThousand;
	}

	// 실지급액 Getter / Setter
	// 手取り額 Getter / Setter
	public long getNetPayThousand() {
		return netPayThousand;
	}

	public void setNetPayThousand(long netPayThousand) {
		this.netPayThousand = netPayThousand;
	}

	// 연봉 증가율 Getter / Setter
	// 年収増加率 Getter / Setter
	public Double getSalaryGrowthRate() {
		return salaryGrowthRate;
	}

	public void setSalaryGrowthRate(Double salaryGrowthRate) {
		this.salaryGrowthRate = salaryGrowthRate;
	}
}