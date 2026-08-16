package employee.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

public class NationalPensionInfo {
	private Integer empId;
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
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public NationalPensionInfo() {
	}

	public NationalPensionInfo(Integer empId, String nationalNo, Date nationalAcqDate, Date nationalLossDate,
			String healthNo, Date healthAcqDate, Date healthLossDate, String employmentNo, Date employmentAcqDate,
			Date employmentLossDate, String industrialNo, Date industrialAcqDate, Date industrialLossDate,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.empId = empId;
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
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
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
