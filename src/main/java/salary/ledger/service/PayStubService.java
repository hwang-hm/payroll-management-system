package salary.ledger.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDateDao;
import salary.entry.dao.SalaryDetailDao;
import salary.ledger.dao.PayStubDao;
import salary.ledger.viewdto.PayStubData;
import salary.ledger.viewdto.PayStubDetailLine;
import salary.ledger.viewdto.PayStubForm;
import salary.ledger.viewdto.PayStubRow;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

public class PayStubService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final PayStubDao payStubDao = new PayStubDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 귀속연월·차수 기준으로 급여명세서 화면 데이터를 모아줌
	public PayStubData getPayStubData(int payYear, int payMonth, int paySeq, int selectedSalaryId,
			String searchKeyword) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			PayStubData data = new PayStubData();
			data.setPayYear(payYear);
			data.setPayMonth(payMonth);
			data.setPaySeq(paySeq);
			data.setSearchKeyword(normalizeKeyword(searchKeyword));

			SalaryDate salaryDate = salaryDateDao.selectByPeriod(conn, payYear, payMonth, paySeq);
			if (salaryDate == null) {
				data.setRows(new ArrayList<>());
				return data;
			}

			setPeriodDates(data, salaryDate);
			List<PayStubRow> rows = payStubDao.selectRowsBySalaryDateId(conn, salaryDate.getSalaryDateId(),
					data.getSearchKeyword());
			data.setRows(rows);

			if (selectedSalaryId == 0 && !rows.isEmpty()) {
				selectedSalaryId = rows.get(0).getSalaryId();
			} else if (selectedSalaryId > 0 && !containsSalaryId(rows, selectedSalaryId)) {
				selectedSalaryId = rows.isEmpty() ? 0 : rows.get(0).getSalaryId();
			}
			data.setSelectedSalaryId(selectedSalaryId);

			if (selectedSalaryId > 0) {
				data.setForm(buildForm(conn, selectedSalaryId, data.getPayDate()));
			}

			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private PayStubForm buildForm(Connection conn, int salaryId, String payDate) throws SQLException {
		PayStubForm form = payStubDao.selectFormBySalaryId(conn, salaryId);
		if (form == null) {
			return null;
		}
		if (form.getPayDate() == null || form.getPayDate().isEmpty()) {
			form.setPayDate(payDate);
		}

		List<SalaryDetail> payDetails = new ArrayList<>();
		List<SalaryDetail> deductDetails = new ArrayList<>();
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			if ("PAY".equals(detail.getItemType())) {
				payDetails.add(detail);
			} else {
				deductDetails.add(detail);
			}
		}
		form.setPayDetails(payDetails);
		form.setDeductDetails(deductDetails);
		form.setDetailLines(toDetailLines(payDetails, deductDetails));
		return form;
	}

	private List<PayStubDetailLine> toDetailLines(List<SalaryDetail> payDetails, List<SalaryDetail> deductDetails) {
		int max = Math.max(payDetails.size(), deductDetails.size());
		List<PayStubDetailLine> lines = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			PayStubDetailLine line = new PayStubDetailLine();
			if (i < payDetails.size()) {
				SalaryDetail pay = payDetails.get(i);
				line.setPayItemName(pay.getItemName());
				line.setPayAmount(pay.getAmount());
				line.setPayCalcMethod(pay.getCalcMethod());
			}
			if (i < deductDetails.size()) {
				SalaryDetail deduct = deductDetails.get(i);
				line.setDeductItemName(deduct.getItemName());
				line.setDeductAmount(deduct.getAmount());
			}
			lines.add(line);
		}
		return lines;
	}

	private boolean containsSalaryId(List<PayStubRow> rows, int salaryId) {
		for (PayStubRow row : rows) {
			if (row.getSalaryId() == salaryId) {
				return true;
			}
		}
		return false;
	}

	private String normalizeKeyword(String searchKeyword) {
		if (searchKeyword == null) {
			return "";
		}
		return searchKeyword.trim();
	}

	private void setPeriodDates(PayStubData data, SalaryDate salaryDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		data.setStartDate(sdf.format(salaryDate.getStartDate()));
		data.setEndDate(sdf.format(salaryDate.getEndDate()));
		data.setPayDate(sdf.format(salaryDate.getPayDate()));
	}
}
