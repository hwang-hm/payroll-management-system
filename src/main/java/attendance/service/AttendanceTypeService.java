package attendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import attendance.dao.AttendanceTypeDao;
import attendance.model.AttendanceType;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/**
 * 근태항목 관련 업무 처리를 담당하는 Service 클래스
 * 勤怠項目に関する業務処理を担当するServiceクラス
 */
public class AttendanceTypeService {

    // 근태항목 DAO / 勤怠項目DAO
    private final AttendanceTypeDao attendanceTypeDao =
            new AttendanceTypeDao();

    /**
     * 전체 근태항목을 조회한다.
     * すべての勤怠項目を照会する。
     *
     * @return 전체 근태항목 목록 / すべての勤怠項目リスト
     */
    public List<AttendanceType> getAttendanceTypes() {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            return attendanceTypeDao.selectAll(conn);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "근태항목 목록 조회 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 근태항목 번호로 한 건을 조회한다.
     * 勤怠項目番号で1件を照会する。
     *
     * @param attendanceTypeId 근태항목 번호 / 勤怠項目番号
     * @return 조회된 근태항목 / 照会された勤怠項目
     */
    public AttendanceType getAttendanceType(int attendanceTypeId) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            return attendanceTypeDao.selectById(
                    conn, attendanceTypeId);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "근태항목 상세 조회 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 새로운 근태항목을 추가한다.
     * 新しい勤怠項目を追加する。
     *
     * @param attendanceType 추가할 근태항목 / 追加する勤怠項目
     * @return 추가 성공 여부 / 追加成功可否
     */
    public boolean addAttendanceType(
            AttendanceType attendanceType) {

        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = attendanceTypeDao.insert(
                    conn, attendanceType);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "근태항목 추가 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 기존 근태항목을 수정한다.
     * 既存の勤怠項目を修正する。
     *
     * @param attendanceType 수정할 근태항목 / 修正する勤怠項目
     * @return 수정 성공 여부 / 修正成功可否
     */
    public boolean modifyAttendanceType(
            AttendanceType attendanceType) {

        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = attendanceTypeDao.update(
                    conn, attendanceType);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "근태항목 수정 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 근태항목을 삭제한다.
     * 勤怠項目を削除する。
     *
     * @param attendanceTypeId 삭제할 근태항목 번호 / 削除する勤怠項目番号
     * @return 삭제 성공 여부 / 削除成功可否
     */
    public boolean removeAttendanceType(
            int attendanceTypeId) {

        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = attendanceTypeDao.delete(
                    conn, attendanceTypeId);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "근태항목 삭제 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }
}