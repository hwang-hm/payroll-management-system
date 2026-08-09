package company.model;

import java.sql.Timestamp;

public class PayrollInfo {
	private int companyId;
	private String salaryStartMonthType;
	private String salaryStartDay;
	private String salaryEndMonthType;
	private String salaryEndDayType;
	private String paymentMonthType;
	private String paymentDay;
	private String salaryBank;
	private String salaryAccount;
	private String accountHolder;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getSalaryStartMonthType() {
		return salaryStartMonthType;
	}
	public void setSalaryStartMonthType(String salaryStartMonthType) {
		this.salaryStartMonthType = salaryStartMonthType;
	}
	public String getSalaryStartDay() {
		return salaryStartDay;
	}
	public void setSalaryStartDay(String salaryStartDay) {
		this.salaryStartDay = salaryStartDay;
	}
	public String getSalaryEndMonthType() {
		return salaryEndMonthType;
	}
	public void setSalaryEndMonthType(String salaryEndMonthType) {
		this.salaryEndMonthType = salaryEndMonthType;
	}
	public String getSalaryEndDayType() {
		return salaryEndDayType;
	}
	public void setSalaryEndDayType(String salaryEndDayType) {
		this.salaryEndDayType = salaryEndDayType;
	}
	public String getPaymentMonthType() {
		return paymentMonthType;
	}
	public void setPaymentMonthType(String paymentMonthType) {
		this.paymentMonthType = paymentMonthType;
	}
	public String getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(String paymentDay) {
		this.paymentDay = paymentDay;
	}
	public String getSalaryBank() {
		return salaryBank;
	}
	public void setSalaryBank(String salaryBank) {
		this.salaryBank = salaryBank;
	}
	public String getSalaryAccount() {
		return salaryAccount;
	}
	public void setSalaryAccount(String salaryAccount) {
		this.salaryAccount = salaryAccount;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
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
