package employee.model;

import java.sql.Date;
import java.sql.Timestamp;

public class RewardPunishment {
	private Integer rewardId;
	private Integer empId;
	private String rewardType;
	private String rewardName;
	private String manager;
	private Date rewardDate;
	private String rewardContent;
	private String remarks;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public RewardPunishment() {
	}

	public RewardPunishment(Integer rewardId, Integer empId, String rewardType, String rewardName, String manager,
			Date rewardDate, String rewardContent, String remarks, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.rewardId = rewardId;
		this.empId = empId;
		this.rewardType = rewardType;
		this.rewardName = rewardName;
		this.manager = manager;
		this.rewardDate = rewardDate;
		this.rewardContent = rewardContent;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Date getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(Date rewardDate) {
		this.rewardDate = rewardDate;
	}

	public String getRewardContent() {
		return rewardContent;
	}

	public void setRewardContent(String rewardContent) {
		this.rewardContent = rewardContent;
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
