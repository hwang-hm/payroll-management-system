package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Education {

	private Integer educationId;
	private Integer empId;
	private String educationType;
	private Date admissionDate;
	private Date graduationDate;
	private String schoolName;
	private String major;
	private String educationStatus;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Education() {
	}
	
	
	public Education(Integer educationId, Integer empId, String educationType, Date admissionDate, Date graduationDate,
			String schoolName, String major, String educationStatus, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.educationId = educationId;
		this.empId = empId;
		this.educationType = educationType;
		this.admissionDate = admissionDate;
		this.graduationDate = graduationDate;
		this.schoolName = schoolName;
		this.major = major;
		this.educationStatus = educationStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	public Integer getEducationId() {
		return educationId;
	}


	public void setEducationId(Integer educationId) {
		this.educationId = educationId;
	}


	public Integer getEmpId() {
		return empId;
	}


	public void setEmpId(Integer empId) {
		this.empId = empId;
	}


	public String getEducationType() {
		return educationType;
	}


	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}


	public Date getAdmissionDate() {
		return admissionDate;
	}


	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}


	public Date getGraduationDate() {
		return graduationDate;
	}


	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getEducationStatus() {
		return educationStatus;
	}


	public void setEducationStatus(String educationStatus) {
		this.educationStatus = educationStatus;
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
