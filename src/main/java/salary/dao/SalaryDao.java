package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.Salary;

public class SalaryDao {

	// 년,월,급여차수로 Salary 리스트를 가져온다.
	public List<Salary> selectListByPeriod(Connection conn, int payYear, int payMonth, int paySeq) throws SQLException {
		String sql = "SELECT * FROM salary WHERE pay_year = ? AND pay_month = ? AND pay_seq = ? ORDER BY salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			pstmt.setInt(2, payMonth);
			pstmt.setInt(3, paySeq);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<Salary> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toSalary(rs));
				}
				return list;
			}
		}
	}

	private Salary toSalary(ResultSet rs) throws SQLException {
		Salary salary = new Salary();
		salary.setSalaryId(rs.getLong("salary_id"));
		salary.setEmpId(rs.getLong("emp_id"));
		salary.setPayYear(rs.getInt("pay_year"));
		salary.setPayMonth(rs.getInt("pay_month"));
		salary.setPaySeq(rs.getInt("pay_seq"));
		salary.setStartDate(rs.getDate("start_date"));
		salary.setEndDate(rs.getDate("end_date"));
		salary.setPayDate(rs.getDate("pay_date"));
		salary.setTotalPay(rs.getLong("total_pay"));
		salary.setTotalDeduct(rs.getLong("total_deduct"));
		salary.setNetPay(rs.getLong("net_pay"));
		return salary;
	}
}
