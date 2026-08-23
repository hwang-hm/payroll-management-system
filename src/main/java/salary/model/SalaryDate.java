package salary.model;

import java.util.Date;

/**
 * 급여 정산일자 정보를 담는 Domain/Model 클래스입니다.
 * 給与精算日時情報を保持する Domain/Model クラスです。
 */
public class SalaryDate {

	private Integer salaryDateId; // 정산일자 ID (PK) / 精算日時ID (PK)
	private Integer payYear;      // 귀속연도 / 帰属年
	private Integer payMonth;     // 귀속월 / 帰属月
	private Integer paySeq;       // 급여차수 / 給与回数
	private Date startDate;       // 정산 시작일 / 精算開始日
	private Date endDate;         // 정산 종료일 / 精算終了日
	private Date payDate;         // 급여 지급일 / 給与支給日

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public SalaryDate() {
	}

	/**
	 * 전체 필드 생성자
	 * 全フィールドコンストラクタ
	 */
	public SalaryDate(Integer salaryDateId, Integer payYear, Integer payMonth, Integer paySeq, Date startDate,
			Date endDate, Date payDate) {
		this.salaryDateId = salaryDateId;
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.payDate = payDate;
	}

	// Getter 및 Setter 메소드
	// Getter および Setter メソッド

	public Integer getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(Integer salaryDateId) {
		this.salaryDateId = salaryDateId;
	}

	public Integer getPayYear() {
		return payYear;
	}

	public void setPayYear(Integer payYear) {
		this.payYear = payYear;
	}

	public Integer getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(Integer payMonth) {
		this.payMonth = payMonth;
	}

	public Integer getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(Integer paySeq) {
		this.paySeq = paySeq;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}