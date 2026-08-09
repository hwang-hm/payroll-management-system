package attendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import attendance.dao.VacationTypeDao;
import attendance.model.VacationType;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/**
 * 휴가항목 관련 업무 처리를 담당하는 Service 클래스
 * 休暇項目に関する業務処理を担当するServiceクラス
 */
public class VacationTypeService {

    // 휴가항목 DAO
    // 休暇項目DAO
    private final VacationTypeDao vacationTypeDao =
            new VacationTypeDao();

    /**
     * 전체 휴가항목을 조회한다.
     * すべての休暇項目を照会する。
     *
     * @return 전체 휴가항목 목록 / 全休暇項目リスト
     */
    public List<VacationType> getVacationTypes() {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            return vacationTypeDao.selectAll(conn);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "휴가항목 목록 조회 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 휴가항목 번호로 한 건을 조회한다.
     * 休暇項目番号で1件を照会する。
     *
     * @param vacationTypeId 휴가항목 번호 / 休暇項目番号
     * @return 조회된 휴가항목 / 照会された休暇項目
     */
    public VacationType getVacationType(int vacationTypeId) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            return vacationTypeDao.selectById(conn, vacationTypeId);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "휴가항목 상세 조회 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 새로운 휴가항목을 추가한다.
     * 新しい休暇項目を追加する。
     *
     * @param vacationType 추가할 휴가항목 / 追加する休暇項目
     * @return 추가 성공 여부 / 追加成功可否
     */
    public boolean addVacationType(VacationType vacationType) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = vacationTypeDao.insert(conn, vacationType);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "휴가항목 추가 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 기존 휴가항목을 수정한다.
     * 既存の休暇項目を修正する。
     *
     * @param vacationType 수정할 휴가항목 / 修正する休暇項目
     * @return 수정 성공 여부 / 修正成功可否
     */
    public boolean modifyVacationType(VacationType vacationType) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = vacationTypeDao.update(conn, vacationType);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "휴가항목 수정 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 휴가항목을 삭제한다.
     * 休暇項目を削除する。
     *
     * @param vacationTypeId 삭제할 휴가항목 번호 / 削除する休暇項目番号
     * @return 삭제 성공 여부 / 削除成功可否
     */
    public boolean removeVacationType(int vacationTypeId) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int result = vacationTypeDao.delete(conn, vacationTypeId);

            conn.commit();

            return result > 0;

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);

            throw new RuntimeException(
                    "휴가항목 삭제 중 오류가 발생했습니다.", e);

        } finally {
            JdbcUtil.close(conn);
        }
    }
}