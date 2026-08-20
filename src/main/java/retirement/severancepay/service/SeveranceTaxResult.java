package retirement.severancepay.service;

public class SeveranceTaxResult {
	private long retirementIncome;
    private long calculatedTax;
    private long retirementIncomeTax;
    private long localIncomeTax;
    private long deferredRetirementTax;
    private long deferredLocalTax;
    private long taxableRetirementPay;
    private long withholdingTax;
    private long netPayment;

    public SeveranceTaxResult(
            long retirementIncome,
            long calculatedTax,
            long retirementIncomeTax,
            long localIncomeTax,
            long deferredRetirementTax,
            long deferredLocalTax,
            long taxableRetirementPay,
            long withholdingTax,
            long netPayment) {

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
