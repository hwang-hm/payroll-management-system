package attendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import attendance.dao.DailyWorkDao;
import attendance.model.DailyWork;
import attendance.model.DailyWorkerInfo;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/**
 * 일용직 근무기록 관련 업무 처리를 담당하는 Service 클래스
 * 日雇い勤務記録に関する業務処理を担当するServiceクラス
 */
public class DailyWorkService {

    // 일용직 근무기록 DAO / 日雇い勤務記録DAO
    private final DailyWorkDao dailyWorkDao =
            new DailyWorkDao();

    public List<DailyWorkerInfo> getDailyWorkers() {
        try (Connection conn = ConnectionProvider.getConnection()) {
            return dailyWorkDao.selectDailyWorkers(conn);
        } catch (SQLException e) {
            throw new RuntimeException("일용직 사원 목록 조회 중 오류가 발생했습니다.", e);
        }
    }

    public List<DailyWork> getWorksByEmpId(int empId) {
        validateId(empId, "사원번호");
        try (Connection conn = ConnectionProvider.getConnection()) {
            return dailyWorkDao.selectByEmpId(conn, empId);
        } catch (SQLException e) {
            throw new RuntimeException("사원별 일용직 근무기록 조회 중 오류가 발생했습니다.", e);
        }
    }

    public DailyWork getDailyWork(int dailyWorkId) {
        validateId(dailyWorkId, "근무기록 번호");
        try (Connection conn = ConnectionProvider.getConnection()) {
            return dailyWorkDao.selectById(conn, dailyWorkId);
        } catch (SQLException e) {
            throw new RuntimeException("일용직 근무기록 조회 중 오류가 발생했습니다.", e);
        }
    }

    public boolean addDailyWork(DailyWork dailyWork) {
        prepareDailyWork(dailyWork);
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            int result = dailyWorkDao.insert(conn, dailyWork);
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException("일용직 근무기록 등록 중 오류가 발생했습니다.", e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    public boolean modifyDailyWork(DailyWork dailyWork) {
        validateId(dailyWork.getDailyWorkId(), "근무기록 번호");
        prepareDailyWork(dailyWork);
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            int result = dailyWorkDao.update(conn, dailyWork);
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException("일용직 근무기록 수정 중 오류가 발생했습니다.", e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    public boolean removeDailyWork(int dailyWorkId) {
        validateId(dailyWorkId, "근무기록 번호");
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            int result = dailyWorkDao.delete(conn, dailyWorkId);
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException("일용직 근무기록 삭제 중 오류가 발생했습니다.", e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    /**
     * 지급액과 세금을 계산하여 저장할 모델에 설정한다.
     * 支給額と税金を計算して保存モデルに設定する。
     */
    private void prepareDailyWork(DailyWork dailyWork) {
        if (dailyWork == null || dailyWork.getWorkDate() == null) {
            throw new IllegalArgumentException("근무일자를 입력해 주세요.");
        }
        validateId(dailyWork.getEmpId(), "사원번호");
        if (dailyWork.getProjectName() == null
                || dailyWork.getProjectName().trim().isEmpty()) {
            throw new IllegalArgumentException("현장/프로젝트를 입력해 주세요.");
        }
        if (dailyWork.getDailyWage() <= 0 || dailyWork.getPaymentRate() <= 0) {
            throw new IllegalArgumentException("일당과 지급률은 0보다 커야 합니다.");
        }

        int paymentAmount = (int) Math.round(
                dailyWork.getDailyWage() * dailyWork.getPaymentRate());

        // 소득세 2.7%, 지방소득세는 소득세의 10%로 자동 계산한다.
        // 所得税2.7%、地方所得税は所得税の10%で自動計算する。
        int incomeTax = (int) Math.round(paymentAmount * 0.027);
        int localIncomeTax = (int) Math.round(incomeTax * 0.1);

        dailyWork.setIncomeTax(incomeTax);
        dailyWork.setLocalIncomeTax(localIncomeTax);
        dailyWork.setNetPay(paymentAmount - incomeTax - localIncomeTax);
        dailyWork.setProjectName(dailyWork.getProjectName().trim());
    }

    private void validateId(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException("올바른 " + fieldName + "를 입력해 주세요.");
        }
    }

    /**
     * 사원번호와 연도, 월을 기준으로 일용직 근무내역을 조회한다.
     * 社員番号と年度、月を基準に日雇い勤務内訳を照会する。
     *
     * @param empId 사원번호 / 社員番号
     * @param year 조회 연도 / 照会年度
     * @param month 조회 월 / 照会月
     * @return 해당 사원의 월간 일용직 근무내역 / 該当社員の月間日雇い勤務内訳
     */
    public List<DailyWork> getMonthlyWorks(
            int empId,
            int year,
            int month) {

        // 사원번호 입력값을 검증한다.
        // 社員番号の入力値を検証する。
        if (empId <= 0) {
            throw new IllegalArgumentException(
                    "올바른 사원번호를 입력해 주세요.");
        }

        // 연도 입력값을 검증한다.
        // 年度の入力値を検証する。
        if (year <= 0) {
            throw new IllegalArgumentException(
                    "올바른 연도를 입력해 주세요.");
        }

        // 월은 1월부터 12월까지만 입력할 수 있다.
        // 月は1月から12月までのみ入力できる。
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "월은 1부터 12 사이의 값이어야 합니다.");
        }

        try (Connection conn =
                ConnectionProvider.getConnection()) {

            return dailyWorkDao.selectByEmpIdAndYearMonth(
                    conn,
                    empId,
                    year,
                    month);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "일용직 근무내역 조회 중 오류가 발생했습니다.",
                    e);
        }
    }
}
