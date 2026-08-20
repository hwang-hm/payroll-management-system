package retirement.severancepay.service;

import java.sql.Date;

public class TaxDeferredAccountRequest {

	private String pensionProviderName;
	private String businessRegistrationNo;
	private String accountNo;
	private Date depositDate;
	private Long depositAmount;
	
	public TaxDeferredAccountRequest() {
	}

	public TaxDeferredAccountRequest(String pensionProviderName,String businessRegistrationNo, String accountNo, Date depositDate, Long depositAmount) {

		this.pensionProviderName = pensionProviderName;
		this.businessRegistrationNo = businessRegistrationNo;
		this.accountNo = accountNo;
		this.depositDate = depositDate;
		this.depositAmount = depositAmount;
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

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	
}
