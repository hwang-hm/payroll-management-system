package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Retirement {
	private int empId;
	private String retirementType;
	private Date retirementDate;
	private String retirementReason;
	private String contactAfterRetirement;
	private Integer retirementPay;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
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
	public Integer getRetirementPay() {
		return retirementPay;
	}
	public void setRetirementPay(Integer retirementPay) {
		this.retirementPay = retirementPay;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}
