package employee.model;

import java.sql.Timestamp;

public class Department {
	private Integer deptId;
	private String deptName;
	private int sortOrder;
	private String useYN;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Department() {
	}

	public Department(Integer deptId, String deptName, int sortOrder, String useYN, Timestamp createdAt,
			Timestamp updatedAt) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.sortOrder = sortOrder;
		this.useYN = useYN;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
