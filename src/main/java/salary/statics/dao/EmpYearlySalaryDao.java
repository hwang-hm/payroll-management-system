package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.EmpYearlySalaryDTO;

/**
 * 개인별 연도별 급여 통계 데이터 액세스 객체 (DAO)
 * 個人別年度別給与統計データアクセスオブジェクト (DAO)
 */
public class EmpYearlySalaryDao {

	/**
	 * 특정 사원의 지정 연도 기준 최근 10개년 급여, 공제액, 실지급액 통계 조회 (연도별 Map 반환)
	 * 特定社員の指定年度基準直近10年間の給与、控除額、手取り額統計を照会（年度別Map返却）
	 */
	public Map<Integer, EmpYearlySalaryDTO> get10YearPersonalStatsMap(Connection conn, int empId, int targetYear)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, EmpYearlySalaryDTO> resultMap = new HashMap<>();

		// 조회 대상 10개년 시작 연도 및 종료 연도 계산
		// 照会対象10年間の開始年度および終了年度の計算
		int startYear = targetYear - 9;
		int endYear = targetYear;

		// 최근 10개년 동안의 연도별 총 지급액, 공제액, 실지급액을 천 원 단위로 반올림(ROUND) 집계하는 SQL
		// 直近10年間の年度別支給総額、控除総額、手取り額を千円単位で四捨五入(ROUND)集計するSQL
		String sql = "SELECT " + "sd.pay_year AS pay_year, " + "ROUND(SUM(s.total_pay) / 1000) AS total_pay_thousand, "
				+ "ROUND(SUM(s.total_deduct) / 1000) AS total_deduct_thousand, "
				+ "ROUND(SUM(s.total_pay - s.total_deduct) / 1000) AS net_pay_thousand " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE s.emp_id = ? AND sd.pay_year BETWEEN ? AND ? " + "GROUP BY sd.pay_year "
				+ "ORDER BY sd.pay_year ASC";

		try {
			// 바인딩 변수 세팅 (사원 ID, 시작 연도, 종료 연도) 및 쿼리 실행
			// バインド変数の設定（社員ID、開始年度、終了年度）およびクエリ実行
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, startYear);
			pstmt.setInt(3, endYear);
			rs = pstmt.executeQuery();

			// 조회 결과셋을 순회하며 연도별 DTO 매핑 후 Map에 추가
			// 照会結果セットをループ処理し、年度別DTOマッピング後Mapに追加
			while (rs.next()) {
				int year = rs.getInt("pay_year");
				long totalPay = rs.getLong("total_pay_thousand");
				long totalDeduct = rs.getLong("total_deduct_thousand");
				long netPay = rs.getLong("net_pay_thousand");

				resultMap.put(year, new EmpYearlySalaryDTO(year, totalPay, totalDeduct, netPay));
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