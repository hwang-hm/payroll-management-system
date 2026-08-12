package salary.register.viewdto;

import java.util.List;

import salary.entry.viewdto.SalarySummary;

// 급여대장 페이지 view용 데이터
public class SalaryRegisterData {

	private int payYear;
	private List<Integer> yearOptions;
	private List<SalaryRegisterRow> rows;
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

	public List<SalaryRegisterRow> getRows() {
		return rows;
	}

	public void setRows(List<SalaryRegisterRow> rows) {
		this.rows = rows;
	}

	public SalarySummary getSummary() {
		return summary;
	}

	public void setSummary(SalarySummary summary) {
		this.summary = summary;
	}
}
