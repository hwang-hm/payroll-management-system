package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import salary.model.SalaryDate;

public class SalaryDateDao {

	public SalaryDate selectByPeriod(Connection conn, int payYear, int payMonth, int paySeq) throws SQLException {
		String sql = "SELECT * FROM salary_date WHERE pay_year = ? AND pay_month = ? AND pay_seq = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			pstmt.setInt(2, payMonth);
			pstmt.setInt(3, paySeq);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return toSalaryDate(rs);
				}
				return null;
			}
		}
	}

	private SalaryDate toSalaryDate(ResultSet rs) throws SQLException {
		SalaryDate salaryDate = new SalaryDate();
		salaryDate.setSalaryDateId(rs.getInt("salary_date_id"));
		salaryDate.setPayYear(rs.getInt("pay_year"));
		salaryDate.setPayMonth(rs.getInt("pay_month"));
		salaryDate.setPaySeq(rs.getInt("pay_seq"));
		salaryDate.setStartDate(rs.getDate("start_date"));
		salaryDate.setEndDate(rs.getDate("end_date"));
		salaryDate.setPayDate(rs.getDate("pay_date"));
		return salaryDate;
	}
}
