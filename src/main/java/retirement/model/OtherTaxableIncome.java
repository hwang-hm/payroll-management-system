package retirement.model;

import java.sql.Date;
import java.sql.Timestamp;

public class OtherTaxableIncome {
	private int otherTaxableIncomeId;
	private int severancePayId;
	private Date paymentMonth;
	private String paymentItem;
	private Integer amount;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getOtherTaxableIncomeId() {
		return otherTaxableIncomeId;
	}
	public void setOtherTaxableIncomeId(int otherTaxableIncomeId) {
		this.otherTaxableIncomeId = otherTaxableIncomeId;
	}
	public int getSeverancePayId() {
		return severancePayId;
	}
	public void setSeverancePayId(int severancePayId) {
		this.severancePayId = severancePayId;
	}
	public Date getPaymentMonth() {
		return paymentMonth;
	}
	public void setPaymentMonth(Date paymentMonth) {
		this.paymentMonth = paymentMonth;
	}
	public String getPaymentItem() {
		return paymentItem;
	}
	public void setPaymentItem(String paymentItem) {
		this.paymentItem = paymentItem;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
