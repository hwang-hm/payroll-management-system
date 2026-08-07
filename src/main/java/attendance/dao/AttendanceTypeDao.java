package attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import attendance.model.AttendanceType;

public class AttendanceTypeDao {

	public List<AttendanceType> selectAll(Connection conn) throws SQLException {

		List<AttendanceType> attendanceTypes = new ArrayList<>();

		String sql = "SELECT attendance_type_id, attendance_type, unit, "
				+ "group_name, use_yn, vacation_type_id "
				+ "FROM attendance_type "
				+ "ORDER BY attendance_type_id";

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				AttendanceType attendanceType = new AttendanceType();

				attendanceType.setAttendanceTypeId(rs.getInt("attendance_type_id"));
				attendanceType.setAttendanceType(rs.getString("attendance_type"));
				attendanceType.setUnit(rs.getString("unit"));
				attendanceType.setGroupName(rs.getString("group_name"));
				attendanceType.setUseYn(rs.getString("use_yn"));
				attendanceType.setVacationTypeId(rs.getInt("vacation_type_id"));

				attendanceTypes.add(attendanceType);
			}
		}

		return attendanceTypes;
	}

	public AttendanceType selectById(Connection conn, int attendanceTypeId) throws SQLException {

		String sql = "SELECT attendance_type_id, attendance_type, unit, "
				+ "group_name, use_yn, vacation_type_id "
				+ "FROM attendance_type "
				+ "WHERE attendance_type_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, attendanceTypeId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					AttendanceType attendanceType = new AttendanceType();

					attendanceType.setAttendanceTypeId(rs.getInt("attendance_type_id"));
					attendanceType.setAttendanceType(rs.getString("attendance_type"));
					attendanceType.setUnit(rs.getString("unit"));
					attendanceType.setGroupName(rs.getString("group_name"));
					attendanceType.setUseYn(rs.getString("use_yn"));
					attendanceType.setVacationTypeId(rs.getInt("vacation_type_id"));

					return attendanceType;
				}
			}
		}

		return null;
	}

	public int insert(Connection conn, AttendanceType attendanceType) throws SQLException {

		String sql = "INSERT INTO attendance_type ("
				+ "attendance_type_id, attendance_type, unit, "
				+ "group_name, use_yn, vacation_type_id"
				+ ") VALUES ("
				+ "seq_attendance_type.NEXTVAL, ?, ?, ?, ?, ?"
				+ ")";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, attendanceType.getAttendanceType());
			pstmt.setString(2, attendanceType.getUnit());
			pstmt.setString(3, attendanceType.getGroupName());
			pstmt.setString(4, attendanceType.getUseYn());

			if (attendanceType.getVacationTypeId() > 0) {
				pstmt.setInt(5, attendanceType.getVacationTypeId());
			} else {
				pstmt.setNull(5, Types.NUMERIC);
			}

			return pstmt.executeUpdate();
		}
	}

	public int update(Connection conn, AttendanceType attendanceType) throws SQLException {

		String sql = "UPDATE attendance_type "
				+ "SET attendance_type = ?, "
				+ "unit = ?, "
				+ "group_name = ?, "
				+ "use_yn = ?, "
				+ "vacation_type_id = ? "
				+ "WHERE attendance_type_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, attendanceType.getAttendanceType());
			pstmt.setString(2, attendanceType.getUnit());
			pstmt.setString(3, attendanceType.getGroupName());
			pstmt.setString(4, attendanceType.getUseYn());

			if (attendanceType.getVacationTypeId() > 0) {
				pstmt.setInt(5, attendanceType.getVacationTypeId());
			} else {
				pstmt.setNull(5, Types.NUMERIC);
			}

			pstmt.setInt(6, attendanceType.getAttendanceTypeId());

			return pstmt.executeUpdate();
		}
	}

	public int delete(Connection conn, int attendanceTypeId) throws SQLException {

		String sql = "DELETE FROM attendance_type "
				+ "WHERE attendance_type_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, attendanceTypeId);

			return pstmt.executeUpdate();
		}
	}
}