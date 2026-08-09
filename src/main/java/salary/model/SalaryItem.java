package salary.model;

import java.util.Date;

public class SalaryItem {

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
	private Date createdAt;
	private Date updatedAt;

	public SalaryItem() {
	}

	public SalaryItem(Integer salaryItemId, Integer attendanceTypeId, String itemType, String itemName, String taxYn,
			String taxFreeName, Long taxFreeLimit, String calcMethod, String roundUnit, Long defaultAmount,
			String remark, String useYn, Date createdAt, Date updatedAt) {
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
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
