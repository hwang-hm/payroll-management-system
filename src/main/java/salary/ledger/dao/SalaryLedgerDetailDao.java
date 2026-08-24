package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import salary.ledger.viewdto.SalaryLedgerDetailRow;

public class SalaryLedgerDetailDao {

	// 귀속차수 전체 사원의 고정 지급 9항목. 항목 없으면 0
	// 帰属回数の全社員の固定支給9項目。項目がなければ0
	public List<SalaryLedgerDetailRow> selectDetailRowsBySalaryDateId(Connection conn, int salaryDateId)
			throws SQLException {
		String sql = "SELECT s.salary_id, e.emp_id, e.employment_type, e.name_kor, e.hire_date, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('기본급', '基本給') THEN dt.amount END), 0) AS basic_pay, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('식비', '식대', '食費') THEN dt.amount END), 0) AS meal_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('보육수당', '保育手当') THEN dt.amount END), 0) AS childcare_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('직책수당', '役職手当') THEN dt.amount END), 0) AS position_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('차량유지비', '車両維持費') THEN dt.amount END), 0) AS car_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('근속수당', '勤続手当') THEN dt.amount END), 0) AS seniority_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('당직수당', '当直手当') THEN dt.amount END), 0) AS night_duty_allowance, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('상여금', '賞与') THEN dt.amount END), 0) AS bonus, "
				+ "NVL(SUM(CASE WHEN TRIM(dt.item_name) IN ('휴일수당', '休日手当') THEN dt.amount END), 0) AS holiday_allowance "
				+ "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "LEFT JOIN salary_detail dt ON s.salary_id = dt.salary_id "
				+ "WHERE s.salary_date_id = ? "
				+ "GROUP BY s.salary_id, e.emp_id, e.employment_type, e.name_kor, e.hire_date, "
				+ "d.dept_name, p.position_name "
				+ "ORDER BY e.emp_id, s.salary_id";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryLedgerDetailRow> list = new ArrayList<>();
				while (rs.next()) {
					SalaryLedgerDetailRow row = toRow(rs, sdf);
					row.recalculateTotals();
					list.add(row);
				}
				return list;
			}
		}
	}

	private SalaryLedgerDetailRow toRow(ResultSet rs, SimpleDateFormat sdf) throws SQLException {
		SalaryLedgerDetailRow row = new SalaryLedgerDetailRow();
		row.setSalaryId(rs.getInt("salary_id"));
		row.setEmpId(rs.getInt("emp_id"));
		row.setEmploymentType(rs.getString("employment_type"));
		row.setNameKor(rs.getString("name_kor"));
		java.sql.Date hireDate = rs.getDate("hire_date");
		row.setHireDate(hireDate != null ? sdf.format(hireDate) : "-");
		row.setDeptName(rs.getString("dept_name"));
		row.setPositionName(rs.getString("position_name"));
		row.setBasicPay(rs.getLong("basic_pay"));
		row.setMealAllowance(rs.getLong("meal_allowance"));
		row.setChildcareAllowance(rs.getLong("childcare_allowance"));
		row.setPositionAllowance(rs.getLong("position_allowance"));
		row.setCarAllowance(rs.getLong("car_allowance"));
		row.setSeniorityAllowance(rs.getLong("seniority_allowance"));
		row.setNightDutyAllowance(rs.getLong("night_duty_allowance"));
		row.setBonus(rs.getLong("bonus"));
		row.setHolidayAllowance(rs.getLong("holiday_allowance"));
		return row;
	}
}
