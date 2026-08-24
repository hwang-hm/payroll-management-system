package retirement.severancepay.service;

public class SeveranceCalculateResult {
	
	// 계산 과정에서 화면에 보여줄 값
	// 計算過程で画面に表示する値
    private long threeMonthTotal;           // 3개월 총계
    // 3か月合計
    private long dailyAverageWage;          // 1일 평균임금
    // 1日平均賃金
    private int retirementTaxYear;         // 퇴직일이 속하는 과세연도
    // 退職日が属する課税年度

    // DB에 최종 저장되는 계산 결과
    // DBに最終保存される計算結果
    private long retirementIncome;           // 퇴직소득
    // 退職所得
    private long calculatedTax;              // 산출세액
    // 算出税額
    private long retirementIncomeTax;        // 퇴직소득세
    // 退職所得税
    private long localIncomeTax;             // 지방소득세
    // 地方所得税
    private long deferredRetirementTax;      // 이연 퇴직소득세
    // 繰延退職所得税
    private long deferredLocalTax;           // 이연 지방소득세
    // 繰延地方所得税
    private long taxableRetirementPay;       // 과세대상 퇴직급여
    // 課税対象退職給与
    private long withholdingTax;             // 차감원천징수세액
    // 差引源泉徴収税額
    private long netPayment;                 // 실수령액
    // 実受取額

    public SeveranceCalculateResult(
    		long threeMonthTotal,
    		long dailyAverageWage,
    		int retirementTaxYear,
    		long retirementIncome,
    		long calculatedTax,
    		long retirementIncomeTax,
    		long localIncomeTax,
    		long deferredRetirementTax,
    		long deferredLocalTax,
    		long taxableRetirementPay,
    		long withholdingTax,
    		long netPayment) {

        this.threeMonthTotal = threeMonthTotal;
        this.dailyAverageWage = dailyAverageWage;
        this.retirementTaxYear = retirementTaxYear;
        this.retirementIncome = retirementIncome;
        this.calculatedTax = calculatedTax;
        this.retirementIncomeTax = retirementIncomeTax;
        this.localIncomeTax = localIncomeTax;
        this.deferredRetirementTax = deferredRetirementTax;
        this.deferredLocalTax = deferredLocalTax;
        this.taxableRetirementPay = taxableRetirementPay;
        this.withholdingTax = withholdingTax;
        this.netPayment = netPayment;
    }

	public long getThreeMonthTotal() {
		return threeMonthTotal;
	}

	public void setThreeMonthTotal(long threeMonthTotal) {
		this.threeMonthTotal = threeMonthTotal;
	}

	public long getDailyAverageWage() {
		return dailyAverageWage;
	}

	public void setDailyAverageWage(long dailyAverageWage) {
		this.dailyAverageWage = dailyAverageWage;
	}

	public int getRetirementTaxYear() {
		return retirementTaxYear;
	}

	public void setRetirementTaxYear(int retirementTaxYear) {
		this.retirementTaxYear = retirementTaxYear;
	}

	public long getRetirementIncome() {
		return retirementIncome;
	}

	public void setRetirementIncome(long retirementIncome) {
		this.retirementIncome = retirementIncome;
	}

	public long getCalculatedTax() {
		return calculatedTax;
	}

	public void setCalculatedTax(long calculatedTax) {
		this.calculatedTax = calculatedTax;
	}

	public long getRetirementIncomeTax() {
		return retirementIncomeTax;
	}

	public void setRetirementIncomeTax(long retirementIncomeTax) {
		this.retirementIncomeTax = retirementIncomeTax;
	}

	public long getLocalIncomeTax() {
		return localIncomeTax;
	}

	public void setLocalIncomeTax(long localIncomeTax) {
		this.localIncomeTax = localIncomeTax;
	}

	public long getDeferredRetirementTax() {
		return deferredRetirementTax;
	}

	public void setDeferredRetirementTax(long deferredRetirementTax) {
		this.deferredRetirementTax = deferredRetirementTax;
	}

	public long getDeferredLocalTax() {
		return deferredLocalTax;
	}

	public void setDeferredLocalTax(long deferredLocalTax) {
		this.deferredLocalTax = deferredLocalTax;
	}

	public long getTaxableRetirementPay() {
		return taxableRetirementPay;
	}

	public void setTaxableRetirementPay(long taxableRetirementPay) {
		this.taxableRetirementPay = taxableRetirementPay;
	}

	public long getWithholdingTax() {
		return withholdingTax;
	}

	public void setWithholdingTax(long withholdingTax) {
		this.withholdingTax = withholdingTax;
	}

	public long getNetPayment() {
		return netPayment;
	}

	public void setNetPayment(long netPayment) {
		this.netPayment = netPayment;
	}

    
}
