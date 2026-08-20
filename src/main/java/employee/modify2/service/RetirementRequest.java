package employee.modify2.service;

import java.sql.Date;

public class RetirementRequest {
	private Integer empId;
    private String retirementType;
    private Date retirementDate;
    private String retirementReason;
    private String contactAfterRetirement;
    

    public RetirementRequest() {
    }

    public RetirementRequest(Integer empId, String retirementType, Date retirementDate, String retirementReason,
            String contactAfterRetirement) {
        this.empId = empId;
        this.retirementType = retirementType;
        this.retirementDate = retirementDate;
        this.retirementReason = retirementReason;
        this.contactAfterRetirement = contactAfterRetirement;
    }

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRetirementType() {
		return retirementType;
	}

	public void setRetirementType(String retirementType) {
		this.retirementType = retirementType;
	}

	public Date getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}

	public String getRetirementReason() {
		return retirementReason;
	}

	public void setRetirementReason(String retirementReason) {
		this.retirementReason = retirementReason;
	}

	public String getContactAfterRetirement() {
		return contactAfterRetirement;
	}

	public void setContactAfterRetirement(String contactAfterRetirement) {
		this.contactAfterRetirement = contactAfterRetirement;
	}
}
