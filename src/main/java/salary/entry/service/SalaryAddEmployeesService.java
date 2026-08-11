package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.entry.dao.SalaryItemDao;
import salary.model.Salary;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;

public class SalaryAddEmployeesService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final SalaryItemDao salaryItemDao = new SalaryItemDao();

	public void add(int salaryDateId, List<Integer> empIds) {
		if (empIds == null || empIds.isEmpty()) {
			return;
		}

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Integer templateSalaryId = salaryDao.selectFirstSalaryIdBySalaryDateId(conn, salaryDateId);

			for (Integer empId : empIds) {
				if (empId == null || salaryDao.existsBySalaryDateIdAndEmpId(conn, salaryDateId, empId)) {
					continue;
				}

				Salary salary = new Salary();
				salary.setSalaryDateId(salaryDateId);
				salary.setEmpId(empId);
				salary.setTotalPay(0L);
				salary.setTotalDeduct(0L);
				salary.setNetPay(0L);

				int salaryId = salaryDao.insert(conn, salary);
				createDetails(conn, salaryId, templateSalaryId);
				recalculateTotals(conn, salaryId);

				if (templateSalaryId == null) {
					templateSalaryId = salaryId;
				}
			}

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private void createDetails(Connection conn, int salaryId, Integer templateSalaryId) throws SQLException {
		if (templateSalaryId != null) {
			createDetailsFromTemplate(conn, salaryId, templateSalaryId);
			return;
		}
		createDetailsFromSalaryItems(conn, salaryId);
	}

	private void createDetailsFromSalaryItems(Connection conn, int salaryId) throws SQLException {
		for (SalaryItem item : salaryItemDao.selectActiveItems(conn)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(salaryId);
			detail.setSalaryItemId(item.getSalaryItemId());
			detail.setAttendanceTypeId(item.getAttendanceTypeId());
			detail.setItemType(item.getItemType());
			detail.setItemName(item.getItemName());
			detail.setTaxYn(item.getTaxYn());
			detail.setTaxFreeName(item.getTaxFreeName());
			detail.setTaxFreeLimit(item.getTaxFreeLimit());
			detail.setCalcMethod(item.getCalcMethod());
			detail.setRoundUnit(item.getRoundUnit());
			detail.setDefaultAmount(item.getDefaultAmount());
			detail.setMemo(item.getRemark());
			detail.setAmount(resolveInitialAmount(item.getDefaultAmount()));
			salaryDetailDao.insert(conn, detail);
		}
	}

	private void createDetailsFromTemplate(Connection conn, int salaryId, int templateSalaryId) throws SQLException {
		for (SalaryDetail template : salaryDetailDao.selectBySalaryId(conn, templateSalaryId)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(salaryId);
			detail.setSalaryItemId(template.getSalaryItemId());
			detail.setAttendanceTypeId(template.getAttendanceTypeId());
			detail.setItemType(template.getItemType());
			detail.setItemName(template.getItemName());
			detail.setTaxYn(template.getTaxYn());
			detail.setTaxFreeName(template.getTaxFreeName());
			detail.setTaxFreeLimit(template.getTaxFreeLimit());
			detail.setCalcMethod(template.getCalcMethod());
			detail.setRoundUnit(template.getRoundUnit());
			detail.setDefaultAmount(template.getDefaultAmount());
			detail.setMemo(template.getMemo());
			detail.setAmount(0L);
			salaryDetailDao.insert(conn, detail);
		}
	}

	private long resolveInitialAmount(Long defaultAmount) {
		if (defaultAmount != null && defaultAmount > 0) {
			return defaultAmount;
		}
		return 0L;
	}

	private void recalculateTotals(Connection conn, int salaryId) throws SQLException {
		long totalPay = 0;
		long totalDeduct = 0;
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			long amount = detail.getAmount() != null ? detail.getAmount() : 0L;
			if ("PAY".equals(detail.getItemType())) {
				totalPay += amount;
			} else {
				totalDeduct += amount;
			}
		}
		salaryDao.updateTotals(conn, salaryId, totalPay, totalDeduct, totalPay - totalDeduct);
	}
}
