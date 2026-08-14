package salary.dayworker.model;

public class Dayworker {
	private String employmentType;
	private Integer empId;
	private String nameKor;
	private Integer deptId;
	private String deptName; // [추가] 부서명
	private Integer positionId;
	private String positionName; // [추가] 직급명
	private String status;

	public Dayworker() {
	}

	public Dayworker(String employmentType, Integer empId, String nameKor, Integer deptId, String deptName,
			Integer positionId, String positionName, String status) {
		super();
		this.employmentType = employmentType;
		this.empId = empId;
		this.nameKor = nameKor;
		this.deptId = deptId;
		this.deptName = deptName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.status = status;
	}

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
