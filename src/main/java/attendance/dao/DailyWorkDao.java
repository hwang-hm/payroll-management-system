package attendance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.model.DailyWork;

/**
 * 일용직 근무기록의 데이터베이스 처리를 담당하는 DAO 클래스
 * 日雇い勤務記録のデータベース処理を担当するDAOクラス
 */
public class DailyWorkDao {

    /**
     * 사원번호와 연도, 월을 기준으로 일용직 근무내역을 조회한다.
     * 社員番号と年度、月を基準に日雇い勤務内訳を照会する。
     *
     * @param conn DB 연결 객체 / DB接続オブジェクト
     * @param empId 사원번호 / 社員番号
     * @param year 조회 연도 / 照会年度
     * @param month 조회 월 / 照会月
     * @return 해당 사원의 월간 일용직 근무내역 / 該当社員の月間日雇い勤務内訳
     * @throws SQLException SQL 실행 오류 / SQL実行エラー
     */
    public List<DailyWork> selectByEmpIdAndYearMonth(
            Connection conn,
            int empId,
            int year,
            int month) throws SQLException {

        // 조회 결과를 저장할 목록
        // 照会結果を保存するリスト
        List<DailyWork> dailyWorks = new ArrayList<>();

        // 조회할 월의 첫날과 다음 달 첫날을 계산한다.
        // 照会する月の初日と翌月の初日を計算する。
        LocalDate firstDay = LocalDate.of(year, month, 1);
        Date startDate = Date.valueOf(firstDay);
        Date endDate = Date.valueOf(firstDay.plusMonths(1));

        String sql = "SELECT daily_work_id, "
                + "work_date, "
                + "project_name, "
                + "daily_wage, "
                + "payment_rate, "
                + "income_tax, "
                + "local_income_tax, "
                + "net_pay, "
                + "emp_id "
                + "FROM daily_work "
                + "WHERE emp_id = ? "
                + "AND work_date >= ? "
                + "AND work_date < ? "
                + "ORDER BY work_date ASC, daily_work_id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    DailyWork dailyWork = new DailyWork();

                    dailyWork.setDailyWorkId(
                            rs.getInt("daily_work_id"));
                    dailyWork.setWorkDate(
                            rs.getDate("work_date"));
                    dailyWork.setProjectName(
                            rs.getString("project_name"));
                    dailyWork.setDailyWage(
                            rs.getInt("daily_wage"));
                    dailyWork.setPaymentRate(
                            rs.getDouble("payment_rate"));
                    dailyWork.setIncomeTax(
                            rs.getInt("income_tax"));
                    dailyWork.setLocalIncomeTax(
                            rs.getInt("local_income_tax"));
                    dailyWork.setNetPay(
                            rs.getInt("net_pay"));
                    dailyWork.setEmpId(
                            rs.getInt("emp_id"));

                    dailyWorks.add(dailyWork);
                }
            }
        }

        return dailyWorks;
    }
}
