package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.MonthlySalaryDTO;

/**
 * 월별 급여 통계 데이터 액세스 객체 (DAO)
 * 月別給与統計データアクセスオブジェクト (DAO)
 */
public class MonthlySalaryDao {

	/**
	 * 선택한 연도(targetYear)의 1~12월 총 급여액 및 집계 인원 결과 조회 (월별 Map 반환)
	 * 選択した年度(targetYear)の1〜12月の給与総額および集計人数結果を照会（月別Map返却）
	 */
	public Map<Integer, MonthlySalaryDTO> getMonthlyStatsMap(Connection conn, int targetYear) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, MonthlySalaryDTO> resultMap = new HashMap<>();

		// 월별 총 급여액(천 원 단위 반올림) 및 중복을 제외한 급여 지급 대상 인원수를 집계하는 SQL
		// 月別の給与総額（千円単位四捨五入）および重複を除いた給与支給対象人数を集計するSQL
		String sql = "SELECT " + "sd.pay_month AS pay_month, "
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_salary_thousand, " + "COUNT(DISTINCT s.emp_id) AS emp_count "
				+ "FROM salary s " + "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE sd.pay_year = ? " + "GROUP BY sd.pay_month " + "ORDER BY sd.pay_month ASC";

		try {
			// 바인딩 변수 세팅 (조회 대상 연도) 및 쿼리 실행
			// バインド変数の設定（照会対象年度）およびクエリ実行
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, targetYear);
			rs = pstmt.executeQuery();

			// 조회 결과셋을 순회하며 DTO 변환 후 월(Key: 1~12) 기준 Map에 저장
			// 照会結果セットをループ処理し、DTO変換後、月(Key: 1〜12)基準でMapに保存
			while (rs.next()) {
				MonthlySalaryDTO dto = toDTO(rs);
				resultMap.put(dto.getMonth(), dto);
			}
			return resultMap;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * ResultSet의 현재 행 데이터를 MonthlySalaryDTO 객체로 매핑
	 * ResultSetの現在行データをMonthlySalaryDTOオブジェクトへマッピング
	 */
	private MonthlySalaryDTO toDTO(ResultSet rs) throws SQLException {
		int month = rs.getInt("pay_month");
		long totalSalary = rs.getLong("total_salary_thousand");
		double empCount = rs.getDouble("emp_count");
		return new MonthlySalaryDTO(month, totalSalary, empCount);
	}
}