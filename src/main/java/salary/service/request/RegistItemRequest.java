package salary.service.request;

import java.util.Map;

public class RegistItemRequest {

	private Integer salaryItemId;
	private Integer attendanceTypeId;
	private String itemType;
	private String itemName;
	private String taxYn;
	private String taxFreeName;
	private Long taxFreeLimit;
	private String calcMethod;
	private String roundUnit;
	private Long defaultAmount;
	private String remark;
	private String useYn;

	public RegistItemRequest() {
	}

	public RegistItemRequest(Integer salaryItemId, Integer attendanceTypeId, String itemType, String itemName,
			String taxYn, String taxFreeName, Long taxFreeLimit, String calcMethod, String roundUnit,
			Long defaultAmount, String remark, String useYn) {
		super();
		this.salaryItemId = salaryItemId;
		this.attendanceTypeId = attendanceTypeId;
		this.itemType = itemType;
		this.itemName = itemName;
		this.taxYn = taxYn;
		this.taxFreeName = taxFreeName;
		this.taxFreeLimit = taxFreeLimit;
		this.calcMethod = calcMethod;
		this.roundUnit = roundUnit;
		this.defaultAmount = defaultAmount;
		this.remark = remark;
		this.useYn = useYn;
	}

	public Integer getSalaryItemId() {
		return salaryItemId;
	}

	public void setSalaryItemId(Integer salaryItemId) {
		this.salaryItemId = salaryItemId;
	}

	public Integer getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Integer attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getTaxYn() {
		return taxYn;
	}

	public void setTaxYn(String taxYn) {
		this.taxYn = taxYn;
	}

	public String getTaxFreeName() {
		return taxFreeName;
	}

	public void setTaxFreeName(String taxFreeName) {
		this.taxFreeName = taxFreeName;
	}

	public Long getTaxFreeLimit() {
		return taxFreeLimit;
	}

	public void setTaxFreeLimit(Long taxFreeLimit) {
		this.taxFreeLimit = taxFreeLimit;
	}

	public String getCalcMethod() {
		return calcMethod;
	}

	public void setCalcMethod(String calcMethod) {
		this.calcMethod = calcMethod;
	}

	public String getRoundUnit() {
		return roundUnit;
	}

	public void setRoundUnit(String roundUnit) {
		this.roundUnit = roundUnit;
	}

	public Long getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(Long defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public void validate(Map<String, Boolean> errors) {
		// 1. 항목명 검증 (필수)
		checkEmpty(errors, itemName, "itemName");

		// 2. 항목 구분 검증 (PAY: 지급, DEDUCT: 공제 중 하나여야 함)
		checkEmpty(errors, itemType, "itemType");

		// 3. 과세 여부 필수 (Y: 과세, N: 비과세)
		checkEmpty(errors, taxYn, "taxYn");

		// 4. 절사단위 검증
		checkEmpty(errors, roundUnit, "roundUnit");

		// 4. 사용 여부 검증 (Y 또는 N)
		checkEmpty(errors, useYn, "useYn");
	}

	// 문자열 공백/null 체크 헬퍼 메서드
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.trim().isEmpty()) {
			errors.put(fieldName, Boolean.TRUE);
		}
	}
}
