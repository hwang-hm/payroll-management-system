package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import salary.model.SalaryDate;
import salary.register.viewdto.SalaryRegisterRow;

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

	public List<SalaryRegisterRow> selectRegisterRowsByYear(Connection conn, int payYear) throws SQLException {
		String sql = "SELECT sd.salary_date_id, sd.pay_year, sd.pay_month, sd.pay_seq, "
				+ "sd.start_date, sd.end_date, sd.pay_date, "
				+ "COUNT(s.salary_id) AS emp_count, "
				+ "NVL(SUM(s.total_pay), 0) AS total_pay, "
				+ "NVL(SUM(s.total_deduct), 0) AS total_deduct, "
				+ "NVL(SUM(s.net_pay), 0) AS net_pay "
				+ "FROM salary_date sd "
				+ "JOIN salary s ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "WHERE sd.pay_year = ? "
				+ "AND e.employment_type <> '일용직' "
				+ "GROUP BY sd.salary_date_id, sd.pay_year, sd.pay_month, sd.pay_seq, "
				+ "sd.start_date, sd.end_date, sd.pay_date "
				+ "HAVING COUNT(s.salary_id) > 0 "
				+ "ORDER BY sd.pay_month, sd.pay_seq";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryRegisterRow> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toRegisterRow(rs));
				}
				return list;
			}
		}
	}

	public List<Integer> selectDistinctPayYears(Connection conn) throws SQLException {
		String sql = "SELECT DISTINCT sd.pay_year "
				+ "FROM salary_date sd "
				+ "JOIN salary s ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "WHERE e.employment_type <> '일용직' "
				+ "ORDER BY sd.pay_year DESC";
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			List<Integer> years = new ArrayList<>();
			while (rs.next()) {
				years.add(rs.getInt("pay_year"));
			}
			return years;
		}
	}

	private SalaryRegisterRow toRegisterRow(ResultSet rs) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SalaryRegisterRow row = new SalaryRegisterRow();
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
		return row;
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

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
