package employee.service;

import java.sql.Date;

public class RetirementRequest {
	private String retirementType;
	private Date retirementDate;
	private String retirementReason;
	private String contactAfterRetirement;
	private Integer retirementPay;
	
	public RetirementRequest() {
	}

	public RetirementRequest(String retirementType, Date retirementDate, String retirementReason,
			String contactAfterRetirement, Integer retirementPay) {
		super();
		this.retirementType = retirementType;
		this.retirementDate = retirementDate;
		this.retirementReason = retirementReason;
		this.contactAfterRetirement = contactAfterRetirement;
		this.retirementPay = retirementPay;
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

	public Integer getRetirementPay() {
		return retirementPay;
	}

	public void setRetirementPay(Integer retirementPay) {
		this.retirementPay = retirementPay;
	}
	
	
}
