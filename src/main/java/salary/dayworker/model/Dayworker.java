package salary.dayworker.model;

/**
 * 일용직 근로자 정보를 담는 도메인 모델 (VO/DTO)
 * 日雇い労働者情報 ドメインモデル (VO/DTO)
 */
public class Dayworker {

	private String employmentType; // 고용 형태 / 雇用形態
	private Integer empId; // 사번 / 社員番号
	private String nameKor; // 성명(한글) / 氏名(韓国語)
	private Integer deptId; // 부서 ID / 部署ID
	private String deptName; // 부서명 / 部署名
	private Integer positionId; // 직급 ID / 役職ID
	private String positionName; // 직급명 / 役職名
	private String status; // 재직 상태 / 在職ステータス

	// 기본 생성자 / コンストラクタ
	public Dayworker() {
	}

	// 필드 전체 생성자 / 全フィールドコンストラクタ
	public Dayworker(String employmentType, Integer empId, String nameKor, Integer deptId, String deptName,
			Integer positionId, String positionName, String status) {
		this.employmentType = employmentType;
		this.empId = empId;
		this.nameKor = nameKor;
		this.deptId = deptId;
		this.deptName = deptName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.status = status;
	}

	// Getter & Setter 메서드 / Getter & Setter メソッド
	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
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

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}