package employee.register2.service;

import employee.model.Retirement;

public class EmpRegister2Mapper {

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
