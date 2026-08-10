package salary.entry.service;

public class SalaryView {

	private int salaryId;
	private int empId;
	private String employmentType;
	private String nameKor;
	private String deptName;
	private long totalPay;
	private long totalDeduct;
	private long netPay;

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}
}
