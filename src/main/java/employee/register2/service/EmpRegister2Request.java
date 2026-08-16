package employee.register2.service;

import java.util.List;

public class EmpRegister2Request {

	private RetirementRequest retirementReq;
	
	public EmpRegister2Request(RetirementRequest retirementReq) {
		this.retirementReq = retirementReq;
	}
	
	public RetirementRequest getRetirementReq() {
		return retirementReq;
	}

	
}
