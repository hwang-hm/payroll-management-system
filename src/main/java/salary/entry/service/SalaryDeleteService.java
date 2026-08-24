package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.transfer.dao.TransferHistoryDao;
import salary.util.PayPeriodParams;

public class SalaryDeleteService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final TransferHistoryDao transferHistoryDao = new TransferHistoryDao();
	private final SalaryDateService salaryDateService = new SalaryDateService();

	// 체크한 사원만 지움. 이번 귀속년월 급여만. 이체 이력도 같이 삭제
	// チェックした社員のみ削除。今回帰属年月の給与のみ。振込履歴も同時削除
	public void deleteSelected(PayPeriodParams period, List<Integer> salaryIds) {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int salaryDateId = salaryDateService.ensureByPeriod(conn, period).getSalaryDateId();
			List<Integer> validIds = salaryDao.selectIdsBySalaryDateIdAndIds(conn, salaryDateId, salaryIds);
			deleteSalariesWithTransfer(conn, validIds);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 이번 귀속년월 정규직 급여 전부 지움. 일용직은 그대로. 이체 이력도 같이 삭제
	// 今回帰属年月の正規社員給与を全削除。日雇いはそのまま。振込履歴も同時削除
	public void deleteAll(PayPeriodParams period) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int salaryDateId = salaryDateService.ensureByPeriod(conn, period).getSalaryDateId();
			List<Salary> salaries = salaryDao.selectBySalaryDateIdExcludingDayworkers(conn, salaryDateId);
			List<Integer> ids = new ArrayList<>();
			for (Salary salary : salaries) {
				ids.add(salary.getSalaryId());
			}
			deleteSalariesWithTransfer(conn, ids);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 해당 salary_date의 급여 전부 지움 (일용직 포함). 급여대장 행 삭제용
	// 該当 salary_date の給与を全削除 (日雇い含む)。給与台帳行削除用
	public void deleteAllBySalaryDateId(int salaryDateId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			List<Integer> ids = salaryDao.selectIdsBySalaryDateId(conn, salaryDateId);
			deleteSalariesWithTransfer(conn, ids);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// transfer_history → salary_detail → salary 순으로 삭제
	// transfer_history → salary_detail → salary の順で削除
	private void deleteSalariesWithTransfer(Connection conn, List<Integer> salaryIds) throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		transferHistoryDao.deleteBySalaryIds(conn, salaryIds);
		salaryDetailDao.deleteBySalaryIds(conn, salaryIds);
		salaryDao.deleteByIds(conn, salaryIds);
	}
}
