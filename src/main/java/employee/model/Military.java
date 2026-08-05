package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Military {
	private int empId;
	private String militaryType;
	private String militaryBranch;
	private Date startDate;
	private Date endDate;
	private String finalRank;
	private String specialty;
	private String exemptionReason;
	
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getMilitaryType() {
		return militaryType;
	}
	public void setMilitaryType(String militaryType) {
		this.militaryType = militaryType;
	}
	public String getMilitaryBranch() {
		return militaryBranch;
	}
	public void setMilitaryBranch(String militaryBranch) {
		this.militaryBranch = militaryBranch;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFinalRank() {
		return finalRank;
	}
	public void setFinalRank(String finalRank) {
		this.finalRank = finalRank;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getExemptionReason() {
		return exemptionReason;
	}
	public void setExemptionReason(String exemptionReason) {
		this.exemptionReason = exemptionReason;
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
