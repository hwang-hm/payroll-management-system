package salary.dayworker.service.request;

public class GetSalaryListDTO {
	Integer salaryId;
	Integer salaryDateId;
	Integer empId;
	Long totalPay;
	Long totalDeduct;
	Long netPay;
	String nameKor;
	String employmentType;
	String deptName;

	public GetSalaryListDTO() {
	}

	public GetSalaryListDTO(Integer salaryId, Integer salaryDateId, Integer empId, Long totalPay, Long totalDeduct,
			Long netPay, String nameKor, String employmentType, String deptName) {
		this.salaryId = salaryId;
		this.salaryDateId = salaryDateId;
		this.empId = empId;
		this.totalPay = totalPay;
		this.totalDeduct = totalDeduct;
		this.netPay = netPay;
		this.nameKor = nameKor;
		this.employmentType = employmentType;
		this.deptName = deptName;
	}

	public Integer getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Integer salaryId) {
		this.salaryId = salaryId;
	}

	public Integer getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(Integer salaryDateId) {
		this.salaryDateId = salaryDateId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getNameKor() {
		return nameKor;
	}

	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Long totalPay) {
		this.totalPay = totalPay;
	}

	public Long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(Long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public Long getNetPay() {
		return netPay;
	}

	public void setNetPay(Long netPay) {
		this.netPay = netPay;
	}

}
