package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

// 급여대장 상세(귀속연월·차수) 화면용 데이터
// 給与台帳詳細(帰属年月・回数)画面用データ
public class SalaryLedgerDetailData {

	private int salaryDateId;
	private int payYear;
	private int payMonth;
	private int paySeq;
	private String startDate;
	private String endDate;
	private String payDate;
	private List<SalaryLedgerDetailRow> rows = new ArrayList<>();
	private SalaryLedgerDetailRow summary = new SalaryLedgerDetailRow();

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

	public List<SalaryLedgerDetailRow> getRows() {
		return rows;
	}

	public void setRows(List<SalaryLedgerDetailRow> rows) {
		this.rows = rows != null ? rows : new ArrayList<>();
	}

	public SalaryLedgerDetailRow getSummary() {
		return summary;
	}

	public void setSummary(SalaryLedgerDetailRow summary) {
		this.summary = summary != null ? summary : new SalaryLedgerDetailRow();
	}
}
