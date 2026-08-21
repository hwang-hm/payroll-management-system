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
import salary.util.PayPeriodParams;

public class SalaryLoadPreviousService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDateService salaryDateService = new SalaryDateService();
	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 선택한 지난 귀속년월 급여를 이번 달로 복사. 이미 있는 사원은 건너뜀
	public void load(PayPeriodParams target, PayPeriodParams source) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			SalaryDate sourceDate = salaryDateDao.selectByPeriod(conn, source.getPayYear(), source.getPayMonth(),
					source.getPaySeq());
			if (sourceDate == null) {
				conn.commit();
				return;
			}

			SalaryDate targetDate = salaryDateService.ensureByPeriod(conn, target);
			if (sourceDate.getSalaryDateId() == targetDate.getSalaryDateId()) {
				conn.commit();
				return;
			}

			List<Salary> sourceSalaries = salaryDao.selectBySalaryDateIdExcludingDayworkers(conn,
					sourceDate.getSalaryDateId());
			for (Salary sourceSalary : sourceSalaries) {
				if (salaryDao.existsBySalaryDateIdAndEmpId(conn, targetDate.getSalaryDateId(), sourceSalary.getEmpId())) {
					continue;
				}
				Salary copied = new Salary();
				copied.setSalaryDateId(targetDate.getSalaryDateId());
				copied.setEmpId(sourceSalary.getEmpId());
				copied.setTotalPay(sourceSalary.getTotalPay());
				copied.setTotalDeduct(sourceSalary.getTotalDeduct());
				copied.setNetPay(sourceSalary.getNetPay());
				int newSalaryId = salaryDao.insert(conn, copied);
				copyDetails(conn, sourceSalary.getSalaryId(), newSalaryId);
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
		for (SalaryDetail sourceDetail : salaryDetailDao.selectBySalaryId(conn, sourceSalaryId)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(targetSalaryId);
			detail.setSalaryItemId(sourceDetail.getSalaryItemId());
			detail.setAttendanceTypeId(sourceDetail.getAttendanceTypeId());
			detail.setItemType(sourceDetail.getItemType());
			detail.setItemName(sourceDetail.getItemName());
			detail.setTaxYn(sourceDetail.getTaxYn());
			detail.setTaxFreeName(sourceDetail.getTaxFreeName());
			detail.setTaxFreeLimit(sourceDetail.getTaxFreeLimit());
			detail.setCalcMethod(sourceDetail.getCalcMethod());
			detail.setRoundUnit(sourceDetail.getRoundUnit());
			detail.setDefaultAmount(sourceDetail.getDefaultAmount());
			detail.setMemo(sourceDetail.getMemo());
			detail.setAmount(sourceDetail.getAmount());
			salaryDetailDao.insert(conn, detail);
		}
	}
}
