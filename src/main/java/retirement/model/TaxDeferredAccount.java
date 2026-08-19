package retirement.model;

import java.sql.Date;
import java.sql.Timestamp;

public class TaxDeferredAccount {
	private int taxDeferredAccountId;
	private int severancePayId;
	private String pensionProviderName;
	private String businessRegistrationNo;
	private String accountNo;
	private Date depositDate;
	private Integer depositAmount;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getTaxDeferredAccountId() {
		return taxDeferredAccountId;
	}
	public void setTaxDeferredAccountId(int taxDeferredAccountId) {
		this.taxDeferredAccountId = taxDeferredAccountId;
	}
	public int getSeverancePayId() {
		return severancePayId;
	}
	public void setSeverancePayId(int severancePayId) {
		this.severancePayId = severancePayId;
	}
	public String getPensionProviderName() {
		return pensionProviderName;
	}
	public void setPensionProviderName(String pensionProviderName) {
		this.pensionProviderName = pensionProviderName;
	}
	public String getBusinessRegistrationNo() {
		return businessRegistrationNo;
	}
	public void setBusinessRegistrationNo(String businessRegistrationNo) {
		this.businessRegistrationNo = businessRegistrationNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	public Integer getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Integer depositAmount) {
		this.depositAmount = depositAmount;
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
