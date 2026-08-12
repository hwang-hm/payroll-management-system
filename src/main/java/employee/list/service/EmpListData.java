package employee.list.service;

import java.sql.Date;

public class EmpListData {
	private int empId;
	private String nameKor;
	private Date hireDate;

	private String deptName;
	private String positionName;

	private Date retireDate;
	private String residentNo;
	private String status;
	private String mobileNum;
	private String email;
	private String employmentType;
	
	public EmpListData() {
	}

	

	public EmpListData(int empId, String nameKor, Date hireDate, String deptName, String positionName, Date retireDate,
			String residentNo, String status, String mobileNum, String email, String employmentType) {
		super();
		this.empId = empId;
		this.nameKor = nameKor;
		this.hireDate = hireDate;
		this.deptName = deptName;
		this.positionName = positionName;
		this.retireDate = retireDate;
		this.residentNo = residentNo;
		this.status = status;
		this.mobileNum = mobileNum;
		this.email = email;
		this.employmentType = employmentType;
	}



	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getResidentNo() {
		return residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	
	
}
