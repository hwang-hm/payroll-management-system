package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import salary.ledger.viewdto.SalaryLedgerRow;

public class SalaryLedgerDao {

	// 연도별 귀속차수 집계 (인원·지급·공제·실지급)
	public List<SalaryLedgerRow> selectLedgerRowsByPayYear(Connection conn, int payYear) throws SQLException {
		String sql = "SELECT sd.salary_date_id, sd.pay_year, sd.pay_month, sd.pay_seq, "
				+ "sd.start_date, sd.end_date, sd.pay_date, "
				+ "COUNT(s.salary_id) AS emp_count, "
				+ "NVL(SUM(s.total_pay), 0) AS total_pay, "
				+ "NVL(SUM(s.total_deduct), 0) AS total_deduct, "
				+ "NVL(SUM(s.net_pay), 0) AS net_pay "
				+ "FROM salary_date sd "
				+ "JOIN salary s ON s.salary_date_id = sd.salary_date_id "
				+ "WHERE sd.pay_year = ? "
				+ "GROUP BY sd.salary_date_id, sd.pay_year, sd.pay_month, sd.pay_seq, "
				+ "sd.start_date, sd.end_date, sd.pay_date "
				+ "HAVING COUNT(s.salary_id) > 0 "
				+ "ORDER BY sd.pay_month, sd.pay_seq";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryLedgerRow> list = new ArrayList<>();
				while (rs.next()) {
					SalaryLedgerRow row = new SalaryLedgerRow();
					row.setSalaryDateId(rs.getInt("salary_date_id"));
					row.setPayYear(rs.getInt("pay_year"));
					row.setPayMonth(rs.getInt("pay_month"));
					row.setPaySeq(rs.getInt("pay_seq"));
					row.setStartDate(sdf.format(rs.getDate("start_date")));
					row.setEndDate(sdf.format(rs.getDate("end_date")));
					row.setPayDate(sdf.format(rs.getDate("pay_date")));
					row.setEmpCount(rs.getInt("emp_count"));
					row.setTotalPay(rs.getLong("total_pay"));
					row.setTotalDeduct(rs.getLong("total_deduct"));
					row.setNetPay(rs.getLong("net_pay"));
					list.add(row);
				}
				return list;
			}
		}
	}

	// 급여가 있는 연도만 뽑아서 셀렉트박스에 씀
	public List<Integer> selectDistinctPayYears(Connection conn) throws SQLException {
		String sql = "SELECT DISTINCT sd.pay_year FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "ORDER BY sd.pay_year DESC";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			List<Integer> years = new ArrayList<>();
			while (rs.next()) {
				years.add(rs.getInt("pay_year"));
			}
			return years;
		}
	}
}
