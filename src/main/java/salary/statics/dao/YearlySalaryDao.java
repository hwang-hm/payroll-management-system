package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.YearlySalaryDTO;

/**
 * 연도별 급여 통계 데이터 액세스 객체 (DAO)
 * 年度別給与統計データアクセスオブジェクト (DAO)
 */
public class YearlySalaryDao {

	/**
	 * 선택한 연도 기준 과거 10개년(targetYear - 9 ~ targetYear) 급여 및 인원 집계 결과 조회 (연도별 Map 반환)
	 * 選択した年度基準の過去10年間(targetYear - 9 ~ targetYear)の給与および人数集計結果を照会（年度別Map返却）
	 */
	public Map<Integer, YearlySalaryDTO> getYearlyStatsMap(Connection conn, int targetYear) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, YearlySalaryDTO> resultMap = new HashMap<>();

		// 조회 대상 10개년 시작 연도 및 종료 연도 계산
		// 照会対象10年間の開始年度および終了年度の計算
		int startYear = targetYear - 9;
		int endYear = targetYear;

		// 연도별 총 급여액(천 원 단위 반올림) 및 월평균 사원수(총 인원수/12개월)를 집계하는 SQL
		// 年度別の給与総額（千円単位四捨五入）および月平均社員数（総人数/12ヶ月）を集計するSQL
		String sql = "SELECT " + "sd.pay_year AS pay_year, "
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_salary_thousand, "
				+ "ROUND(COUNT(DISTINCT s.emp_id) / 12.0, 1) AS avg_emp_count "
				+ "FROM salary s " + "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE sd.pay_year BETWEEN ? AND ? " + "GROUP BY sd.pay_year " + "ORDER BY sd.pay_year ASC";

		try {
			// 바인딩 변수 세팅 (시작 연도, 종료 연도) 및 쿼리 실행
			// バインド変数の設定（開始年度、終了年度）およびクエリ実行
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startYear);
			pstmt.setInt(2, endYear);
			rs = pstmt.executeQuery();

			// 조회 결과셋을 순회하며 DTO 변환 후 연도(Key) 기준 Map에 저장
			// 照会結果セットをループ処理し、DTO変換後、年度(Key)基準でMapに保存
			while (rs.next()) {
				YearlySalaryDTO dto = toDTO(rs);
				resultMap.put(dto.getYear(), dto);
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
	 * ResultSet의 현재 행 데이터를 YearlySalaryDTO 객체로 매핑
	 * ResultSetの現在行データをYearlySalaryDTOオブジェクトへマッピング
	 */
	private YearlySalaryDTO toDTO(ResultSet rs) throws SQLException {
		int year = rs.getInt("pay_year");
		long totalSalary = rs.getLong("total_salary_thousand");
		double avgEmpCount = rs.getDouble("avg_emp_count");
		return new YearlySalaryDTO(year, totalSalary, avgEmpCount);
	}
}