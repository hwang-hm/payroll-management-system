package employee.model;

import java.sql.Timestamp;

public class Position {
	private Integer postionId;
	private String positionName;
	private int sortOrder;
	private String useYN;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Integer getPostionId() {
		return postionId;
	}
	public void setPostionId(Integer postionId) {
		this.postionId = postionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getUseYN() {
		return useYN;
	}
	public void setUseYN(String useYN) {
		this.useYN = useYN;
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
