package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import salary.model.SalaryDate;

public class SalaryDateDao {

	// salary_date_id로 한 건 찾기
	public SalaryDate selectById(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT * FROM salary_date WHERE salary_date_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return toSalaryDate(rs);
				}
				return null;
			}
		}
	}

	// 귀속년월+차수로 salary_date 한 건 찾기
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

	// 정산기간/지급일이랑 같이 salary_date 새로 넣음
	public void insert(Connection conn, SalaryDate salaryDate) throws SQLException {
		String sql = "INSERT INTO salary_date (salary_date_id, pay_year, pay_month, pay_seq, start_date, end_date, pay_date) "
				+ "VALUES (seq_salary_date.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDate.getPayYear());
			pstmt.setInt(2, salaryDate.getPayMonth());
			pstmt.setInt(3, salaryDate.getPaySeq());
			pstmt.setTimestamp(4, toTimestamp(salaryDate.getStartDate()));
			pstmt.setTimestamp(5, toTimestamp(salaryDate.getEndDate()));
			pstmt.setTimestamp(6, toTimestamp(salaryDate.getPayDate()));
			pstmt.executeUpdate();
		}
	}

	// 조회결과를 salary_date로 옮겨담기
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

	// Date를 DB에 넣을 수 있게 Timestamp로 바꿈
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
