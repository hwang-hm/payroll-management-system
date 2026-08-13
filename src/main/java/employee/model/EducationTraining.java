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

	public EducationTraining() {
	}

	public EducationTraining(Integer trainingId, Integer empId, String trainingType, String trainingName,
			Date startDate, Date endDate, String trainingCenter, Integer trainingCost, Integer refundCost,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.trainingId = trainingId;
		this.empId = empId;
		this.trainingType = trainingType;
		this.trainingName = trainingName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainingCenter = trainingCenter;
		this.trainingCost = trainingCost;
		this.refundCost = refundCost;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

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
