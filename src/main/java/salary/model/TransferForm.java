package salary.model;

public class TransferForm {

	private Integer formId;
	private String formName;

	public TransferForm() {
	}

	public TransferForm(Integer formId, String formName) {
		this.formId = formId;
		this.formName = formName;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
}
