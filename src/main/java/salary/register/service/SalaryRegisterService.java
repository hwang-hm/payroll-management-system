package salary.register.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDateDao;
import salary.entry.viewdto.SalarySummary;
import salary.register.viewdto.SalaryRegisterData;
import salary.register.viewdto.SalaryRegisterRow;

public class SalaryRegisterService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();

	public SalaryRegisterData getRegisterData(int payYear) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<SalaryRegisterRow> rows = salaryDateDao.selectRegisterRowsByYear(conn, payYear);
			List<Integer> yearOptions = buildYearOptions(salaryDateDao.selectDistinctPayYears(conn), payYear);

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
