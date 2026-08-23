package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDateDao;
import salary.model.SalaryDate;

/**
 * 급여 정산일자 조회 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 給与精算日時の照会ビジネスロジックを処理するサービス類です。
 */
public class GetSalaryDateService {
	private SalaryDateDao salaryDateDao = new SalaryDateDao();

	/**
	 * 귀속연도, 귀속월, 급여차수로 정산일자 정보를 조회합니다. 帰属年、帰属月、給与回数で精算日時情報を照会します。
	 * 
	 * @param year  귀속연도 / 帰属年
	 * @param month 귀속월 / 帰属月
	 * @param seq   급여차수 / 給与回数
	 * @return SalaryDate 정산일자 객체 / 精算日時オブジェクト
	 */
	public SalaryDate getSalaryDate(int year, int month, int seq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return salaryDateDao.selectByYearMonthSeq(conn, year, month, seq);
		} catch (SQLException e) {
			// 조회 실패 시 예외 처리
			// 照会失敗時の例外処理
			throw new RuntimeException("SalaryDate 조회 실패 / SalaryDateの照会に失敗しました", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}