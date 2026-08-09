package salary.model;

import java.util.Date;

public class Salary {

	private Long salaryId;
	private Long salaryDateId;
	private Long empId;
	private Long totalPay;
	private Long totalDeduct;
	private Long netPay;
	private Date createdAt;
	private Date updatedAt;

	public Salary() {
	}

	public Salary(Long salaryId, Long salaryDateId, Long empId, Long totalPay, Long totalDeduct, Long netPay,
			Date createdAt, Date updatedAt) {
		super();
		this.salaryId = salaryId;
		this.salaryDateId = salaryDateId;
		this.empId = empId;
		this.totalPay = totalPay;
		this.totalDeduct = totalDeduct;
		this.netPay = netPay;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Long salaryId) {
		this.salaryId = salaryId;
	}

	public Long getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(Long salaryDateId) {
		this.salaryDateId = salaryDateId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
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