package salary.dayworker.service.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 일용직 급여 등록 요청 DTO
 * 日雇い給与登録リクエストDTO
 */
public class RegistSalaryRequest {
	private int payYear; // 귀속년도 / 帰属年度
	private int payMonth; // 귀속월 / 帰属月
	private int paySeq; // 정산 차수 / 精算回数
	private Date startDate; // 급여 산정 시작일 / 給与計算開始日
	private Date endDate; // 급여 산정 종료일 / 給与計算終了日
	private Date payDate; // 지급일 / 支給日
	private int empId = 0; // 사원 ID / 社員ID
	private Long totalPay = 0L; // 총지급액 / 支給総額
	private Long totalDeduct = 0L; // 총공제액 / 控除総額
	private Long netPay = 0L; // 실지급액 / 差引支給額
	private List<SalaryDetailRequest> details = new ArrayList<>(); // 급여 상세 내역 목록 / 給与詳細履歴リスト

	// --- Constructors / コンストラクタ ---
	public RegistSalaryRequest() {
	}

	public RegistSalaryRequest(int payYear, int payMonth, int paySeq, Date startDate, Date endDate, Date payDate,
			int empId, Long totalPay, Long totalDeduct, Long netPay, List<SalaryDetailRequest> details) {
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

	/**
	 * 필수 입력 데이터 유효성 검증
	 * 必須入力データの妥当性検証
	 * 
	 * @param errors 에러 항목 수집 맵 / エラー項目収集マップ
	 */
	public void validate(Map<String, Boolean> errors) {
		if (empId <= 0) {
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

	// --- Getters & Setters (Null-Safe 적용 / Null-Safe適用) ---
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

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
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

	/**
	 * 급여 상세 항목 요청 DTO (정산 항목별 금액/메모)
	 * 給与詳細項目リクエストDTO (精算項目別金額/メモ)
	 */
	public static class SalaryDetailRequest {
		private Integer salaryItemId; // 급여 항목 마스터 ID (직접 입력 항목일 경우 Null) / 給与項目マスターID (直接入力項目の場合はNull)
		private String itemType; // 항목 구분 (PAY: 지급, DEDUCT: 공제) / 項目区分 (PAY: 支給, DEDUCT: 控除)
		private String itemName; // 항목명 / 項目名
		private String taxYn; // 과세 여부 (Y/N) / 課税有無 (Y/N)
		private Long amount; // 금액 / 金額
		private String memo; // 메모 / メモ

		public SalaryDetailRequest() {
		}

		public SalaryDetailRequest(Integer salaryItemId, String itemType, String itemName, String taxYn, Long amount,
				String memo) {
			this.salaryItemId = salaryItemId;
			setItemType(itemType);
			setItemName(itemName);
			setTaxYn(taxYn);
			setAmount(amount);
			setMemo(memo);
		}

		public Integer getSalaryItemId() {
			return salaryItemId;
		}

		public void setSalaryItemId(Integer salaryItemId) {
			// 신규 직접 입력 항목일 수 있으므로 null 허용 / 新規直接入力項目の可能性があるためnull許容
			this.salaryItemId = salaryItemId;
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
			// null 입력 시 0L로 자동 변환 / null入力時に0Lへ自動変換
			this.amount = (amount != null) ? amount : 0L;
		}

		public String getMemo() {
			return memo != null ? memo : "";
		}

		public void setMemo(String memo) {
			this.memo = (memo != null) ? memo : "";
		}
	}
}