package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;

public class DeleteSalaryItemService {

	SalaryItemDao itemDao = new SalaryItemDao();

	// 2. 삭제
	public boolean deleteItem(Long salaryItemId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			boolean result = itemDao.delete(conn, salaryItemId);

			conn.commit();
			return result;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여항목 삭제 실패", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
