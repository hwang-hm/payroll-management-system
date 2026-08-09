package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import salary.model.SalaryDate;

public class SalaryDateDao {

	// 1. 기존 데이터 존재 여부 확인 (귀속연/월/차수 기준)
	public SalaryDate selectByYearMonthSeq(Connection conn, int year, int month, int seq) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salary_date_id, pay_year, pay_month, pay_seq, start_date, end_date, pay_date "
				+ "FROM salary_date WHERE pay_year = ? AND pay_month = ? AND pay_seq = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, seq);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				SalaryDate sd = new SalaryDate(rs.getInt("salary_date_id"), rs.getInt("pay_year"),
						rs.getInt("pay_month"), rs.getInt("pay_seq"), rs.getDate("start_date"), rs.getDate("end_date"),
						rs.getDate("pay_date"));
				return sd;
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 2. 신규 정산일자 등록
	public boolean insert(Connection conn, SalaryDate salaryDate) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary_date (salary_date_id, pay_year, pay_month, pay_seq, start_date, end_date, pay_date) "
				+ "VALUES (seq_salary_date.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDate.getPayYear());
			pstmt.setInt(2, salaryDate.getPayMonth());
			pstmt.setInt(3, salaryDate.getPaySeq());
			pstmt.setTimestamp(4, toTimestamp(salaryDate.getStartDate()));
			pstmt.setTimestamp(5, toTimestamp(salaryDate.getEndDate()));
			pstmt.setTimestamp(6, toTimestamp(salaryDate.getPayDate()));

			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// 3. 기존 정산일자 수정
	public boolean update(Connection conn, SalaryDate salaryDate) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "UPDATE salary_date SET start_date = ?, end_date = ?, pay_date = ? " + "WHERE salary_date_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, toTimestamp(salaryDate.getStartDate()));
			pstmt.setTimestamp(2, toTimestamp(salaryDate.getEndDate()));
			pstmt.setTimestamp(3, toTimestamp(salaryDate.getPayDate()));
			pstmt.setLong(4, salaryDate.getSalaryDateId());

			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
