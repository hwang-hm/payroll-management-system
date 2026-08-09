package salary.model;

import java.util.Date;

public class Salary {

	private Integer salaryId;
	private Integer salaryDateId;
	private Integer empId;
	private Long totalPay;
	private Long totalDeduct;
	private Long netPay;
	private Date createdAt;
	private Date updatedAt;

	public Salary() {
	}

	public Salary(Integer salaryId, Integer salaryDateId, Integer empId, Long totalPay, Long totalDeduct, Long netPay,
			Date createdAt, Date updatedAt) {
		this.salaryId = salaryId;
		this.salaryDateId = salaryDateId;
		this.empId = empId;
		this.totalPay = totalPay;
		this.totalDeduct = totalDeduct;
		this.netPay = netPay;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
