package employee.modify2.service;

import java.util.List;

import employee.modify.service.EmpRequest;

public class EmpModify2Request {
	private EmpRequest empReq;
	private RetirementRequest retirementReq;
	
	public EmpModify2Request(EmpRequest empReq, RetirementRequest retirementReq) {
		this.empReq = empReq;
		this.retirementReq = retirementReq;
	}
	
	public EmpRequest getEmpReq() {
		return empReq;
	}
	
	public RetirementRequest getRetirementReq() {
		return retirementReq;
	}

	
}
