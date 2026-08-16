package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class GuaranteeInsurance {
	private Integer empId;
	private String insuranceCompany;
	private String insuranceNumber;
	private Integer insuranceAmount;
	private Date joinDate;
	private Date expirationDate;
	private String remarks;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	public GuaranteeInsurance() {
	}

	public GuaranteeInsurance(Integer empId, String insuranceCompany, String insuranceNumber, Integer insuranceAmount,
			Date joinDate, Date expirationDate, String remarks, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.empId = empId;
		this.insuranceCompany = insuranceCompany;
		this.insuranceNumber = insuranceNumber;
		this.insuranceAmount = insuranceAmount;
		this.joinDate = joinDate;
		this.expirationDate = expirationDate;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public Integer getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(Integer insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
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
