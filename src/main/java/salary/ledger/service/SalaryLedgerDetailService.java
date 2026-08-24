package salary.ledger.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDateDao;
import salary.ledger.dao.SalaryLedgerDetailDao;
import salary.ledger.viewdto.SalaryLedgerDetailData;
import salary.ledger.viewdto.SalaryLedgerDetailRow;
import salary.model.SalaryDate;

public class SalaryLedgerDetailService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryLedgerDetailDao detailDao = new SalaryLedgerDetailDao();

	public SalaryLedgerDetailData getDetailData(int salaryDateId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			SalaryLedgerDetailData data = new SalaryLedgerDetailData();
			data.setSalaryDateId(salaryDateId);

			if (salaryDateId <= 0) {
				data.setRows(new ArrayList<>());
				return data;
			}

			SalaryDate salaryDate = salaryDateDao.selectById(conn, salaryDateId);
			if (salaryDate == null) {
				data.setRows(new ArrayList<>());
				return data;
			}

			setPeriod(data, salaryDate);
			List<SalaryLedgerDetailRow> rows = detailDao.selectDetailRowsBySalaryDateId(conn, salaryDateId);
			data.setRows(rows);
			data.setSummary(toSummary(rows));
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void setPeriod(SalaryLedgerDetailData data, SalaryDate salaryDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		data.setPayYear(salaryDate.getPayYear());
		data.setPayMonth(salaryDate.getPayMonth());
		data.setPaySeq(salaryDate.getPaySeq());
		if (salaryDate.getStartDate() != null) {
			data.setStartDate(sdf.format(salaryDate.getStartDate()));
		}
		if (salaryDate.getEndDate() != null) {
			data.setEndDate(sdf.format(salaryDate.getEndDate()));
		}
		if (salaryDate.getPayDate() != null) {
			data.setPayDate(sdf.format(salaryDate.getPayDate()));
		}
	}

	private SalaryLedgerDetailRow toSummary(List<SalaryLedgerDetailRow> rows) {
		SalaryLedgerDetailRow summary = new SalaryLedgerDetailRow();
		for (SalaryLedgerDetailRow row : rows) {
			summary.setBasicPay(summary.getBasicPay() + row.getBasicPay());
			summary.setMealAllowance(summary.getMealAllowance() + row.getMealAllowance());
			summary.setChildcareAllowance(summary.getChildcareAllowance() + row.getChildcareAllowance());
			summary.setPositionAllowance(summary.getPositionAllowance() + row.getPositionAllowance());
			summary.setCarAllowance(summary.getCarAllowance() + row.getCarAllowance());
			summary.setSeniorityAllowance(summary.getSeniorityAllowance() + row.getSeniorityAllowance());
			summary.setNightDutyAllowance(summary.getNightDutyAllowance() + row.getNightDutyAllowance());
			summary.setBonus(summary.getBonus() + row.getBonus());
			summary.setHolidayAllowance(summary.getHolidayAllowance() + row.getHolidayAllowance());
		}
		summary.recalculateTotals();
		return summary;
	}
}
