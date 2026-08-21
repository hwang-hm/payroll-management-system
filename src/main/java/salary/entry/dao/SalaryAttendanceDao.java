package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// 급여입력용 근태 수당 합계 조회
public class SalaryAttendanceDao {

	// 사원·귀속연월 기준 attendance_type_id별 allowance_amount 합계
	public Map<Integer, Long> selectAllowanceSumByType(Connection conn, int empId, int year, int month)
			throws SQLException {
		LocalDate firstDay = LocalDate.of(year, month, 1);
		java.sql.Date startDate = java.sql.Date.valueOf(firstDay);
		java.sql.Date endDate = java.sql.Date.valueOf(firstDay.plusMonths(1));

		String sql = "SELECT attendance_type_id, NVL(SUM(allowance_amount), 0) AS allowance_sum "
				+ "FROM attendance "
				+ "WHERE emp_id = ? "
				+ "AND attendance_start_date >= ? "
				+ "AND attendance_start_date < ? "
				+ "GROUP BY attendance_type_id";

		Map<Integer, Long> sums = new HashMap<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, endDate);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					sums.put(rs.getInt("attendance_type_id"), rs.getLong("allowance_sum"));
				}
			}
		}
		return sums;
	}
}
