package salary.model;

public class SalaryDetail {

	private Integer detailId;
	private Integer salaryId;
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
	private String memo;
	private Long amount;

	public SalaryDetail() {
	}

	public SalaryDetail(Integer detailId, Integer salaryId, Integer salaryItemId, Integer attendanceTypeId,
			String itemType, String itemName, String taxYn, String taxFreeName, Long taxFreeLimit, String calcMethod,
			String roundUnit, Long defaultAmount, String memo, Long amount) {
		this.detailId = detailId;
		this.salaryId = salaryId;
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
		this.memo = memo;
		this.amount = amount;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Integer salaryId) {
		this.salaryId = salaryId;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
