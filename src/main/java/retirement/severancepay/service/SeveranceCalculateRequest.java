package retirement.severancepay.service;

import java.sql.Date;
import java.util.List;

public class SeveranceCalculateRequest {
	private int empId;
	
	// 직접 입력
    private Long retirementBonus;           // 퇴직위로금
    private Long dismissalAllowance;        // 해고예고수당
    private Long nonTaxableRetirementPay;   // 비과세퇴직급여
    private Long prepaidTax;                // 기납부세액
    private Long taxCredit;                 // 세액공제
    private Long dailyOrdinaryWage;         // 1일 통상임금
    private Long ruralSpecialTax;           // 농어촌특별세
    private Long otherDeduction;            // 기타공제
    private Date paymentDate;                  // 지급일
	
	// 기타 과세소득
    private List<OtherTaxableIncomeRequest> otherTaxableIncomes;

    // 과세이연계좌 
    private List<TaxDeferredAccountRequest> taxDeferredAccounts;

	public SeveranceCalculateRequest() {
	}

	public SeveranceCalculateRequest(int empId, Long retirementBonus, Long dismissalAllowance,
			Long nonTaxableRetirementPay, Long prepaidTax, Long taxCredit, Long dailyOrdinaryWage, Long ruralSpecialTax,
			Long otherDeduction, Date paymentDate, List<OtherTaxableIncomeRequest> otherTaxableIncomes,
			List<TaxDeferredAccountRequest> taxDeferredAccounts) {

		this.empId = empId;
		this.retirementBonus = retirementBonus;
		this.dismissalAllowance = dismissalAllowance;
		this.nonTaxableRetirementPay = nonTaxableRetirementPay;
		this.prepaidTax = prepaidTax;
		this.taxCredit = taxCredit;
		this.dailyOrdinaryWage = dailyOrdinaryWage;
		this.ruralSpecialTax = ruralSpecialTax;
		this.otherDeduction = otherDeduction;
		this.paymentDate = paymentDate;
		this.otherTaxableIncomes = otherTaxableIncomes;
		this.taxDeferredAccounts = taxDeferredAccounts;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public Long getRetirementBonus() {
		return retirementBonus;
	}

	public void setRetirementBonus(Long retirementBonus) {
		this.retirementBonus = retirementBonus;
	}

	public Long getDismissalAllowance() {
		return dismissalAllowance;
	}

	public void setDismissalAllowance(Long dismissalAllowance) {
		this.dismissalAllowance = dismissalAllowance;
	}

	public Long getNonTaxableRetirementPay() {
		return nonTaxableRetirementPay;
	}

	public void setNonTaxableRetirementPay(Long nonTaxableRetirementPay) {
		this.nonTaxableRetirementPay = nonTaxableRetirementPay;
	}

	public Long getPrepaidTax() {
		return prepaidTax;
	}

	public void setPrepaidTax(Long prepaidTax) {
		this.prepaidTax = prepaidTax;
	}

	public Long getTaxCredit() {
		return taxCredit;
	}

	public void setTaxCredit(Long taxCredit) {
		this.taxCredit = taxCredit;
	}

	public Long getDailyOrdinaryWage() {
		return dailyOrdinaryWage;
	}

	public void setDailyOrdinaryWage(Long dailyOrdinaryWage) {
		this.dailyOrdinaryWage = dailyOrdinaryWage;
	}

	public Long getRuralSpecialTax() {
		return ruralSpecialTax;
	}

	public void setRuralSpecialTax(Long ruralSpecialTax) {
		this.ruralSpecialTax = ruralSpecialTax;
	}

	public Long getOtherDeduction() {
		return otherDeduction;
	}

	public void setOtherDeduction(Long otherDeduction) {
		this.otherDeduction = otherDeduction;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public List<OtherTaxableIncomeRequest> getOtherTaxableIncomes() {
		return otherTaxableIncomes;
	}

	public void setOtherTaxableIncomes(List<OtherTaxableIncomeRequest> otherTaxableIncomes) {
		this.otherTaxableIncomes = otherTaxableIncomes;
	}

	public List<TaxDeferredAccountRequest> getTaxDeferredAccounts() {
		return taxDeferredAccounts;
	}

	public void setTaxDeferredAccounts(List<TaxDeferredAccountRequest> taxDeferredAccounts) {
		this.taxDeferredAccounts = taxDeferredAccounts;
	}

	

	
    
}
