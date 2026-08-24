package salary.model;

import java.util.Date;

/**
 * 급여(SALARY) 테이블의 도메인 모델 클래스입니다.
 * 給与(SALARY)テーブルのドメインモデルクラスです。
 */
public class Salary {

	// 급여 식별 ID (PK) / 給与識別ID (PK)
	private Integer salaryId;
	
	// 급여 지급일 ID (FK) / 給与支給日ID (FK)
	private Integer salaryDateId;
	
	// 사원 ID (FK) / 社員ID (FK)
	private Integer empId;
	
	// 총 지급액 / 총支給額
	private Long totalPay;
	
	// 총 공제액 / 総控除額
	private Long totalDeduct;
	
	// 실수령액 / 手取り額
	private Long netPay;
	
	// 데이터 생성 일시 / データ作成日時
	private Date createdAt;
	
	// 데이터 수정 일시 / 데이터更新日時
	private Date updatedAt;

	/**
	 * 기본 생성자
	 * 基本コンストラクタ
	 */
	public Salary() {
	}

	/**
	 * 전체 필드 초기화 생성자
	 * 全フィールド初期化コンストラクタ
	 */
	public Salary(Integer salaryId, Integer salaryDateId, Integer empId, Long totalPay, Long totalDeduct, Long netPay,
			Date createdAt, Date updatedAt) {
		this.salaryId = salaryId;
		this.salaryDateId = salaryDateId;
		this.empId = empId;
		this.totalPay = totalPay;
		this.totalDeduct = totalDeduct;
		this.netPay = netPay;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getter & Setter 메소드
	// Getter & Setter メソッド

	public Integer getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Integer salaryId) {
		this.salaryId = salaryId;
	}

	public Integer getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(Integer salaryDateId) {
		this.salaryDateId = salaryDateId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Long totalPay) {
		this.totalPay = totalPay;
	}

	public Long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(Long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public Long getNetPay() {
		return netPay;
	}

	public void setNetPay(Long netPay) {
		this.netPay = netPay;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}