package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.SalaryDetail;
import salary.util.SalaryTotals;

public class SalarySaveDetailService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 급여상세 업데이터 하면서, salary테이블의 총합도 같이 업데이트 해준다.(salary 레코드 한 행의 급여상세 총합임)
	// 給与詳細を更新しつつ、salaryテーブルの合計も一緒に更新する。(salaryレコード1行の給与詳細合計)
	public void save(int salaryId, List<Integer> detailIds, List<Long> amounts, List<String> calcMethods) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			salaryDetailDao.updateAmountAndCalcMethodBatch(conn, detailIds, amounts, calcMethods);

			List<SalaryDetail> details = salaryDetailDao.selectBySalaryId(conn, salaryId);
			long[] totals = SalaryTotals.fromDetails(details);
			salaryDao.updateTotals(conn, salaryId, totals[0], totals[1], totals[2]);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
