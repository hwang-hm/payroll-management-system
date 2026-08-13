package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class PersonalHistory {
	private Integer careerId;
	private Integer empId;
	private String companyName;
	private Date hireDate;
	private Date retireDate;
	private String lastPosition;
	private String position;
	private String retireReason;
	private Integer workYear;
	private Integer workMonth;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public PersonalHistory() {
	}

	public PersonalHistory(Integer careerId, Integer empId, String companyName, Date hireDate, Date retireDate,
			String lastPosition, String position, String retireReason, Integer workYear, Integer workMonth,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.careerId = careerId;
		this.empId = empId;
		this.companyName = companyName;
		this.hireDate = hireDate;
		this.retireDate = retireDate;
		this.lastPosition = lastPosition;
		this.position = position;
		this.retireReason = retireReason;
		this.workYear = workYear;
		this.workMonth = workMonth;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getCareerId() {
		return careerId;
	}

	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(String lastPosition) {
		this.lastPosition = lastPosition;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRetireReason() {
		return retireReason;
	}

	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}

	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public Integer getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(Integer workMonth) {
		this.workMonth = workMonth;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}
