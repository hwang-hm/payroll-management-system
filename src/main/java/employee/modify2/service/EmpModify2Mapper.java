package employee.modify2.service;

import employee.model.Employee;
import employee.model.Retirement;
import employee.modify.service.EmpRequest;

public class EmpModify2Mapper {
	
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

	public Retirement toRetirement(RetirementRequest retirementReq) {

	    Retirement retirement = new Retirement();

	    retirement.setEmpId(retirementReq.getEmpId());
	    retirement.setRetirementType(retirementReq.getRetirementType());
	    retirement.setRetirementDate(retirementReq.getRetirementDate());
	    retirement.setRetirementReason(retirementReq.getRetirementReason());
	    retirement.setContactAfterRetirement(retirementReq.getContactAfterRetirement());
	    
	    return retirement;
	}
}
