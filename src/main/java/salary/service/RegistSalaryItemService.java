package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;
import salary.model.SalaryItem;
import salary.service.request.RegistItemRequest;

public class RegistSalaryItemService {
	private SalaryItemDao itemDao = new SalaryItemDao();

	// 1. 등록 및 수정
	public boolean saveOrUpdate(RegistItemRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			SalaryItem item = toItem(req);

			boolean result;
			if (item.getSalaryItemId() != null) {
				result = itemDao.update(conn, item);
			} else {
				result = itemDao.insert(conn, item);
			}

			conn.commit();
			return result;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여항목 저장 실패", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private SalaryItem toItem(RegistItemRequest req) {
		return new SalaryItem(req.getSalaryItemId(), req.getAttendanceTypeId(), req.getItemType(), req.getItemName(),
				req.getTaxYn(), req.getTaxFreeName(), req.getTaxFreeLimit(), req.getCalcMethod(), req.getRoundUnit(),
				req.getDefaultAmount(), req.getRemark(), req.getUseYn(), null, null);
	}
}
