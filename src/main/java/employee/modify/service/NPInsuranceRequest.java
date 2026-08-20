package employee.modify.service;

import java.sql.Date;

public class NPInsuranceRequest {
	private String nationalNo;
	private Date nationalAcqDate;
	private Date nationalLossDate;
	private String healthNo;
	private Date healthAcqDate;
	private Date healthLossDate;
	private String employmentNo;
	private Date employmentAcqDate;
	private Date employmentLossDate;
	private String industrialNo;
	private Date industrialAcqDate;
	private Date industrialLossDate;
	
	public NPInsuranceRequest() { 
		
	}

	public NPInsuranceRequest(String nationalNo, Date nationalAcqDate, Date nationalLossDate, String healthNo,
			Date healthAcqDate, Date healthLossDate, String employmentNo, Date employmentAcqDate,
			Date employmentLossDate, String industrialNo, Date industrialAcqDate, Date industrialLossDate) {
		super();
		this.nationalNo = nationalNo;
		this.nationalAcqDate = nationalAcqDate;
		this.nationalLossDate = nationalLossDate;
		this.healthNo = healthNo;
		this.healthAcqDate = healthAcqDate;
		this.healthLossDate = healthLossDate;
		this.employmentNo = employmentNo;
		this.employmentAcqDate = employmentAcqDate;
		this.employmentLossDate = employmentLossDate;
		this.industrialNo = industrialNo;
		this.industrialAcqDate = industrialAcqDate;
		this.industrialLossDate = industrialLossDate;
	}

	public String getNationalNo() {
		return nationalNo;
	}

	public void setNationalNo(String nationalNo) {
		this.nationalNo = nationalNo;
	}

	public Date getNationalAcqDate() {
		return nationalAcqDate;
	}

	public void setNationalAcqDate(Date nationalAcqDate) {
		this.nationalAcqDate = nationalAcqDate;
	}

	public Date getNationalLossDate() {
		return nationalLossDate;
	}

	public void setNationalLossDate(Date nationalLossDate) {
		this.nationalLossDate = nationalLossDate;
	}

	public String getHealthNo() {
		return healthNo;
	}

	public void setHealthNo(String healthNo) {
		this.healthNo = healthNo;
	}

	public Date getHealthAcqDate() {
		return healthAcqDate;
	}

	public void setHealthAcqDate(Date healthAcqDate) {
		this.healthAcqDate = healthAcqDate;
	}

	public Date getHealthLossDate() {
		return healthLossDate;
	}

	public void setHealthLossDate(Date healthLossDate) {
		this.healthLossDate = healthLossDate;
	}

	public String getEmploymentNo() {
		return employmentNo;
	}

	public void setEmploymentNo(String employmentNo) {
		this.employmentNo = employmentNo;
	}

	public Date getEmploymentAcqDate() {
		return employmentAcqDate;
	}

	public void setEmploymentAcqDate(Date employmentAcqDate) {
		this.employmentAcqDate = employmentAcqDate;
	}

	public Date getEmploymentLossDate() {
		return employmentLossDate;
	}

	public void setEmploymentLossDate(Date employmentLossDate) {
		this.employmentLossDate = employmentLossDate;
	}

	public String getIndustrialNo() {
		return industrialNo;
	}

	public void setIndustrialNo(String industrialNo) {
		this.industrialNo = industrialNo;
	}

	public Date getIndustrialAcqDate() {
		return industrialAcqDate;
	}

	public void setIndustrialAcqDate(Date industrialAcqDate) {
		this.industrialAcqDate = industrialAcqDate;
	}

	public Date getIndustrialLossDate() {
		return industrialLossDate;
	}

	public void setIndustrialLossDate(Date industrialLossDate) {
		this.industrialLossDate = industrialLossDate;
	}
	
	
	
	
}
