package retirement.severancepay.service;

import java.sql.Date;
import java.util.List;

public class SeveranceCalculateRequest {
	private int empId;
	
	// 직접 입력
	// 直接入力
    private Long retirementBonus;           // 퇴직위로금
    // 退職慰労金
    private Long dismissalAllowance;        // 해고예고수당
    // 解雇予告手当
    private Long nonTaxableRetirementPay;   // 비과세퇴직급여
    // 非課税退職給与
    private Long prepaidTax;                // 기납부세액
    // 既納付税額
    private Long taxCredit;                 // 세액공제
    // 税額控除
    private Long dailyOrdinaryWage;         // 1일 통상임금
    // 1日通常賃金
    private Long ruralSpecialTax;           // 농어촌특별세
    // 農漁村特別税
    private Long otherDeduction;            // 기타공제
    // その他控除
    private Date paymentDate;                  // 지급일
    // 支給日
	
	// 기타 과세소득
	// その他課税所得
    private List<OtherTaxableIncomeRequest> otherTaxableIncomes;

    // 과세이연계좌 
    // 課税繰延口座
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
