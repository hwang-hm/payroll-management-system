package salary.model;

public class TransferFormItem {

	private Long itemId;
	private Long formId;
	private String itemCode;
	private Long displayOrder;

	public TransferFormItem() {
	}

	public TransferFormItem(Long itemId, Long formId, String itemCode, Long displayOrder) {
		this.itemId = itemId;
		this.formId = formId;
		this.itemCode = itemCode;
		this.displayOrder = displayOrder;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
}