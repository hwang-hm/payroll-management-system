package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class License {
	private Integer licenseId;
	private Integer empId;
	private String licenseName;
	private Date acquisitionDate;
	private String issuer;
	private String licenseNumber;
	private String remarks;
	
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public License() {
	}

	public License(Integer licenseId, Integer empId, String licenseName, Date acquisitionDate, String issuer,
			String licenseNumber, String remarks, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.licenseId = licenseId;
		this.empId = empId;
		this.licenseName = licenseName;
		this.acquisitionDate = acquisitionDate;
		this.issuer = issuer;
		this.licenseNumber = licenseNumber;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
