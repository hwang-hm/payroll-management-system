package salary.statics.service.request;

public class SalaryItemDTO {

	private String itemName;
	private String itemType; // PAY 또는 DEDUCT
	private long amount;
	private double rate; // 해당 구분(지급/공제) 내 구성비율(%)

	public SalaryItemDTO() {
	}

	public SalaryItemDTO(String itemName, String itemType, long amount) {
		this.itemName = itemName;
		this.itemType = itemType;
		this.amount = amount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
