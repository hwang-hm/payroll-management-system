package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDateDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;

public class SalaryLoadPreviousService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDateService salaryDateService = new SalaryDateService();
	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 선택한 지난 귀속년월 급여를 이번 달로 복사. 이미 있는 사원은 건너뜀
	public void load(int targetYear, int targetMonth, int targetSeq, int sourceYear, int sourceMonth, int sourceSeq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			SalaryDate sourceDate = salaryDateDao.selectByPeriod(conn, sourceYear, sourceMonth, sourceSeq);
			if (sourceDate == null) {
				conn.commit();
				return;
			}

			SalaryDate targetDate = salaryDateService.ensureByPeriod(conn, targetYear, targetMonth, targetSeq);
			if (sourceDate.getSalaryDateId() == targetDate.getSalaryDateId()) {
				conn.commit();
				return;
			}

			List<Salary> sourceSalaries = salaryDao.selectBySalaryDateIdExcludingDayworkers(conn,
					sourceDate.getSalaryDateId());
			for (Salary source : sourceSalaries) {
				if (salaryDao.existsBySalaryDateIdAndEmpId(conn, targetDate.getSalaryDateId(), source.getEmpId())) {
					continue;
				}
				Salary copied = new Salary();
				copied.setSalaryDateId(targetDate.getSalaryDateId());
				copied.setEmpId(source.getEmpId());
				copied.setTotalPay(source.getTotalPay());
				copied.setTotalDeduct(source.getTotalDeduct());
				copied.setNetPay(source.getNetPay());
				int newSalaryId = salaryDao.insert(conn, copied);
				copyDetails(conn, source.getSalaryId(), newSalaryId);
			}

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private void copyDetails(Connection conn, int sourceSalaryId, int targetSalaryId) throws SQLException {
		for (SalaryDetail source : salaryDetailDao.selectBySalaryId(conn, sourceSalaryId)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(targetSalaryId);
			detail.setSalaryItemId(source.getSalaryItemId());
			detail.setAttendanceTypeId(source.getAttendanceTypeId());
			detail.setItemType(source.getItemType());
			detail.setItemName(source.getItemName());
			detail.setTaxYn(source.getTaxYn());
			detail.setTaxFreeName(source.getTaxFreeName());
			detail.setTaxFreeLimit(source.getTaxFreeLimit());
			detail.setCalcMethod(source.getCalcMethod());
			detail.setRoundUnit(source.getRoundUnit());
			detail.setDefaultAmount(source.getDefaultAmount());
			detail.setMemo(source.getMemo());
			detail.setAmount(source.getAmount());
			salaryDetailDao.insert(conn, detail);
		}
	}
}
