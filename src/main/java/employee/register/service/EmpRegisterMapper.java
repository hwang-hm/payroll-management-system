package employee.register.service;

import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Employee;

public class EmpRegisterMapper {
	public Employee toEmp(EmpRequest empReq) {

	    Employee emp = new Employee();

	    emp.setNameKor(empReq.getNameKor());
	    emp.setHireDate(empReq.getHireDate());
	    emp.setEmploymentType(empReq.getEmploymentType());
	    emp.setDeptId(empReq.getDeptId());
	    emp.setNationality(empReq.getNationality());
	    emp.setNameEng(empReq.getNameEng());
	    emp.setRetireDate(empReq.getRetireDate());
	    emp.setPositionId(empReq.getPositionId());
	    emp.setResidentNo(empReq.getResidentNo());
	    emp.setZipCode(empReq.getZipCode());
	    emp.setAddress(empReq.getAddress());
	    emp.setPhoneNum(empReq.getPhoneNum());
	    emp.setMobileNum(empReq.getMobileNum());
	    emp.setEmail(empReq.getEmail());
	    emp.setSns(empReq.getSns());
	    emp.setRemarks(empReq.getRemarks());
	    emp.setPhotoPath(empReq.getPhotoPath());

	    return emp;
	}
	public EmpInfoSalary toEmpInfoSalary (EmpInfoSalaryRequest empInfoSalaryReq) {
		
		EmpInfoSalary empInfoSalary = new EmpInfoSalary();

		empInfoSalary.setNationalPensionYN(empInfoSalaryReq.getNationalPensionYN());
		empInfoSalary.setHealthInsuranceYN(empInfoSalaryReq.getHealthInsuranceYN());
		empInfoSalary.setHealthDiscountRate(empInfoSalaryReq.getHealthDiscountRate());
		empInfoSalary.setLongtermCareYN(empInfoSalaryReq.getLongtermCareYN());
		empInfoSalary.setLongtermCareDiscountRate(empInfoSalaryReq.getLongtermCareDiscountRate());
		empInfoSalary.setEmploymentInsuranceYN(empInfoSalaryReq.getEmploymentInsuranceYN());
		empInfoSalary.setIncomeTaxType(empInfoSalaryReq.getIncomeTaxType());
		empInfoSalary.setYouthTaxReductionYN(empInfoSalaryReq.getYouthTaxReductionYN());
		empInfoSalary.setYouthTaxReductionRate(empInfoSalaryReq.getYouthTaxReductionRate());
		empInfoSalary.setDurunuriType(empInfoSalaryReq.getDurunuriType());
		empInfoSalary.setDurunuriSplitYN(empInfoSalaryReq.getDurunuriSplitYN());
		empInfoSalary.setDurunuriPensionRate(empInfoSalaryReq.getDurunuriPensionRate());
		empInfoSalary.setDurunuriEmploymentRate(empInfoSalaryReq.getDurunuriEmploymentRate());
		empInfoSalary.setBaseSalary(empInfoSalaryReq.getBaseSalary());
		empInfoSalary.setPensionSalary(empInfoSalaryReq.getPensionSalary());
		empInfoSalary.setHealthSalary(empInfoSalaryReq.getHealthSalary());
		empInfoSalary.setEmploymentSalary(empInfoSalaryReq.getEmploymentSalary());
		empInfoSalary.setSalaryBank(empInfoSalaryReq.getSalaryBank());
		empInfoSalary.setSalaryAccount(empInfoSalaryReq.getSalaryAccount());
		
		return empInfoSalary;
	}
	
	public Dependent toDependent(DependentRequest dependentReq) {
		
		Dependent dependent = new Dependent();
		
		dependent.setRelation(dependentReq.getRelation());
		dependent.setFamilyName(dependentReq.getFamilyName());
		dependent.setNationality(dependentReq.getNationality());
		dependent.setResidentNo(dependentReq.getResidentNo());
		dependent.setDisabilityYN(dependentReq.getDisabilityYN());
		dependent.setDeductionYN(dependentReq.getDeductionYN());
		dependent.setHealthInsuranceYN(dependentReq.getHealthInsuranceYN());
		dependent.setCohabitationYN(dependentReq.getCohabitationYN());
		dependent.setIncomeTaxYN(dependentReq.getIncomeTaxYN());
		dependent.setChildAgeYN(dependentReq.getChildAgeYN());
		
		return dependent;
	}
}
