package salary.model;

public class TransferFormItem {

	private Integer itemId;
	private Integer formId;
	private String itemCode;
	private Integer displayOrder;

	public TransferFormItem() {
	}

	public TransferFormItem(Integer itemId, Integer formId, String itemCode, Integer displayOrder) {
		this.itemId = itemId;
		this.formId = formId;
		this.itemCode = itemCode;
		this.displayOrder = displayOrder;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
}
