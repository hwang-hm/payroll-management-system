package attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.model.Attendance;

/**
 * 근태기록 데이터베이스 처리를 담당하는 DAO
 * 勤怠記録のデータベース処理を担当するDAO
 */
public class AttendanceDao {

	/**
	 * 전체 근태기록을 조회한다.
	 * すべての勤怠記録を照会する。
	 */
	public List<Attendance> selectAll(Connection conn) throws SQLException {

		List<Attendance> attendances = new ArrayList<>();

		String sql = "SELECT attendance_id, input_date, "
				+ "attendance_start_date, attendance_end_date, "
				+ "attendance_days, allowance_amount, remarks, "
				+ "emp_id, attendance_type_id "
				+ "FROM attendance "
				+ "ORDER BY attendance_id DESC";

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				attendances.add(createAttendance(rs));
			}
		}

		return attendances;
	}

	/**
	 * 근태기록 번호(PK)로 한 건을 조회한다.
	 * 勤怠記録番号(PK)で一件を照会する。
	 */
	public Attendance selectById(Connection conn, int attendanceId)
			throws SQLException {

		String sql = "SELECT attendance_id, input_date, "
				+ "attendance_start_date, attendance_end_date, "
				+ "attendance_days, allowance_amount, remarks, "
				+ "emp_id, attendance_type_id "
				+ "FROM attendance "
				+ "WHERE attendance_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, attendanceId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return createAttendance(rs);
				}
			}
		}

		return null;
	}

	/**
	 * 사원번호로 근태기록 목록을 조회한다.
	 * 社員番号で勤怠記録リストを照会する。
	 */
	public List<Attendance> selectByEmpId(Connection conn, int empId)
			throws SQLException {

		List<Attendance> attendances = new ArrayList<>();

		String sql = "SELECT attendance_id, input_date, "
				+ "attendance_start_date, attendance_end_date, "
				+ "attendance_days, allowance_amount, remarks, "
				+ "emp_id, attendance_type_id "
				+ "FROM attendance "
				+ "WHERE emp_id = ? "
				+ "ORDER BY attendance_start_date DESC, attendance_id DESC";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					attendances.add(createAttendance(rs));
				}
			}
		}

		return attendances;
	}

	/**
	 * 사원번호와 연월로 근태기록을 조회한다.
	 * 社員番号と年月で勤怠記録を照会する。
	 */
	public List<Attendance> selectByEmpIdAndYearMonth(
			Connection conn, int empId, int year, int month)
			throws SQLException {

		List<Attendance> attendances = new ArrayList<>();
		LocalDate firstDay = LocalDate.of(year, month, 1);
		java.sql.Date startDate = java.sql.Date.valueOf(firstDay);
		java.sql.Date endDate = java.sql.Date.valueOf(
				firstDay.plusMonths(1));

		String sql = "SELECT attendance_id, input_date, "
				+ "attendance_start_date, attendance_end_date, "
				+ "attendance_days, allowance_amount, remarks, "
				+ "emp_id, attendance_type_id "
				+ "FROM attendance "
				+ "WHERE emp_id = ? "
				+ "AND attendance_start_date >= ? "
				+ "AND attendance_start_date < ? "
				+ "ORDER BY attendance_start_date, attendance_id";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, endDate);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					attendances.add(createAttendance(rs));
				}
			}
		}

		return attendances;
	}

	/**
	 * 근태기록을 등록한다.
	 * 勤怠記録を登録する。
	 */
	public int insert(Connection conn, Attendance attendance)
			throws SQLException {

		String sql = "INSERT INTO attendance ("
				+ "attendance_id, input_date, "
				+ "attendance_start_date, attendance_end_date, "
				+ "attendance_days, allowance_amount, remarks, "
				+ "emp_id, attendance_type_id"
				+ ") VALUES ("
				+ "seq_attendance.NEXTVAL, SYSDATE, ?, ?, ?, ?, ?, ?, ?"
				+ ")";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, attendance.getAttendanceStartDate());

			if (attendance.getAttendanceEndDate() != null) {
				pstmt.setDate(2, attendance.getAttendanceEndDate());
			} else {
				pstmt.setNull(2, Types.DATE);
			}

			pstmt.setDouble(3, attendance.getAttendanceDays());
			pstmt.setInt(4, attendance.getAllowanceAmount());
			pstmt.setString(5, attendance.getRemarks());
			pstmt.setInt(6, attendance.getEmpId());
			pstmt.setInt(7, attendance.getAttendanceTypeId());

			return pstmt.executeUpdate();
		}
	}

	/**
	 * 근태기록을 수정한다.
	 * 勤怠記録を修正する。
	 */
	public int update(Connection conn, Attendance attendance)
			throws SQLException {

		String sql = "UPDATE attendance "
				+ "SET attendance_start_date = ?, "
				+ "attendance_end_date = ?, "
				+ "attendance_days = ?, "
				+ "allowance_amount = ?, "
				+ "remarks = ?, "
				+ "emp_id = ?, "
				+ "attendance_type_id = ? "
				+ "WHERE attendance_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, attendance.getAttendanceStartDate());

			if (attendance.getAttendanceEndDate() != null) {
				pstmt.setDate(2, attendance.getAttendanceEndDate());
			} else {
				pstmt.setNull(2, Types.DATE);
			}

			pstmt.setDouble(3, attendance.getAttendanceDays());
			pstmt.setInt(4, attendance.getAllowanceAmount());
			pstmt.setString(5, attendance.getRemarks());
			pstmt.setInt(6, attendance.getEmpId());
			pstmt.setInt(7, attendance.getAttendanceTypeId());
			pstmt.setInt(8, attendance.getAttendanceId());

			return pstmt.executeUpdate();
		}
	}

	/**
	 * 근태기록 번호(PK)로 한 건을 삭제한다.
	 * 勤怠記録番号(PK)で一件を削除する。
	 */
	public int delete(Connection conn, int attendanceId)
			throws SQLException {

		String sql = "DELETE FROM attendance "
				+ "WHERE attendance_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, attendanceId);

			return pstmt.executeUpdate();
		}
	}

	/**
	 * 조회 결과 한 행을 Attendance 객체로 변환한다.
	 * 照会結果の一行をAttendanceオブジェクトに変換する。
	 */
	private Attendance createAttendance(ResultSet rs) throws SQLException {

		Attendance attendance = new Attendance();

		attendance.setAttendanceId(rs.getInt("attendance_id"));
		attendance.setInputDate(rs.getDate("input_date"));
		attendance.setAttendanceStartDate(
				rs.getDate("attendance_start_date"));
		attendance.setAttendanceEndDate(
				rs.getDate("attendance_end_date"));
		attendance.setAttendanceDays(rs.getDouble("attendance_days"));
		attendance.setAllowanceAmount(rs.getInt("allowance_amount"));
		attendance.setRemarks(rs.getString("remarks"));
		attendance.setEmpId(rs.getInt("emp_id"));
		attendance.setAttendanceTypeId(
				rs.getInt("attendance_type_id"));

		return attendance;
	}
}
