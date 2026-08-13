package employee.register.service;

import java.util.List;

public class EmpRegisterRequest {
	private EmpRequest empReq;
	private EmpInfoSalaryRequest empInfoSalaryReq;
	private List<DependentRequest> dependentReqs;
	
	public EmpRegisterRequest(EmpRequest empReq, EmpInfoSalaryRequest empInfoSalaryReq,
			List<DependentRequest> dependentReqs) {
		this.empReq = empReq;
		this.empInfoSalaryReq = empInfoSalaryReq;
		this.dependentReqs = dependentReqs;
	}
	public EmpRequest getEmpReq() {
		return empReq;
	}
	public EmpInfoSalaryRequest getEmpInfoSalaryReq() {
		return empInfoSalaryReq;
	}
	public List<DependentRequest> getDependentReqs() {
		return dependentReqs;
	}

	
}
