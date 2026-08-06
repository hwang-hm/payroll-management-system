package salary.service.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistSalaryRequest {
	private Long salaryId; // salaryId가 null이면 저장
	private Long empId;
	private Integer payYear;
	private Integer payMonth;
	private Integer paySeq;
	private String startDate;
	private String endDate;
	private String payDate;
	private Long totalPay;
	private Long totalDeduct;
	private Long netPay;

	// 급여 상세 항목 목록 (SALARY_DETAIL 테이블 매핑용)
	private List<SalaryDetailRequest> details = new ArrayList<>();

	// --- Getter / Setter ---
	public Long getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Long salaryId) {
		this.salaryId = salaryId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Integer getPayYear() {
		return payYear;
	}

	public void setPayYear(Integer payYear) {
		this.payYear = payYear;
	}

	public Integer getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(Integer payMonth) {
		this.payMonth = payMonth;
	}

	public Integer getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(Integer paySeq) {
		this.paySeq = paySeq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public Long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Long totalPay) {
		this.totalPay = totalPay;
	}

	public Long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(Long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public Long getNetPay() {
		return netPay;
	}

	public void setNetPay(Long netPay) {
		this.netPay = netPay;
	}

	public List<SalaryDetailRequest> getDetails() {
		return details;
	}

	public void setDetails(List<SalaryDetailRequest> details) {
		this.details = details;
	}

	// --- 유효성 검증 ---
	public void validate(Map<String, Boolean> errors) {
		if (empId == null || empId <= 0)
			errors.put("empId", Boolean.TRUE);
		if (payYear == null || payYear <= 0)
			errors.put("payYear", Boolean.TRUE);
		if (payMonth == null || payMonth < 1 || payMonth > 12)
			errors.put("payMonth", Boolean.TRUE);
		if (paySeq == null || paySeq < 1 || paySeq > 10)
			errors.put("paySeq", Boolean.TRUE);
		if (startDate == null || startDate.trim().isEmpty())
			errors.put("startDate", Boolean.TRUE);
		if (endDate == null || endDate.trim().isEmpty())
			errors.put("endDate", Boolean.TRUE);
		if (payDate == null || payDate.trim().isEmpty())
			errors.put("payDate", Boolean.TRUE);
	}

	// Inner Class: 급여 상세 항목 DTO
	public static class SalaryDetailRequest {
		private Long salaryItemId;
		private String itemType; // PAY(지급), DEDUCT(공제)
		private String itemName; // 기본급, 식대, 소득세 등
		private String taxYn; // Y, N
		private Long amount; // 금액
		private String memo; // 비고/메모

		public Long getSalaryItemId() {
			return salaryItemId;
		}

		public void setSalaryItemId(Long salaryItemId) {
			this.salaryItemId = salaryItemId;
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

		public Long getAmount() {
			return amount;
		}

		public void setAmount(Long amount) {
			this.amount = amount;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}
	}
}