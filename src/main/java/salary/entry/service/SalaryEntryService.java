package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDateDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

public class SalaryEntryService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	public SalaryEntryData getEntryData(int payYear, int payMonth, int paySeq, int selectedSalaryId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			SalaryDate salaryDate = salaryDateDao.selectByPeriod(conn, payYear, payMonth, paySeq);
			List<SalaryView> salaryList = salaryDate != null
					? salaryDao.selectViewListBySalaryDateId(conn, salaryDate.getSalaryDateId())
					: new ArrayList<>();

			if (selectedSalaryId == 0 && !salaryList.isEmpty()) {
				selectedSalaryId = salaryList.get(0).getSalaryId();
			}

			SalaryEntryData data = new SalaryEntryData();
			data.setPayYear(payYear);
			data.setPayMonth(payMonth);
			data.setPaySeq(paySeq);
			data.setSalaryList(salaryList);
			data.setSummary(toSummary(salaryList));
			data.setSelectedSalaryId(selectedSalaryId);
			setPeriodDates(data, salaryDate, payYear, payMonth);

			if (selectedSalaryId > 0) {
				splitDetails(conn, selectedSalaryId, data);
				data.setSelectedSalary(findSalaryView(salaryList, selectedSalaryId));
			}

			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private SalarySummary toSummary(List<SalaryView> salaryList) {
		SalarySummary summary = new SalarySummary();
		summary.setCount(salaryList.size());
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (SalaryView view : salaryList) {
			totalPay += view.getTotalPay();
			totalDeduct += view.getTotalDeduct();
			netPay += view.getNetPay();
		}
		summary.setTotalPay(totalPay);
		summary.setTotalDeduct(totalDeduct);
		summary.setNetPay(netPay);
		return summary;
	}

	private void splitDetails(Connection conn, int salaryId, SalaryEntryData data) throws SQLException {
		List<SalaryDetail> payDetails = new ArrayList<>();
		List<SalaryDetail> deductDetails = new ArrayList<>();
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			if ("PAY".equals(detail.getItemType())) {
				payDetails.add(detail);
			} else {
				deductDetails.add(detail);
			}
		}
		data.setPayDetails(payDetails);
		data.setDeductDetails(deductDetails);
	}

	private SalaryView findSalaryView(List<SalaryView> salaryList, int salaryId) {
		for (SalaryView view : salaryList) {
			if (view.getSalaryId() == salaryId) {
				return view;
			}
		}
		return null;
	}

	private void setPeriodDates(SalaryEntryData data, SalaryDate salaryDate, int payYear, int payMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (salaryDate != null) {
			data.setStartDate(sdf.format(salaryDate.getStartDate()));
			data.setEndDate(sdf.format(salaryDate.getEndDate()));
			data.setPayDate(sdf.format(salaryDate.getPayDate()));
			return;
		}

		int nextMonth = payMonth == 12 ? 1 : payMonth + 1;
		int nextYear = payMonth == 12 ? payYear + 1 : payYear;
		Calendar cal = Calendar.getInstance();
		cal.set(payYear, payMonth - 1, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		data.setStartDate(String.format("%04d-%02d-01", payYear, payMonth));
		data.setEndDate(String.format("%04d-%02d-%02d", payYear, payMonth, lastDay));
		data.setPayDate(String.format("%04d-%02d-05", nextYear, nextMonth));
	}
}
