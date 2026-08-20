package salary.statics.service.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryCompositionDTO {

	private int empId;
	private String empName;
	private int year;
	private int month;

	private long totalPay; // 총 지급액
	private long totalDeduct; // 총 공제액
	private long netPay; // 실수령액

	private List<SalaryItemDTO> payList = new ArrayList<>();
	private List<SalaryItemDTO> deductList = new ArrayList<>();

	// Map 형태 추출 지원 ( "PAY" -> 지급리스트, "DEDUCT" -> 공제리스트 )
	public Map<String, List<SalaryItemDTO>> getItemMap() {
		Map<String, List<SalaryItemDTO>> map = new HashMap<>();
		map.put("PAY", payList);
		map.put("DEDUCT", deductList);
		return map;
	}

	public void addPayItem(SalaryItemDTO item) {
		this.payList.add(item);
	}

	public void addDeductItem(SalaryItemDTO item) {
		this.deductList.add(item);
	}

	// 비율 계산 처리
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

	// Getter / Setter
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(long totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public long getNetPay() {
		return netPay;
	}

	public void setNetPay(long netPay) {
		this.netPay = netPay;
	}

	public List<SalaryItemDTO> getPayList() {
		return payList;
	}

	public void setPayList(List<SalaryItemDTO> payList) {
		this.payList = payList;
	}

	public List<SalaryItemDTO> getDeductList() {
		return deductList;
	}

	public void setDeductList(List<SalaryItemDTO> deductList) {
		this.deductList = deductList;
	}
}
