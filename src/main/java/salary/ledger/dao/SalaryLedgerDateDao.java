package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.SalaryDate;

public class SalaryLedgerDateDao {

	// 그 해 salary_date 목록
	public List<SalaryDate> selectByYear(Connection conn, int payYear) throws SQLException {
		String sql = "SELECT * FROM salary_date WHERE pay_year = ? ORDER BY pay_month, pay_seq";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryDate> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toSalaryDate(rs));
				}
				return list;
			}
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
}
