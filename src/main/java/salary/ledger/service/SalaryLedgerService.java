package salary.ledger.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.viewdto.SalarySummary;
import salary.ledger.dao.SalaryLedgerDao;
import salary.ledger.viewdto.SalaryLedgerData;
import salary.ledger.viewdto.SalaryLedgerRow;

public class SalaryLedgerService {

	private final SalaryLedgerDao salaryLedgerDao = new SalaryLedgerDao();

	public SalaryLedgerData getLedgerData(int payYear) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<SalaryLedgerRow> rows = salaryLedgerDao.selectLedgerRowsByPayYear(conn, payYear);
			List<Integer> yearOptions = buildYearOptions(salaryLedgerDao.selectDistinctPayYears(conn), payYear);

			SalaryLedgerData data = new SalaryLedgerData();
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

	private SalarySummary toSummary(List<SalaryLedgerRow> rows) {
		SalarySummary summary = new SalarySummary();
		int count = 0;
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (SalaryLedgerRow row : rows) {
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
