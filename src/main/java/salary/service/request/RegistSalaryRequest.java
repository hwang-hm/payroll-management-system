package salary.service.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RegistSalaryRequest {
	private int payYear;
	private int payMonth;
	private int paySeq;
	private Date startDate;
	private Date endDate;
	private Date payDate;
	private Long empId = 0L;
	private Long totalPay = 0L;
	private Long totalDeduct = 0L;
	private Long netPay = 0L;
	private List<SalaryDetailRequest> details = new ArrayList<>();

	// --- Constructors ---
	public RegistSalaryRequest() {
	}

	public RegistSalaryRequest(int payYear, int payMonth, int paySeq, Date startDate, Date endDate, Date payDate,
			Long empId, Long totalPay, Long totalDeduct, Long netPay, List<SalaryDetailRequest> details) {
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.payDate = payDate;
		setEmpId(empId);
		setTotalPay(totalPay);
		setTotalDeduct(totalDeduct);
		setNetPay(netPay);
		setDetails(details);
	}

	// --- Validation ---
	public void validate(Map<String, Boolean> errors) {
		if (empId == null || empId <= 0) {
			errors.put("empId", Boolean.TRUE);
		}
		if (payYear <= 0) {
			errors.put("payYear", Boolean.TRUE);
		}
		if (payMonth < 1 || payMonth > 12) {
			errors.put("payMonth", Boolean.TRUE);
		}
		if (paySeq <= 0) {
			errors.put("paySeq", Boolean.TRUE);
		}
		if (startDate == null) {
			errors.put("startDate", Boolean.TRUE);
		}
		if (endDate == null) {
			errors.put("endDate", Boolean.TRUE);
		}
		if (payDate == null) {
			errors.put("payDate", Boolean.TRUE);
		}
	}

	// --- Getters & Setters (Null-Safe 적용) ---
	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}

	public int getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(int paySeq) {
		this.paySeq = paySeq;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public long getEmpId() {
		return empId != null ? empId : 0L;
	}

	public void setEmpId(Long empId) {
		this.empId = (empId != null) ? empId : 0L;
	}

	public long getTotalPay() {
		return totalPay != null ? totalPay : 0L;
	}

	public void setTotalPay(Long totalPay) {
		this.totalPay = (totalPay != null) ? totalPay : 0L;
	}

	public long getTotalDeduct() {
		return totalDeduct != null ? totalDeduct : 0L;
	}

	public void setTotalDeduct(Long totalDeduct) {
		this.totalDeduct = (totalDeduct != null) ? totalDeduct : 0L;
	}

	public long getNetPay() {
		return netPay != null ? netPay : 0L;
	}

	public void setNetPay(Long netPay) {
		this.netPay = (netPay != null) ? netPay : 0L;
	}

	public List<SalaryDetailRequest> getDetails() {
		return details != null ? details : new ArrayList<>();
	}

	public void setDetails(List<SalaryDetailRequest> details) {
		this.details = (details != null) ? details : new ArrayList<>();
	}

	// --- Inner Class (상세 항목 요청 DTO) ---
	public static class SalaryDetailRequest {
		private Long salaryItemId;
		private String itemType;
		private String itemName;
		private String taxYn;
		private Long amount;
		private String memo;

		public SalaryDetailRequest() {
		}

		public SalaryDetailRequest(Long salaryItemId, String itemType, String itemName, String taxYn, Long amount,
				String memo) {
			this.salaryItemId = salaryItemId;
			setItemType(itemType);
			setItemName(itemName);
			setTaxYn(taxYn);
			setAmount(amount);
			setMemo(memo);
		}

		public Long getSalaryItemId() {
			return salaryItemId;
		}

		public void setSalaryItemId(Long salaryItemId) {
			this.salaryItemId = salaryItemId; // 신규 항목일 수 있으므로 null 허용
		}

		public String getItemType() {
			return itemType != null ? itemType : "PAY";
		}

		public void setItemType(String itemType) {
			this.itemType = (itemType != null) ? itemType : "PAY";
		}

		public String getItemName() {
			return itemName != null ? itemName : "";
		}

		public void setItemName(String itemName) {
			this.itemName = (itemName != null) ? itemName : "";
		}

		public String getTaxYn() {
			return taxYn != null ? taxYn : "Y";
		}

		public void setTaxYn(String taxYn) {
			this.taxYn = (taxYn != null) ? taxYn : "Y";
		}

		public long getAmount() {
			return amount != null ? amount : 0L;
		}

		public void setAmount(Long amount) {
			this.amount = (amount != null) ? amount : 0L; // null 입력 시 0L로 자동 변환
		}

		public String getMemo() {
			return memo != null ? memo : "";
		}

		public void setMemo(String memo) {
			this.memo = (memo != null) ? memo : "";
		}
	}
}