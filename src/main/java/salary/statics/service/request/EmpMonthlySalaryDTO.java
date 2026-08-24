package salary.statics.service.request;

/**
 * 개인별 월별 급여 통계 DTO
 * 個人別月別給与統計DTO
 */
public class EmpMonthlySalaryDTO {

	// 귀속월 (1~12)
	// 帰属月 (1〜12)
	private int month;

	// 월 총 지급액 (단위: 천 원)
	// 月支給総額（単位：千円）
	private long totalPayThousand;

	// 월 총 공제액 (단위: 천 원)
	// 月控除総額（単位：千円）
	private long totalDeductThousand;

	// 월 실지급액 (단위: 천 원)
	// 月手取り額（単位：千円）
	private long netPayThousand;

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public EmpMonthlySalaryDTO() {
	}

	/**
	 * 필드 초기화 생성자
	 * フィールド初期化コンストラクタ
	 */
	public EmpMonthlySalaryDTO(int month, long totalPayThousand, long totalDeductThousand, long netPayThousand) {
		this.month = month;
		this.totalPayThousand = totalPayThousand;
		this.totalDeductThousand = totalDeductThousand;
		this.netPayThousand = netPayThousand;
	}

	// 귀속월 Getter / Setter
	// 帰属月 Getter / Setter
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// 월 총 지급액 Getter / Setter
	// 月支給総額 Getter / Setter
	public long getTotalPayThousand() {
		return totalPayThousand;
	}

	public void setTotalPayThousand(long totalPayThousand) {
		this.totalPayThousand = totalPayThousand;
	}

	// 월 총 공제액 Getter / Setter
	// 月控除総額 Getter / Setter
	public long getTotalDeductThousand() {
		return totalDeductThousand;
	}

	public void setTotalDeductThousand(long totalDeductThousand) {
		this.totalDeductThousand = totalDeductThousand;
	}

	// 월 실지급액 Getter / Setter
	// 月手取り額 Getter / Setter
	public long getNetPayThousand() {
		return netPayThousand;
	}

	public void setNetPayThousand(long netPayThousand) {
		this.netPayThousand = netPayThousand;
	}
}