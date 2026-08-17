package attendance.model;

/**
 * 일용직 사원 목록 화면에 필요한 정보를 저장하는 클래스
 * 日雇い社員リスト画面に必要な情報を保存するクラス
 */
public class DailyWorkerInfo {

	private int empId;
	private String employmentType;
	private String nameKor;
	private String deptName;
	private String status;

	public DailyWorkerInfo() {
	}

	public DailyWorkerInfo(int empId, String employmentType,
			String nameKor, String deptName, String status) {
		this.empId = empId;
		this.employmentType = employmentType;
		this.nameKor = nameKor;
		this.deptName = deptName;
		this.status = status;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DailyWorkerInfo [empId=" + empId
				+ ", employmentType=" + employmentType
				+ ", nameKor=" + nameKor
				+ ", deptName=" + deptName
				+ ", status=" + status + "]";
	}
}
