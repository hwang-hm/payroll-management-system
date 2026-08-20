package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemLedgerDao {

	// 기간·항목명으로 사원별 월 합계. 같은 달 여러 차수는 합산
	public List<ItemAmount> selectItemAmountsByPeriod(Connection conn, String itemName, int startYm, int endYm)
			throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "sd.pay_year, sd.pay_month, NVL(SUM(dt.amount), 0) AS item_amount "
				+ "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "LEFT JOIN salary_detail dt ON s.salary_id = dt.salary_id AND dt.item_name = ? "
				+ "WHERE (sd.pay_year * 100 + sd.pay_month) BETWEEN ? AND ? "
				+ "GROUP BY e.emp_id, e.employment_type, e.name_kor, d.dept_name, p.position_name, "
				+ "sd.pay_year, sd.pay_month "
				+ "ORDER BY e.emp_id, sd.pay_year, sd.pay_month";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, itemName);
			pstmt.setInt(2, startYm);
			pstmt.setInt(3, endYm);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<ItemAmount> list = new ArrayList<>();
				while (rs.next()) {
					ItemAmount row = new ItemAmount();
					row.empId = rs.getInt("emp_id");
					row.employmentType = rs.getString("employment_type");
					row.nameKor = rs.getString("name_kor");
					row.deptName = rs.getString("dept_name");
					row.positionName = rs.getString("position_name");
					row.payYear = rs.getInt("pay_year");
					row.payMonth = rs.getInt("pay_month");
					row.amount = rs.getLong("item_amount");
					list.add(row);
				}
				return list;
			}
		}
	}

	public static class ItemAmount {
		public int empId;
		public String employmentType;
		public String nameKor;
		public String deptName;
		public String positionName;
		public int payYear;
		public int payMonth;
		public long amount;
	}
}
