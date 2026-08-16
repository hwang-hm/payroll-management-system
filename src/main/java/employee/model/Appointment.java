package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
	private Integer appointmentId;
	private int empId;
	private String appointmentType;
	private Date appointmentDate;
	private String deptName;
	private String positionName;
	private String jobTitle;
	private String remarks;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	

	public Appointment() {
	}
	
	
	public Appointment(Integer appointmentId, int empId, String appointmentType, Date appointmentDate, String deptName,
			String positionName, String jobTitle, String remarks, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.appointmentId = appointmentId;
		this.empId = empId;
		this.appointmentType = appointmentType;
		this.appointmentDate = appointmentDate;
		this.deptName = deptName;
		this.positionName = positionName;
		this.jobTitle = jobTitle;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
