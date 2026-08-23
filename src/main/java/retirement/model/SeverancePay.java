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
	private Integer retirementIncome;
	private Integer calculatedTax;
	private Integer retirementIncomeTax;
	private Integer localIncomeTax;
	private Integer deferredRetirementTax;
	private Integer deferredLocalTax;
	private Integer ruralSpecialTax;
	private Integer otherDeduction;
	private Integer taxableRetirementPay;
	private Integer withholdingTax;
	private Integer netPayment;
	private Date paymentDate;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public SeverancePay() {
	}

	public SeverancePay(int severancePayId, int empId, Integer retirementBonus, Integer dismissalAllowance,
			Integer nonTaxableRetirementPay, Integer prepaidTax, Integer taxCredit, Integer dailyOrdinaryWage,
			Integer retirementIncome, Integer calculatedTax, Integer retirementIncomeTax, Integer localIncomeTax,
			Integer deferredRetirementTax, Integer deferredLocalTax, Integer ruralSpecialTax, Integer otherDeduction,
			Integer taxableRetirementPay, Integer withholdingTax, Integer netPayment, Date paymentDate,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.severancePayId = severancePayId;
		this.empId = empId;
		this.retirementBonus = retirementBonus;
		this.dismissalAllowance = dismissalAllowance;
		this.nonTaxableRetirementPay = nonTaxableRetirementPay;
		this.prepaidTax = prepaidTax;
		this.taxCredit = taxCredit;
		this.dailyOrdinaryWage = dailyOrdinaryWage;
		this.retirementIncome = retirementIncome;
		this.calculatedTax = calculatedTax;
		this.retirementIncomeTax = retirementIncomeTax;
		this.localIncomeTax = localIncomeTax;
		this.deferredRetirementTax = deferredRetirementTax;
		this.deferredLocalTax = deferredLocalTax;
		this.ruralSpecialTax = ruralSpecialTax;
		this.otherDeduction = otherDeduction;
		this.taxableRetirementPay = taxableRetirementPay;
		this.withholdingTax = withholdingTax;
		this.netPayment = netPayment;
		this.paymentDate = paymentDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public Integer getRetirementIncome() {
		return retirementIncome;
	}

	public void setRetirementIncome(Integer retirementIncome) {
		this.retirementIncome = retirementIncome;
	}

	public Integer getCalculatedTax() {
		return calculatedTax;
	}

	public void setCalculatedTax(Integer calculatedTax) {
		this.calculatedTax = calculatedTax;
	}

	public Integer getRetirementIncomeTax() {
		return retirementIncomeTax;
	}

	public void setRetirementIncomeTax(Integer retirementIncomeTax) {
		this.retirementIncomeTax = retirementIncomeTax;
	}

	public Integer getLocalIncomeTax() {
		return localIncomeTax;
	}

	public void setLocalIncomeTax(Integer localIncomeTax) {
		this.localIncomeTax = localIncomeTax;
	}

	public Integer getDeferredRetirementTax() {
		return deferredRetirementTax;
	}

	public void setDeferredRetirementTax(Integer deferredRetirementTax) {
		this.deferredRetirementTax = deferredRetirementTax;
	}

	public Integer getDeferredLocalTax() {
		return deferredLocalTax;
	}

	public void setDeferredLocalTax(Integer deferredLocalTax) {
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

	public Integer getTaxableRetirementPay() {
		return taxableRetirementPay;
	}

	public void setTaxableRetirementPay(Integer taxableRetirementPay) {
		this.taxableRetirementPay = taxableRetirementPay;
	}

	public Integer getWithholdingTax() {
		return withholdingTax;
	}

	public void setWithholdingTax(Integer withholdingTax) {
		this.withholdingTax = withholdingTax;
	}

	public Integer getNetPayment() {
		return netPayment;
	}

	public void setNetPayment(Integer netPayment) {
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
