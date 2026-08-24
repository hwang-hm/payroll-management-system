package salary.ledger.viewdto;

import java.util.ArrayList;
import java.util.List;

// 사원별 급여내역 화면 전체 데이터
// 社員別給与明細画面の全体データ
public class EmpSalaryHistoryData {

	private int startYear;
	private int startMonth;
	private int endYear;
	private int endMonth;
	private int empId;
	private String empName;
	private List<Integer> yearOptions = new ArrayList<>();
	private List<EmpSalaryHistoryDept> departments = new ArrayList<>();
	private List<EmpSalaryHistoryRow> rows = new ArrayList<>();
	private EmpSalaryHistoryRow summary;

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

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

	public List<Integer> getYearOptions() {
		return yearOptions;
	}

	public void setYearOptions(List<Integer> yearOptions) {
		this.yearOptions = yearOptions == null ? new ArrayList<>() : yearOptions;
	}

	public List<EmpSalaryHistoryDept> getDepartments() {
		return departments;
	}

	public void setDepartments(List<EmpSalaryHistoryDept> departments) {
		this.departments = departments == null ? new ArrayList<>() : departments;
	}

	public List<EmpSalaryHistoryRow> getRows() {
		return rows;
	}

	public void setRows(List<EmpSalaryHistoryRow> rows) {
		this.rows = rows == null ? new ArrayList<>() : rows;
	}

	public EmpSalaryHistoryRow getSummary() {
		return summary;
	}

	public void setSummary(EmpSalaryHistoryRow summary) {
		this.summary = summary;
	}
}
