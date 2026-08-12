package salary.register.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDateDao;
import salary.entry.viewdto.SalarySummary;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.register.viewdto.SalaryRegisterData;
import salary.register.viewdto.SalaryRegisterRow;

public class SalaryRegisterService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDao salaryDao = new SalaryDao();

	public SalaryRegisterData getRegisterData(int payYear) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<SalaryDate> dates = salaryDateDao.selectByYear(conn, payYear);
			List<Salary> salaries = salaryDao.selectByPayYear(conn, payYear);
			List<SalaryRegisterRow> rows = toRegisterRows(dates, salaries);
			List<Integer> yearOptions = buildYearOptions(salaryDao.selectDistinctPayYears(conn), payYear);

			SalaryRegisterData data = new SalaryRegisterData();
			data.setPayYear(payYear);
			data.setYearOptions(yearOptions);
			data.setRows(rows);
			data.setSummary(toSummary(rows));
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private List<SalaryRegisterRow> toRegisterRows(List<SalaryDate> dates, List<Salary> salaries) {
		Map<Integer, List<Salary>> salariesByDateId = groupBySalaryDateId(salaries);
		List<SalaryRegisterRow> rows = new ArrayList<>();

		for (SalaryDate date : dates) {
			List<Salary> dateSalaries = salariesByDateId.get(date.getSalaryDateId());
			if (dateSalaries == null || dateSalaries.isEmpty()) {
				continue;
			}
			rows.add(toRegisterRow(date, dateSalaries));
		}
		return rows;
	}

	private Map<Integer, List<Salary>> groupBySalaryDateId(List<Salary> salaries) {
		Map<Integer, List<Salary>> grouped = new HashMap<>();
		for (Salary salary : salaries) {
			grouped.computeIfAbsent(salary.getSalaryDateId(), key -> new ArrayList<>()).add(salary);
		}
		return grouped;
	}

	private SalaryRegisterRow toRegisterRow(SalaryDate date, List<Salary> salaries) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (Salary salary : salaries) {
			totalPay += salary.getTotalPay() != null ? salary.getTotalPay() : 0L;
			totalDeduct += salary.getTotalDeduct() != null ? salary.getTotalDeduct() : 0L;
			netPay += salary.getNetPay() != null ? salary.getNetPay() : 0L;
		}

		SalaryRegisterRow row = new SalaryRegisterRow();
		row.setSalaryDateId(date.getSalaryDateId());
		row.setPayYear(date.getPayYear());
		row.setPayMonth(date.getPayMonth());
		row.setPaySeq(date.getPaySeq());
		row.setStartDate(sdf.format(date.getStartDate()));
		row.setEndDate(sdf.format(date.getEndDate()));
		row.setPayDate(sdf.format(date.getPayDate()));
		row.setEmpCount(salaries.size());
		row.setTotalPay(totalPay);
		row.setTotalDeduct(totalDeduct);
		row.setNetPay(netPay);
		return row;
	}

	private List<Integer> buildYearOptions(List<Integer> dbYears, int selectedYear) {
		List<Integer> years = new ArrayList<>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (dbYears != null) {
			years.addAll(dbYears);
		}
		if (!years.contains(currentYear)) {
			years.add(0, currentYear);
		}
		if (!years.contains(selectedYear)) {
			years.add(0, selectedYear);
		}
		years.sort((a, b) -> Integer.compare(b, a));
		return years;
	}

	private SalarySummary toSummary(List<SalaryRegisterRow> rows) {
		SalarySummary summary = new SalarySummary();
		int count = 0;
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (SalaryRegisterRow row : rows) {
			count += row.getEmpCount();
			totalPay += row.getTotalPay();
			totalDeduct += row.getTotalDeduct();
			netPay += row.getNetPay();
		}
		summary.setCount(count);
		summary.setTotalPay(totalPay);
		summary.setTotalDeduct(totalDeduct);
		summary.setNetPay(netPay);
		return summary;
	}
}
