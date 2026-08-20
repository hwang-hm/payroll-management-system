package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.transfer.dao.TransferHistoryDao;

public class SalaryDeleteService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final TransferHistoryDao transferHistoryDao = new TransferHistoryDao();

	// 체크한 사원만 지움. 이번 귀속년월 급여만
	public void deleteSelected(int salaryDateId, List<Integer> salaryIds) {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			for (Integer salaryId : salaryIds) {
				if (salaryId == null) {
					continue;
				}
				Salary salary = salaryDao.selectById(conn, salaryId);
				if (salary == null || salary.getSalaryDateId() == null
						|| salary.getSalaryDateId() != salaryDateId) {
					continue;
				}
				deleteOne(conn, salaryId);
			}
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 이번 귀속년월 정규직 급여 전부 지움. 일용직은 그대로. 이체 이력 있는 건은 건너뜀
	public void deleteAll(int salaryDateId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			List<Salary> salaries = salaryDao.selectBySalaryDateIdExcludingDayworkers(conn, salaryDateId);
			for (Salary salary : salaries) {
				if (transferHistoryDao.existsBySalaryId(conn, salary.getSalaryId())) {
					continue;
				}
				deleteSalaryOnly(conn, salary.getSalaryId());
			}
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private void deleteOne(Connection conn, int salaryId) throws SQLException {
		if (transferHistoryDao.existsBySalaryId(conn, salaryId)) {
			throw new IllegalStateException("이체 이력이 있는 급여는 삭제할 수 없습니다.");
		}
		deleteSalaryOnly(conn, salaryId);
	}

	private void deleteSalaryOnly(Connection conn, int salaryId) throws SQLException {
		salaryDetailDao.deleteBySalaryId(conn, salaryId);
		salaryDao.deleteById(conn, salaryId);
	}
}
