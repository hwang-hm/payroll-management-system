package employee.model;

import java.sql.Timestamp;

public class Dependent {
	private Integer dependentId;
	private Integer empId;

	// 필수 입력 항목
	private String relation;
	private String familyName;

	private String nationality;
	private String residentNo;
	private String disabilityYN;
	private String deductionYN;
	private String healthInsuranceYN;
	private String cohabitationYN;
	private String incomeTaxYN;
	private String childAgeYN;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Dependent() {
	}

	public Dependent(Integer dependentId, Integer empId, String relation, String familyName, String nationality,
			String residentNo, String disabilityYN, String deductionYN, String healthInsuranceYN, String cohabitationYN,
			String incomeTaxYN, String childAgeYN, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.dependentId = dependentId;
		this.empId = empId;
		this.relation = relation;
		this.familyName = familyName;
		this.nationality = nationality;
		this.residentNo = residentNo;
		this.disabilityYN = disabilityYN;
		this.deductionYN = deductionYN;
		this.healthInsuranceYN = healthInsuranceYN;
		this.cohabitationYN = cohabitationYN;
		this.incomeTaxYN = incomeTaxYN;
		this.childAgeYN = childAgeYN;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getDependentId() {
		return dependentId;
	}

	public void setDependentId(Integer dependentId) {
		this.dependentId = dependentId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getResidentNo() {
		return residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	public String getDisabilityYN() {
		return disabilityYN;
	}

	public void setDisabilityYN(String disabilityYN) {
		this.disabilityYN = disabilityYN;
	}

	public String getDeductionYN() {
		return deductionYN;
	}

	public void setDeductionYN(String deductionYN) {
		this.deductionYN = deductionYN;
	}

	public String getHealthInsuranceYN() {
		return healthInsuranceYN;
	}

	public void setHealthInsuranceYN(String healthInsuranceYN) {
		this.healthInsuranceYN = healthInsuranceYN;
	}

	public String getCohabitationYN() {
		return cohabitationYN;
	}

	public void setCohabitationYN(String cohabitationYN) {
		this.cohabitationYN = cohabitationYN;
	}

	public String getIncomeTaxYN() {
		return incomeTaxYN;
	}

	public void setIncomeTaxYN(String incomeTaxYN) {
		this.incomeTaxYN = incomeTaxYN;
	}

	public String getChildAgeYN() {
		return childAgeYN;
	}

	public void setChildAgeYN(String childAgeYN) {
		this.childAgeYN = childAgeYN;
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
