package salary.statics.service.request;

/**
 * 급여 구성 세부 항목 DTO
 * 給与構成詳細項目DTO
 */
public class SalaryItemDTO {

	// 항목명
	// 項目名
	private String itemName;

	// 항목 유형 (PAY: 지급, DEDUCT: 공제)
	// 項目タイプ (PAY: 支給, DEDUCT: 控除)
	private String itemType;

	// 금액
	// 金額
	private long amount;

	// 해당 구분(지급/공제) 내 구성 비율 (%)
	// 該当区分(支給/控除)内の構成割合 (%)
	private double rate;

	/**
	 * 기본 생성자
	 * デフォルトコンストラクタ
	 */
	public SalaryItemDTO() {
	}

	/**
	 * 필드 초기화 생성자
	 * フィールド初期化コンストラクタ
	 */
	public SalaryItemDTO(String itemName, String itemType, long amount) {
		this.itemName = itemName;
		this.itemType = itemType;
		this.amount = amount;
	}

	// 항목명 Getter / Setter
	// 項目名 Getter / Setter
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	// 항목 유형 Getter / Setter
	// 項目タイプ Getter / Setter
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	// 금액 Getter / Setter
	// 金額 Getter / Setter
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	// 구성 비율 Getter / Setter
	// 構成割合 Getter / Setter
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}