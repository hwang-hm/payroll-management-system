package salary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;
import salary.model.SalaryItem;

public class GetSalaryItemService {

	SalaryItemDao itemDao = new SalaryItemDao();

	// 단일 조회
	public SalaryItem getItemById(int itemId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return itemDao.selectById(conn, itemId);
		} catch (SQLException e) {
			throw new RuntimeException("급여항목 조회 실패 PK: " + itemId, e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 타입별 목록 조회
	public List<SalaryItem> getItemsByType(String itemType) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return itemDao.selectByType(conn, itemType);
		} catch (SQLException e) {
			throw new RuntimeException("급여항목 목록 조회 실패", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
