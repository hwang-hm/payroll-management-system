package salary.model;

import java.util.Date;

public class SalaryDate {

	private Long salaryDateId;
	private Integer payYear;
	private Integer payMonth;
	private Integer paySeq;
	private Date startDate;
	private Date endDate;
	private Date payDate;

	public SalaryDate() {
	}

	public SalaryDate(Integer payYear, Integer payMonth, Integer paySeq, Date startDate, Date endDate, Date payDate) {
		super();
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.payDate = payDate;
	}

	public SalaryDate(Long salaryDateId, Integer payYear, Integer payMonth, Integer paySeq, Date startDate,
			Date endDate, Date payDate) {
		super();
		this.salaryDateId = salaryDateId;
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.payDate = payDate;
	}
	
	public Long getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(Long salaryDateId) {
		this.salaryDateId = salaryDateId;
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

}
