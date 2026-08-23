package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.EmpMonthlySalaryDTO;

/**
 * 개인별 월별 급여 통계 데이터 액세스 객체 (DAO)
 * 個人別月別給与統計データアクセスオブジェクト (DAO)
 */
public class EmpMonthlySalaryDao {

	/**
	 * 특정 사원의 지정 연도 12개월 급여, 공제액, 실지급액 통계 조회 (월별 Map 반환)
	 * 特定社員の指定年度12ヶ月の給与、控除額、手取り額統計を照会（月別Map返却）
	 */
	public Map<Integer, EmpMonthlySalaryDTO> getMonthlyPersonalStatsMap(Connection conn, int empId, int targetYear)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, EmpMonthlySalaryDTO> resultMap = new HashMap<>();

		// 월별 Total 지급액, 공제액, 실지급액을 천 원 단위로 반올림(ROUND) 집계하는 SQL
		// 月別の支給総額、控除総額、手取り額を千円単位で四捨五入(ROUND)集計するSQL
		String sql = "SELECT " + "sd.pay_month AS pay_month, "
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_pay_thousand, "
				+ "ROUND(SUM(s.total_deduct) / 1000) AS total_deduct_thousand, "
				+ "ROUND(SUM(s.total_pay - s.total_deduct) / 1000) AS net_pay_thousand " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE s.emp_id = ? AND sd.pay_year = ? " + "GROUP BY sd.pay_month " + "ORDER BY sd.pay_month ASC";

		try {
			// 바인딩 변수 세팅 (사원 ID, 조회 대상 연도) 및 쿼리 실행
			// バインド変数の設定（社員ID、照会対象年度）およびクエリ実行
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, targetYear);
			rs = pstmt.executeQuery();

			// 조회 결과셋을 순회하며 월별 DTO 매핑 후 Map에 추가
			// 照会結果セットをループ処理し、月別DTOマッピング後Mapに追加
			while (rs.next()) {
				int month = rs.getInt("pay_month");
				long totalPay = rs.getLong("total_pay_thousand");
				long totalDeduct = rs.getLong("total_deduct_thousand");
				long netPay = rs.getLong("net_pay_thousand");

				resultMap.put(month, new EmpMonthlySalaryDTO(month, totalPay, totalDeduct, netPay));
			}
			return resultMap;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}