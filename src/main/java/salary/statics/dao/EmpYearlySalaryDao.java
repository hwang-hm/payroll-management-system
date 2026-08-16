package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.EmpYearlySalaryDTO;

public class EmpYearlySalaryDao {
	// 특정 사원의 최근 10개년 연봉/공제액/실지급액 조회 (Map 형태)
	public Map<Integer, EmpYearlySalaryDTO> get10YearPersonalStatsMap(Connection conn, int empId, int targetYear)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, EmpYearlySalaryDTO> resultMap = new HashMap<>();

		int startYear = targetYear - 9;
		int endYear = targetYear;

		String sql = "SELECT " + "sd.pay_year AS pay_year, " + "ROUND(SUM(s.total_pay) / 1000) AS total_pay_thousand, "
				+ "ROUND(SUM(s.total_deduct) / 1000) AS total_deduct_thousand, "
				+ "ROUND(SUM(s.total_pay - s.total_deduct) / 1000) AS net_pay_thousand " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE s.emp_id = ? AND sd.pay_year BETWEEN ? AND ? " + "GROUP BY sd.pay_year "
				+ "ORDER BY sd.pay_year ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, startYear);
			pstmt.setInt(3, endYear);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int year = rs.getInt("pay_year");
				long totalPay = rs.getLong("total_pay_thousand");
				long totalDeduct = rs.getLong("total_deduct_thousand");
				long netPay = rs.getLong("net_pay_thousand");

				resultMap.put(year, new EmpYearlySalaryDTO(year, totalPay, totalDeduct, netPay));
			}
			return resultMap;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}
