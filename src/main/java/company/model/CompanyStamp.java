package company.model;

import java.sql.Timestamp;

public class CompanyStamp {
	private int companyId;
	private String stampPath;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getStampPath() {
		return stampPath;
	}
	public void setStampPath(String stampPath) {
		this.stampPath = stampPath;
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
