package salary.ledger.viewdto;

// 급여명세서 지급·공제 항목 한 줄 (좌우 정렬용)
// 給与明細書の支給・控除項目1行 (左右整列用)
public class PayStubDetailLine {

	private String payItemName;
	private Long payAmount;
	private String payCalcMethod;
	private String deductItemName;
	private Long deductAmount;

	public String getPayItemName() {
		return payItemName;
	}

	public void setPayItemName(String payItemName) {
		this.payItemName = payItemName;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayCalcMethod() {
		return payCalcMethod;
	}

	public void setPayCalcMethod(String payCalcMethod) {
		this.payCalcMethod = payCalcMethod;
	}

	public String getDeductItemName() {
		return deductItemName;
	}

	public void setDeductItemName(String deductItemName) {
		this.deductItemName = deductItemName;
	}

	public Long getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(Long deductAmount) {
		this.deductAmount = deductAmount;
	}
}
