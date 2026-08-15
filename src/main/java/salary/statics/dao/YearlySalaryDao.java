package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.YearlySalaryDto;

public class YearlySalaryDao {
	// 선택한 연도 기준 과거 10개년(targetYear - 9 ~ targetYear) 급여 및 인원 집계
	public Map<Integer, YearlySalaryDto> getYearlyStatsMap(Connection conn, int targetYear) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, YearlySalaryDto> resultMap = new HashMap<>();

		int startYear = targetYear - 9;
		int endYear = targetYear;

		String sql = "SELECT " + "sd.pay_year AS pay_year, " // 귀속년
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_salary_thousand, " // 총 지급액 합(1000원 단위)
				+ "ROUND(COUNT(DISTINCT s.emp_id) / 12.0, 1) AS avg_emp_count " // 사원수 평균(총사원수/12개월)
				+ "FROM salary s " + "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE sd.pay_year BETWEEN ? AND ? " + "GROUP BY sd.pay_year " + "ORDER BY sd.pay_year ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startYear);
			pstmt.setInt(2, endYear);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				YearlySalaryDto dto = toDTO(rs);
				resultMap.put(dto.getYear(), dto);
			}
			return resultMap;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	private YearlySalaryDto toDTO(ResultSet rs) throws SQLException {
		int year = rs.getInt("pay_year");
		long totalSalary = rs.getLong("total_salary_thousand");
		double avgEmpCount = rs.getDouble("avg_emp_count");
		return new YearlySalaryDto(year, totalSalary, avgEmpCount);
	}
}