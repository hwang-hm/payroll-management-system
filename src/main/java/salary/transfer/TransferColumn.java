package salary.transfer;

/**
 * 이체양식에서 선택 가능한 컬럼 전체 목록.
 * 振込様式で選択可能なカラム全一覧。
 * code 값은 transfer_form_item.item_code 와 동일하게 사용한다.
 * code 値は transfer_form_item.item_code と同一に使用する。
 */
public enum TransferColumn {

	EMP_TYPE("EMP_TYPE", "区分"),
	EMP_ID("EMP_ID", "社員番号"),
	NAME_KOR("NAME_KOR", "氏名"),
	NAME_ENG("NAME_ENG", "氏名(E)"),
	DEPT("DEPT", "部署"),
	POSITION("POSITION", "職位"),
	HIRE_DATE("HIRE_DATE", "入社日"),
	MOBILE("MOBILE", "携帯電話番号"),
	EMAIL("EMAIL", "E-mail"),
	BANK("BANK", "金融機関"),
	ACCOUNT("ACCOUNT", "口座番号"),
	TOTAL_PAY("TOTAL_PAY", "支給合計"),
	TOTAL_DEDUCT("TOTAL_DEDUCT", "控除合計"),
	NET_PAY("NET_PAY", "実支給額");

	private final String code;
	private final String label;

	TransferColumn(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	// 저장된 코드값으로 컬럼 찾아줌. 없으면 null
	// 保存済みコード値でカラムを検索。なければ null
	public static TransferColumn fromCode(String code) {
		if (code == null) {
			return null;
		}
		for (TransferColumn column : values()) {
			if (column.code.equals(code)) {
				return column;
			}
		}
		return null;
	}
}
