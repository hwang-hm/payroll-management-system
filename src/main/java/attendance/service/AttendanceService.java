package attendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import attendance.dao.AttendanceDao;
import attendance.model.Attendance;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AttendanceService {

	private final AttendanceDao attendanceDao = new AttendanceDao();

	public List<Attendance> getAttendances() {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();

			return attendanceDao.selectAll(conn);
		} catch (SQLException e) {
			throw new RuntimeException("근태기록 목록 조회 중 오류가 발생했습니다.", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public Attendance getAttendance(int attendanceId) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();

			return attendanceDao.selectById(conn, attendanceId);

		} catch (SQLException e) {
			throw new RuntimeException("근태기록 상세 조회 중 오류가 발생했습니다.", e);
		} finally {
			JdbcUtil.close(conn);
		}

	}

	public List<Attendance> getAttendancesByEmpId(int empId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			return attendanceDao.selectByEmpId(conn, empId);
		} catch (SQLException e) {
			throw new RuntimeException("사원별 근태기록 조회 중 오류가 발생했습니다.", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	/**
	 * 사원번호와 연월로 근태기록을 조회한다.
	 * 社員番号と年月で勤怠記録を照会する。
	 */
	public List<Attendance> getMonthlyAttendances(
			int empId, int year, int month) {

		if (empId <= 0 || year <= 0 || month < 1 || month > 12) {
			throw new IllegalArgumentException(
					"올바른 사원번호와 조회 연월을 입력해 주세요.");
		}

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return attendanceDao.selectByEmpIdAndYearMonth(
					conn, empId, year, month);
		} catch (SQLException e) {
			throw new RuntimeException(
					"월간 근태기록 조회 중 오류가 발생했습니다.", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public boolean addAttendance(Attendance attendance) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int result = attendanceDao.insert(conn, attendance);

			conn.commit();
			return result > 0;

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);

			throw new RuntimeException("근태기록 등록 중 오류가 발생했습니다.", e);

		} finally {
			JdbcUtil.close(conn);
		}
	}

	public boolean modifyAttendance(Attendance attendance) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int result = attendanceDao.update(conn, attendance);

			conn.commit();
			return result > 0;

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);

			throw new RuntimeException("근태기록 수정 중 오류가 발생했습니다.", e);

		} finally {
			JdbcUtil.close(conn);

		}
	}

	public boolean removeAttendance(int attendanceId) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int result = attendanceDao.delete(conn, attendanceId);

			conn.commit();
			return result > 0;

		} catch (SQLException e) {
			JdbcUtil.rollback(conn);

			throw new RuntimeException("근태기록 삭제 중 오류가 발생했습니다.", e);

		} finally {
			JdbcUtil.close(conn);

		}
	}

}
