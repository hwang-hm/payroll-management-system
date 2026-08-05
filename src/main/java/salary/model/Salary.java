package salary.model;

import java.util.Date;

public class Salary {

	private Long salaryId;
	private Long empId;
	private Integer payYear;
	private Integer payMonth;
	private Integer paySeq;
	private Date startDate;
	private Date endDate;
	private Date payDate;
	private Long totalPay;
	private Long totalDeduct;
	private Long netPay;
	private Date createdAt;
	private Date updatedAt;

	public Salary() {
	}

	public Salary(Long salaryId, Long empId, Integer payYear, Integer payMonth, Integer paySeq, Date startDate,
			Date endDate, Date payDate, Long totalPay, Long totalDeduct, Long netPay, Date createdAt, Date updatedAt) {
		this.salaryId = salaryId;
		this.empId = empId;
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.payDate = payDate;
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

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Integer getPayYear() {
		return payYear;
	}

	public void setPayYear(Integer payYear) {
		this.payYear = payYear;
	}

	public Integer getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(Integer payMonth) {
		this.payMonth = payMonth;
	}

	public Integer getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(Integer paySeq) {
		this.paySeq = paySeq;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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