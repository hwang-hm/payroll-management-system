package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryDateDao;
import salary.model.SalaryDate;

public class SaveSalaryDateService {
	private SalaryDateDao salaryDateDao = new SalaryDateDao();

	public boolean saveOrUpdate(SalaryDate salaryDate) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 동일 귀속연/월/차수 데이터가 존재하는지 확인
			SalaryDate existing = salaryDateDao.selectByYearMonthSeq(conn, salaryDate.getPayYear(),
					salaryDate.getPayMonth(), salaryDate.getPaySeq());

			boolean result;
			if (existing != null) {
				// 이미 존재하면 UPDATE
				result = salaryDateDao.update(conn, salaryDate);
			} else {
				// 존재하지 않으면 INSERT
				result = salaryDateDao.insert(conn, salaryDate);
			}

			conn.commit();
			return result;

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("SalaryDate 저장 처리 실패", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
