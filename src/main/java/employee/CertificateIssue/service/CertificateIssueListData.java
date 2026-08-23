package employee.CertificateIssue.service;

import java.sql.Date;

public class CertificateIssueListData {
	private int certificateIssueId;
    private int empId;
    private String certificateType;
    private String purpose;
    private String issueDepartment;
    private String nameKor;
    private String deptName;
    private String positionName;
    private String employmentType;
    private Date issueDate;
	
    public CertificateIssueListData() {
	}

	public CertificateIssueListData(int certificateIssueId, int empId, String certificateType, String purpose,
			String issueDepartment, String nameKor, String deptName, String positionName, String employmentType,
			Date issueDate) {
		super();
		this.certificateIssueId = certificateIssueId;
		this.empId = empId;
		this.certificateType = certificateType;
		this.purpose = purpose;
		this.issueDepartment = issueDepartment;
		this.nameKor = nameKor;
		this.deptName = deptName;
		this.positionName = positionName;
		this.employmentType = employmentType;
		this.issueDate = issueDate;
	}

	public int getCertificateIssueId() {
		return certificateIssueId;
	}

	public void setCertificateIssueId(int certificateIssueId) {
		this.certificateIssueId = certificateIssueId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getIssueDepartment() {
		return issueDepartment;
	}

	public void setIssueDepartment(String issueDepartment) {
		this.issueDepartment = issueDepartment;
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

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

}
