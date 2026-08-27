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
import attendance.model.DailyWorkerInfo;

/**
 * 일용직 근무기록의 데이터베이스 처리를 담당하는 DAO 클래스
 * 日雇い勤務記録のデータベース処理を担当するDAOクラス
 */
public class DailyWorkDao {

    /**
     * 재직 중인 일용직 사원 목록을 조회한다.
     * 在職中の日雇い社員リストを照会する。
     */
    public List<DailyWorkerInfo> selectDailyWorkers(Connection conn)
            throws SQLException {

        List<DailyWorkerInfo> workers = new ArrayList<>();

        String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
                + "NVL(d.dept_name, '-') AS dept_name, e.status "
                + "FROM employee e "
                + "LEFT JOIN department d ON e.dept_id = d.dept_id "
                + "WHERE e.employment_type = '日雇い' "
                + "AND e.status = 'Y' "
                + "ORDER BY e.emp_id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                workers.add(new DailyWorkerInfo(
                        rs.getInt("emp_id"),
                        rs.getString("employment_type"),
                        rs.getString("name_kor"),
                        rs.getString("dept_name"),
                        rs.getString("status")));
            }
        }

        return workers;
    }

    /**
     * 일용직 근무기록 번호(PK)로 한 건을 조회한다.
     * 日雇い勤務記録番号(PK)で一件を照会する。
     */
    public DailyWork selectById(Connection conn, int dailyWorkId)
            throws SQLException {

        String sql = "SELECT daily_work_id, work_date, project_name, "
                + "daily_wage, payment_rate, income_tax, "
                + "local_income_tax, net_pay, emp_id "
                + "FROM daily_work WHERE daily_work_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dailyWorkId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? createDailyWork(rs) : null;
            }
        }
    }

    /**
     * 사원의 전체 일용직 근무기록을 최신순으로 조회한다.
     * 社員の日雇い勤務記録を新しい順に照会する。
     */
    public List<DailyWork> selectByEmpId(Connection conn, int empId)
            throws SQLException {

        List<DailyWork> dailyWorks = new ArrayList<>();
        String sql = "SELECT daily_work_id, work_date, project_name, "
                + "daily_wage, payment_rate, income_tax, "
                + "local_income_tax, net_pay, emp_id "
                + "FROM daily_work WHERE emp_id = ? "
                + "ORDER BY work_date DESC, daily_work_id DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dailyWorks.add(createDailyWork(rs));
                }
            }
        }

        return dailyWorks;
    }

    /** 일용직 근무기록 등록 / 日雇い勤務記録登録 */
    public int insert(Connection conn, DailyWork dailyWork)
            throws SQLException {

        String sql = "INSERT INTO daily_work (daily_work_id, work_date, "
                + "project_name, daily_wage, payment_rate, income_tax, "
                + "local_income_tax, net_pay, emp_id) VALUES ("
                + "seq_daily_work.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setDailyWorkValues(pstmt, dailyWork, false);
            return pstmt.executeUpdate();
        }
    }

    /** 일용직 근무기록 수정 / 日雇い勤務記録修正 */
    public int update(Connection conn, DailyWork dailyWork)
            throws SQLException {

        String sql = "UPDATE daily_work SET work_date = ?, "
                + "project_name = ?, daily_wage = ?, payment_rate = ?, "
                + "income_tax = ?, local_income_tax = ?, net_pay = ?, "
                + "emp_id = ? WHERE daily_work_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setDailyWorkValues(pstmt, dailyWork, true);
            return pstmt.executeUpdate();
        }
    }

    /** 일용직 근무기록 삭제 / 日雇い勤務記録削除 */
    public int delete(Connection conn, int dailyWorkId)
            throws SQLException {

        String sql = "DELETE FROM daily_work WHERE daily_work_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dailyWorkId);
            return pstmt.executeUpdate();
        }
    }

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
                    dailyWorks.add(createDailyWork(rs));
                }
            }
        }

        return dailyWorks;
    }

    /** PreparedStatement에 공통 저장값 설정 / 共通保存値設定 */
    private void setDailyWorkValues(PreparedStatement pstmt,
            DailyWork dailyWork, boolean update) throws SQLException {

        pstmt.setDate(1, dailyWork.getWorkDate());
        pstmt.setString(2, dailyWork.getProjectName());
        pstmt.setInt(3, dailyWork.getDailyWage());
        pstmt.setDouble(4, dailyWork.getPaymentRate());
        pstmt.setInt(5, dailyWork.getIncomeTax());
        pstmt.setInt(6, dailyWork.getLocalIncomeTax());
        pstmt.setInt(7, dailyWork.getNetPay());
        pstmt.setInt(8, dailyWork.getEmpId());

        if (update) {
            pstmt.setInt(9, dailyWork.getDailyWorkId());
        }
    }

    /** ResultSet 한 행을 모델로 변환 / ResultSet一行をモデルに変換 */
    private DailyWork createDailyWork(ResultSet rs) throws SQLException {
        DailyWork dailyWork = new DailyWork();
        dailyWork.setDailyWorkId(rs.getInt("daily_work_id"));
        dailyWork.setWorkDate(rs.getDate("work_date"));
        dailyWork.setProjectName(rs.getString("project_name"));
        dailyWork.setDailyWage(rs.getInt("daily_wage"));
        dailyWork.setPaymentRate(rs.getDouble("payment_rate"));
        dailyWork.setIncomeTax(rs.getInt("income_tax"));
        dailyWork.setLocalIncomeTax(rs.getInt("local_income_tax"));
        dailyWork.setNetPay(rs.getInt("net_pay"));
        dailyWork.setEmpId(rs.getInt("emp_id"));
        return dailyWork;
    }
}
