package salary.statics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jdbc.JdbcUtil;
import salary.statics.service.request.EmpMonthlySalaryDTO;

public class EmpMonthlySalaryDao {
	// 특정 사원의 12개월 연봉/공제액/실지급액 조회 (Map 형태)
	public Map<Integer, EmpMonthlySalaryDTO> getMonthlyPersonalStatsMap(Connection conn, int empId, int targetYear)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, EmpMonthlySalaryDTO> resultMap = new HashMap<>();

		String sql = "SELECT " + "sd.pay_month AS pay_month, "
				+ "ROUND(SUM(s.total_pay) / 1000) AS total_pay_thousand, "
				+ "ROUND(SUM(s.total_deduct) / 1000) AS total_deduct_thousand, "
				+ "ROUND(SUM(s.total_pay - s.total_deduct) / 1000) AS net_pay_thousand " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE s.emp_id = ? AND sd.pay_year = ? " + "GROUP BY sd.pay_month " + "ORDER BY sd.pay_month ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.setInt(2, targetYear);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int month = rs.getInt("pay_month");
				long totalPay = rs.getLong("total_pay_thousand");
				long totalDeduct = rs.getLong("total_deduct_thousand");
				long netPay = rs.getLong("net_pay_thousand");

				resultMap.put(month, new EmpMonthlySalaryDTO(month, totalPay, totalDeduct, netPay));
			}
			return resultMap;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}
