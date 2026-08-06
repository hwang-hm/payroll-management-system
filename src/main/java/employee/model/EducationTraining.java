package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class EducationTraining {
	private Integer trainingId;
	private Integer empId;
	private String trainingType;
	private String trainingName;
	private Date startDate;
	private Date endDate;
	private String trainingCenter;
	private Integer trainingCost;
	private Integer refundCost;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Integer getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
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
	public String getTrainingCenter() {
		return trainingCenter;
	}
	public void setTrainingCenter(String trainingCenter) {
		this.trainingCenter = trainingCenter;
	}
	public Integer getTrainingCost() {
		return trainingCost;
	}
	public void setTrainingCost(Integer trainingCost) {
		this.trainingCost = trainingCost;
	}
	public Integer getRefundCost() {
		return refundCost;
	}
	public void setRefundCost(Integer refundCost) {
		this.refundCost = refundCost;
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
