package employee.model;

import java.sql.Timestamp;

public class Recommender {
	private Integer empId;
	private String recommenderName;
	private String relation;
	private String companyName;
	private String positionName;
	private String phoneNumber;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Recommender() {
	}

	public Recommender(Integer empId, String recommenderName, String relation, String companyName, String positionName,
			String phoneNumber, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.empId = empId;
		this.recommenderName = recommenderName;
		this.relation = relation;
		this.companyName = companyName;
		this.positionName = positionName;
		this.phoneNumber = phoneNumber;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRecommenderName() {
		return recommenderName;
	}

	public void setRecommenderName(String recommenderName) {
		this.recommenderName = recommenderName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
