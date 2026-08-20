package employee.modify.service;

import java.util.Map;

public class DependentRequest {
	
	private Integer dependentId;
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
	
	public DependentRequest() {
	}

	public DependentRequest(Integer dependentId, String relation, String familyName, String nationality, String residentNo,
			String disabilityYN, String deductionYN, String healthInsuranceYN, String cohabitationYN,
			String incomeTaxYN, String childAgeYN) {
		this.dependentId = dependentId;
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
	}

	
	public Integer getDependentId() {
		return dependentId;
	}

	public void setDependentId(Integer dependentId) {
		this.dependentId = dependentId;
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
	
	public void validate(Map<String, Boolean> errors) {
		if (relation == null || relation.trim().isEmpty()) {
	        errors.put("relation", Boolean.TRUE);
	    }
		if (familyName == null || familyName.trim().isEmpty()) {
			errors.put("familyName", Boolean.TRUE);
		}
	}
	
}
