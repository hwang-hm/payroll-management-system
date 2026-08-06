package retirement.model;

import java.sql.Date;
import java.sql.Timestamp;

public class SeverancePay {
	private int severancePayId;
	private int empId;
	private Integer retirementBonus;
	private Integer dismissalAllowance;
	private Integer nonTaxableRetirementPay;
	private Integer prepaidTax;
	private Integer taxCredit;
	private Integer dailyOrdinaryWage;
	private int retirementIncome;
	private int calculatedTax;
	private int retirementIncomeTax;
	private int localIncomeTax;
	private int deferredRetirementTax;
	private int deferredLocalTax;
	private Integer ruralSpecialTax;
	private Integer otherDeduction;
	private int taxableRetirementPay;
	private int withholdingTax;
	private int netPayment;
	private Date paymentDate;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getSeverancePayId() {
		return severancePayId;
	}
	public void setSeverancePayId(int severancePayId) {
		this.severancePayId = severancePayId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Integer getRetirementBonus() {
		return retirementBonus;
	}
	public void setRetirementBonus(Integer retirementBonus) {
		this.retirementBonus = retirementBonus;
	}
	public Integer getDismissalAllowance() {
		return dismissalAllowance;
	}
	public void setDismissalAllowance(Integer dismissalAllowance) {
		this.dismissalAllowance = dismissalAllowance;
	}
	public Integer getNonTaxableRetirementPay() {
		return nonTaxableRetirementPay;
	}
	public void setNonTaxableRetirementPay(Integer nonTaxableRetirementPay) {
		this.nonTaxableRetirementPay = nonTaxableRetirementPay;
	}
	public Integer getPrepaidTax() {
		return prepaidTax;
	}
	public void setPrepaidTax(Integer prepaidTax) {
		this.prepaidTax = prepaidTax;
	}
	public Integer getTaxCredit() {
		return taxCredit;
	}
	public void setTaxCredit(Integer taxCredit) {
		this.taxCredit = taxCredit;
	}
	public Integer getDailyOrdinaryWage() {
		return dailyOrdinaryWage;
	}
	public void setDailyOrdinaryWage(Integer dailyOrdinaryWage) {
		this.dailyOrdinaryWage = dailyOrdinaryWage;
	}
	public int getRetirementIncome() {
		return retirementIncome;
	}
	public void setRetirementIncome(int retirementIncome) {
		this.retirementIncome = retirementIncome;
	}
	public int getCalculatedTax() {
		return calculatedTax;
	}
	public void setCalculatedTax(int calculatedTax) {
		this.calculatedTax = calculatedTax;
	}
	public int getRetirementIncomeTax() {
		return retirementIncomeTax;
	}
	public void setRetirementIncomeTax(int retirementIncomeTax) {
		this.retirementIncomeTax = retirementIncomeTax;
	}
	public int getLocalIncomeTax() {
		return localIncomeTax;
	}
	public void setLocalIncomeTax(int localIncomeTax) {
		this.localIncomeTax = localIncomeTax;
	}
	public int getDeferredRetirementTax() {
		return deferredRetirementTax;
	}
	public void setDeferredRetirementTax(int deferredRetirementTax) {
		this.deferredRetirementTax = deferredRetirementTax;
	}
	public int getDeferredLocalTax() {
		return deferredLocalTax;
	}
	public void setDeferredLocalTax(int deferredLocalTax) {
		this.deferredLocalTax = deferredLocalTax;
	}
	public Integer getRuralSpecialTax() {
		return ruralSpecialTax;
	}
	public void setRuralSpecialTax(Integer ruralSpecialTax) {
		this.ruralSpecialTax = ruralSpecialTax;
	}
	public Integer getOtherDeduction() {
		return otherDeduction;
	}
	public void setOtherDeduction(Integer otherDeduction) {
		this.otherDeduction = otherDeduction;
	}
	public int getTaxableRetirementPay() {
		return taxableRetirementPay;
	}
	public void setTaxableRetirementPay(int taxableRetirementPay) {
		this.taxableRetirementPay = taxableRetirementPay;
	}
	public int getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(int withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public int getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(int netPayment) {
		this.netPayment = netPayment;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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
