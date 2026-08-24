package salary.ledger.viewdto;

import java.util.List;

import salary.entry.viewdto.SalarySummary;

// 급여대장 조회 페이지 view용 데이터
// 給与台帳照会ページview用データ
public class SalaryLedgerData {

	private int payYear;
	private List<Integer> yearOptions;
	private List<SalaryLedgerRow> rows;
	private SalarySummary summary;

	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public List<Integer> getYearOptions() {
		return yearOptions;
	}

	public void setYearOptions(List<Integer> yearOptions) {
		this.yearOptions = yearOptions;
	}

	public List<SalaryLedgerRow> getRows() {
		return rows;
	}

	public void setRows(List<SalaryLedgerRow> rows) {
		this.rows = rows;
	}

	public SalarySummary getSummary() {
		return summary;
	}

	public void setSummary(SalarySummary summary) {
		this.summary = summary;
	}
}
