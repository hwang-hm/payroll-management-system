package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import salary.util.SqlIn;

// 급여입력용 근태 수당 합계 조회
// 給与入力用勤怠手当合計の照会
public class SalaryAttendanceDao {

	// 사원·귀속연월 기준 attendance_type_id별 allowance_amount 합계
	// 社員・帰属年月基準で attendance_type_id 別 allowance_amount 合計
	public Map<Integer, Long> selectAllowanceSumByType(Connection conn, int empId, int year, int month)
			throws SQLException {
		Map<Integer, Map<Integer, Long>> all = selectAllowanceSumByEmpIds(conn,
				Collections.singletonList(empId), year, month);
		Map<Integer, Long> sums = all.get(empId);
		return sums != null ? sums : Collections.emptyMap();
	}

	// 여러 사원의 근태수당 합계. empId → (attendanceTypeId → sum)
	// 複数社員の勤怠手当合計。empId → (attendanceTypeId → sum)
	public Map<Integer, Map<Integer, Long>> selectAllowanceSumByEmpIds(Connection conn, List<Integer> empIds,
			int year, int month) throws SQLException {
		Map<Integer, Map<Integer, Long>> result = new HashMap<>();
		if (empIds == null || empIds.isEmpty()) {
			return result;
		}

		LocalDate firstDay = LocalDate.of(year, month, 1);
		java.sql.Date startDate = java.sql.Date.valueOf(firstDay);
		java.sql.Date endDate = java.sql.Date.valueOf(firstDay.plusMonths(1));

		String sql = "SELECT emp_id, attendance_type_id, NVL(SUM(allowance_amount), 0) AS allowance_sum "
				+ "FROM attendance "
				+ "WHERE emp_id IN (" + SqlIn.placeholders(empIds.size()) + ") "
				+ "AND attendance_start_date >= ? "
				+ "AND attendance_start_date < ? "
				+ "GROUP BY emp_id, attendance_type_id";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int idx = SqlIn.setInts(pstmt, 1, empIds);
			pstmt.setDate(idx, startDate);
			pstmt.setDate(idx + 1, endDate);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int empId = rs.getInt("emp_id");
					int typeId = rs.getInt("attendance_type_id");
					long sum = rs.getLong("allowance_sum");
					result.computeIfAbsent(empId, k -> new HashMap<>()).put(typeId, sum);
				}
			}
		}
		return result;
	}
}
