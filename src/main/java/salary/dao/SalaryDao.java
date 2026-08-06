package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import salary.model.Salary;

public class SalaryDao {

	public boolean insertDayworkerSalary(Connection conn, Salary salary) throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "INSERT INTO salary (" + "salary_id, emp_id, pay_year, pay_month, pay_seq, "
				+ "start_date, end_date, pay_date, total_pay, total_deduct, net_pay" + ") VALUES ("
				+ "seq_salary.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, salary.getEmpId());
			pstmt.setInt(2, salary.getPayYear());
			pstmt.setInt(3, salary.getPayMonth());
			pstmt.setInt(4, salary.getPaySeq());

			pstmt.setTimestamp(5, toTimestamp(salary.getStartDate()));
			pstmt.setTimestamp(6, toTimestamp(salary.getEndDate()));
			pstmt.setTimestamp(7, toTimestamp(salary.getPayDate()));

			pstmt.setLong(8, salary.getTotalPay());
			pstmt.setLong(9, salary.getTotalDeduct());
			pstmt.setLong(10, salary.getNetPay());

			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				return true;
			}

			return false;

		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

}
