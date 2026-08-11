package salary.entry.viewdto;

import java.util.List;

import salary.model.SalaryDetail;

//급여입력/관리 페이지의 전반적인 view용 정보
public class SalaryEntryData {

	private int salaryDateId;
	private int payYear;
	private int payMonth;
	private int paySeq;
	private String startDate;
	private String endDate;
	private String payDate;
	private List<SalaryView> salaryList;
	private SalarySummary summary;
	private int selectedSalaryId;
	private SalaryView selectedSalary;
	private List<SalaryDetail> payDetails;
	private List<SalaryDetail> deductDetails;

	public int getSalaryDateId() {
		return salaryDateId;
	}

	public void setSalaryDateId(int salaryDateId) {
		this.salaryDateId = salaryDateId;
	}

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

	public List<SalaryView> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<SalaryView> salaryList) {
		this.salaryList = salaryList;
	}

	public SalarySummary getSummary() {
		return summary;
	}

	public void setSummary(SalarySummary summary) {
		this.summary = summary;
	}

	public int getSelectedSalaryId() {
		return selectedSalaryId;
	}

	public void setSelectedSalaryId(int selectedSalaryId) {
		this.selectedSalaryId = selectedSalaryId;
	}

	public SalaryView getSelectedSalary() {
		return selectedSalary;
	}

	public void setSelectedSalary(SalaryView selectedSalary) {
		this.selectedSalary = selectedSalary;
	}

	public List<SalaryDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<SalaryDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public List<SalaryDetail> getDeductDetails() {
		return deductDetails;
	}

	public void setDeductDetails(List<SalaryDetail> deductDetails) {
		this.deductDetails = deductDetails;
	}
}
