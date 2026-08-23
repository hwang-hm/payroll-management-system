package salary.statics.service.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 급여 구성 항목별 통계 DTO
 * 給与構成項目別統計DTO
 */
public class SalaryCompositionDTO {

	// 사원 ID
	// 社員ID
	private int empId;

	// 사원명
	// 社員名
	private String empName;

	// 귀속년도
	// 帰属年度
	private int year;

	// 귀속월
	// 帰属月
	private int month;

	// 총 지급액
	// 支給総額
	private long totalPay;

	// 총 공제액
	// 控除総額
	private long totalDeduct;

	// 실수령액 (실지급액)
	// 手取り額
	private long netPay;

	// 지급 항목 리스트
	// 支給項目リスト
	private List<SalaryItemDTO> payList = new ArrayList<>();

	// 공제 항목 리스트
	// 控除項目リスト
	private List<SalaryItemDTO> deductList = new ArrayList<>();

	/**
	 * Map 형태 추출 지원 ( "PAY" -> 지급 리스트, "DEDUCT" -> 공제 리스트 )
	 * Map形式での抽出サポート（ "PAY" -> 支給リスト, "DEDUCT" -> 控除リスト ）
	 */
	public Map<String, List<SalaryItemDTO>> getItemMap() {
		Map<String, List<SalaryItemDTO>> map = new HashMap<>();
		map.put("PAY", payList);
		map.put("DEDUCT", deductList);
		return map;
	}

	/**
	 * 지급 항목 추가
	 * 支給項目の追加
	 */
	public void addPayItem(SalaryItemDTO item) {
		this.payList.add(item);
	}

	/**
	 * 공제 항목 추가
	 * 控除項目の追加
	 */
	public void addDeductItem(SalaryItemDTO item) {
		this.deductList.add(item);
	}

	/**
	 * 각 항목별 비율 계산 처리 (총 지급액 및 총 공제액 대비 %)
	 * 各項目別の割合計算処理（支給総額および控除総額に対する%）
	 */
	public void calculateRates() {
		for (SalaryItemDTO item : payList) {
			if (totalPay > 0) {
				double rate = Math.round((item.getAmount() / (double) totalPay) * 1000.0) / 10.0;
				item.setRate(rate);
			}
		}
		for (SalaryItemDTO item : deductList) {
			if (totalDeduct > 0) {
				double rate = Math.round((item.getAmount() / (double) totalDeduct) * 1000.0) / 10.0;
				item.setRate(rate);
			}
		}
	}

	// 사원 ID Getter / Setter
	// 社員ID Getter / Setter
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	// 사원명 Getter / Setter
	// 社員名 Getter / Setter
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	// 귀속년도 Getter / Setter
	// 帰属年度 Getter / Setter
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// 귀속월 Getter / Setter
	// 帰属月 Getter / Setter
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// 총 지급액 Getter / Setter
	// 支給総額 Getter / Setter
	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	// 총 공제액 Getter / Setter
	// 控除総額 Getter / Setter
	public long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	// 실수령액 Getter / Setter
	// 手取り額 Getter / Setter
	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}

	// 지급 항목 리스트 Getter / Setter
	// 支給項目リスト Getter / Setter
	public List<SalaryItemDTO> getPayList() {
		return payList;
	}

	public void setPayList(List<SalaryItemDTO> payList) {
		this.payList = payList;
	}

	// 공제 항목 리스트 Getter / Setter
	// 控除項目リスト Getter / Setter
	public List<SalaryItemDTO> getDeductList() {
		return deductList;
	}

	public void setDeductList(List<SalaryItemDTO> deductList) {
		this.deductList = deductList;
	}
}