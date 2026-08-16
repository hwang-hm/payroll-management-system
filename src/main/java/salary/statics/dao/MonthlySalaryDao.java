package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.MonthlySalaryDTO;

public class MonthlySalaryDao {

	// 선택한 연도(targetYear)의 1~12월 급여 및 인원 집계 결과를 반환
	public Map<Integer, MonthlySalaryDTO> getMonthlyStatsMap(Connection conn, int targetYear) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, MonthlySalaryDTO> resultMap = new HashMap<>();

		String sql = "SELECT " + "sd.pay_month AS pay_month, "
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_salary_thousand, " + "COUNT(DISTINCT s.emp_id) AS emp_count "
				+ "FROM salary s " + "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE sd.pay_year = ? " + "GROUP BY sd.pay_month " + "ORDER BY sd.pay_month ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, targetYear);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MonthlySalaryDTO dto = toDTO(rs);
				resultMap.put(dto.getMonth(), dto); // Key: 1 ~ 12 (월)
			}
			return resultMap;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	private MonthlySalaryDTO toDTO(ResultSet rs) throws SQLException {
		int month = rs.getInt("pay_month");
		long totalSalary = rs.getLong("total_salary_thousand");
		double empCount = rs.getDouble("emp_count");
		return new MonthlySalaryDTO(month, totalSalary, empCount);
	}
}
