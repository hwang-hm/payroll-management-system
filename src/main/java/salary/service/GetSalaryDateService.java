package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryDateDao;
import salary.model.SalaryDate;

public class GetSalaryDateService {
	private SalaryDateDao salaryDateDao = new SalaryDateDao();

	public SalaryDate getSalaryDate(int year, int month, int seq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return salaryDateDao.selectByYearMonthSeq(conn, year, month, seq);
		} catch (SQLException e) {
			throw new RuntimeException("SalaryDate 조회 실패", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
