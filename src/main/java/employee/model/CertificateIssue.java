package employee.model;

import java.sql.Date;

public class CertificateIssue {
	
	private int certificateIssueId;
    private int empId;
    private String certificateType;
    private String purpose;
    private String issueDepartment;
    private Date issueDate;
	
    public CertificateIssue() {
	}

	public CertificateIssue(int certificateIssueId, int empId, String certificateType, String purpose,
			String issueDepartment, Date issueDate) {
		super();
		this.certificateIssueId = certificateIssueId;
		this.empId = empId;
		this.certificateType = certificateType;
		this.purpose = purpose;
		this.issueDepartment = issueDepartment;
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
    
    
}
