package salary.transfer;

/**
 * 이체양식에서 선택 가능한 컬럼 전체 목록.
 * code 값은 transfer_form_item.item_code 와 동일하게 사용한다.
 */
public enum TransferColumn {

	EMP_TYPE("EMP_TYPE", "구분"),
	EMP_ID("EMP_ID", "사원번호"),
	NAME_KOR("NAME_KOR", "성명"),
	NAME_ENG("NAME_ENG", "성명(E)"),
	DEPT("DEPT", "부서"),
	POSITION("POSITION", "직위"),
	HIRE_DATE("HIRE_DATE", "입사일"),
	MOBILE("MOBILE", "휴대폰번호"),
	EMAIL("EMAIL", "E-mail"),
	BANK("BANK", "금융기관"),
	ACCOUNT("ACCOUNT", "계좌번호"),
	TOTAL_PAY("TOTAL_PAY", "지급합계"),
	TOTAL_DEDUCT("TOTAL_DEDUCT", "공제합계"),
	NET_PAY("NET_PAY", "실지급액");

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
