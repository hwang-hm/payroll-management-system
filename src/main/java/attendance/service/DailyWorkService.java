package attendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import attendance.dao.DailyWorkDao;
import attendance.model.DailyWork;
import jdbc.connection.ConnectionProvider;

/**
 * 일용직 근무기록 관련 업무 처리를 담당하는 Service 클래스
 * 日雇い勤務記録に関する業務処理を担当するServiceクラス
 */
public class DailyWorkService {

    // 일용직 근무기록 DAO / 日雇い勤務記録DAO
    private final DailyWorkDao dailyWorkDao =
            new DailyWorkDao();

    /**
     * 사원번호와 연도, 월을 기준으로 일용직 근무내역을 조회한다.
     * 社員番号と年度、月を基準に日雇い勤務内訳を照会する。
     *
     * @param empId 사원번호 / 社員番号
     * @param year 조회 연도 / 照会年度
     * @param month 조회 월 / 照会月
     * @return 해당 사원의 월간 일용직 근무내역 / 該当社員の月間日雇い勤務内訳
     */
    public List<DailyWork> getDailyWorksByEmployeeAndMonth(
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
