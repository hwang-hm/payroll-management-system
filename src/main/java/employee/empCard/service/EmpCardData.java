package employee.empCard.service;

import java.util.List;

import employee.dto.EmployeeDetailData;
import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.NationalPensionInfo;
import employee.model.Retirement;
import retirement.model.SeverancePay;

public class EmpCardData {
	private EmployeeDetailData emp;
	private EmpInfoSalary empInfoSalary;
	private List<Dependent> dependents;
	private Retirement retirement;
	private SeverancePay severancePay;

	public EmpCardData() {
	}

	public EmpCardData(EmployeeDetailData emp, EmpInfoSalary empInfoSalary, List<Dependent> dependents,
			Retirement retirement, SeverancePay severancePay) {
		this.emp = emp;
		this.empInfoSalary = empInfoSalary;
		this.dependents = dependents;
		this.retirement = retirement;
		this.severancePay = severancePay;
	}

	public EmployeeDetailData getEmp() {
		return emp;
	}

	public void setEmp(EmployeeDetailData emp) {
		this.emp = emp;
	}

	public EmpInfoSalary getEmpInfoSalary() {
		return empInfoSalary;
	}

	public void setEmpInfoSalary(EmpInfoSalary empInfoSalary) {
		this.empInfoSalary = empInfoSalary;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependent> dependents) {
		this.dependents = dependents;
	}

	public Retirement getRetirement() {
		return retirement;
	}

	public void setRetirement(Retirement retirement) {
		this.retirement = retirement;
	}

	public SeverancePay getSeverancePay() {
		return severancePay;
	}

	public void setSeverancePay(SeverancePay severancePay) {
		this.severancePay = severancePay;
	}

}
