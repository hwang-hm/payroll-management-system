package retirement.severancepay.service;

import java.sql.Date;

public class OtherTaxableIncomeRequest {

	private Date paymentMonth;
	private String paymentItem;
	private Long amount;
	
	public OtherTaxableIncomeRequest() {
	}

	public OtherTaxableIncomeRequest(Date paymentMonth,
			String paymentItem, Long amount) {

		this.paymentMonth = paymentMonth;
		this.paymentItem = paymentItem;
		this.amount = amount;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	
	
	
}
