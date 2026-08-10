package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.SalaryDetail;

public class SalarySaveDetailService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 급여상세 업데이터 하면서, salary테이블의 총합도 같이 업데이트 해준다.
	public void save(int salaryId, List<Integer> detailIds, List<Long> amounts, List<String> calcMethods) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			for (int i = 0; i < detailIds.size(); i++) {
				int detailId = detailIds.get(i);
				long amount = amounts.get(i) != null ? amounts.get(i) : 0L;
				String calcMethod = calcMethods.get(i);
				salaryDetailDao.updateAmountAndCalcMethod(conn, detailId, amount, calcMethod);
			}

			recalculateTotals(conn, salaryId);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
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
