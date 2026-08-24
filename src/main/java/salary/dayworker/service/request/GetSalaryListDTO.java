package salary.dayworker.service.request;

/**
 * 일용직 급여 목록 조회 결과를 전달하기 위한 DTO 클래스입니다.
 * 日雇い給与リスト照会結果を伝達するためのDTOクラスです。
 */
public class GetSalaryListDTO {

	// 급여 식별 ID / 給与識別ID
	private Integer salaryId;

	// 급여 지급일 ID / 給与支給日ID
	private Integer salaryDateId;

	// 사원 ID / 社員ID
	private Integer empId;

	// 총 지급액 / 総支給額
	private Long totalPay;

	// 총 공제액 / 総控除額
	private Long totalDeduct;

	// 실수령액 / 手取り額
	private Long netPay;

	// 사원명(한글) / 社員名(韓国語)
	private String nameKor;

	// 고용 형태 / 雇用形態
	private String employmentType;

	// 부서명 / 部署名
	private String deptName;

	/**
	 * 기본 생성자 基本コンストラクタ
	 */
	public GetSalaryListDTO() {
	}

	/**
	 * 전체 필드 초기화 생성자 全フィールド初期化コンストラクタ
	 */
	public GetSalaryListDTO(Integer salaryId, Integer salaryDateId, Integer empId, Long totalPay, Long totalDeduct,
			Long netPay, String nameKor, String employmentType, String deptName) {
		this.salaryId = salaryId;
		this.salaryDateId = salaryDateId;
		this.empId = empId;
		this.totalPay = totalPay;
		this.totalDeduct = totalDeduct;
		this.netPay = netPay;
		this.nameKor = nameKor;
		this.employmentType = employmentType;
		this.deptName = deptName;
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

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

}