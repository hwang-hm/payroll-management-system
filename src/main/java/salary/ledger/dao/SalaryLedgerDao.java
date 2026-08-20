package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.Salary;

public class SalaryLedgerDao {

	// 그 해 급여 데이터 전부. 급여대장에서 씀
	public List<Salary> selectByPayYear(Connection conn, int payYear) throws SQLException {
		String sql = "SELECT s.salary_id, s.salary_date_id, s.emp_id, s.total_pay, s.total_deduct, s.net_pay, "
				+ "s.created_at, s.updated_at " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "WHERE sd.pay_year = ? "
				+ "ORDER BY sd.pay_month, sd.pay_seq, s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<Salary> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toSalary(rs));
				}
				return list;
			}
		}
	}

	// 급여가 있는 연도만 뽑아서 셀렉트박스에 씀
	public List<Integer> selectDistinctPayYears(Connection conn) throws SQLException {
		String sql = "SELECT DISTINCT sd.pay_year " + "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "ORDER BY sd.pay_year DESC";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			List<Integer> years = new ArrayList<>();
			while (rs.next()) {
				years.add(rs.getInt("pay_year"));
			}
			return years;
		}
	}

	// salary 테이블 한 줄로 옮겨담기
	private Salary toSalary(ResultSet rs) throws SQLException {
		Salary salary = new Salary();
		salary.setSalaryId(rs.getInt("salary_id"));
		salary.setSalaryDateId(rs.getInt("salary_date_id"));
		salary.setEmpId(rs.getInt("emp_id"));
		salary.setTotalPay(rs.getLong("total_pay"));
		salary.setTotalDeduct(rs.getLong("total_deduct"));
		salary.setNetPay(rs.getLong("net_pay"));
		salary.setCreatedAt(rs.getDate("created_at"));
		salary.setUpdatedAt(rs.getDate("updated_at"));
		return salary;
	}
}
