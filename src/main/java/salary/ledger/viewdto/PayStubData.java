package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

// 급여명세서 화면 전체 데이터
// 給与明細書画面の全体データ
public class PayStubData {

	private int payYear;
	private int payMonth;
	private int paySeq;
	private String startDate;
	private String endDate;
	private String payDate;
	private String searchKeyword;
	private List<PayStubRow> rows = new ArrayList<>();
	private int selectedSalaryId;
	private PayStubForm form;

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

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public List<PayStubRow> getRows() {
		return rows;
	}

	public void setRows(List<PayStubRow> rows) {
		this.rows = rows == null ? new ArrayList<>() : rows;
	}

	public int getSelectedSalaryId() {
		return selectedSalaryId;
	}

	public void setSelectedSalaryId(int selectedSalaryId) {
		this.selectedSalaryId = selectedSalaryId;
	}

	public PayStubForm getForm() {
		return form;
	}

	public void setForm(PayStubForm form) {
		this.form = form;
	}
}
