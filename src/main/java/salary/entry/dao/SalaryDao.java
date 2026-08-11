package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.entry.viewdto.EmployeeSearchView;
import salary.entry.viewdto.SalaryView;
import salary.model.Salary;

public class SalaryDao {

	public List<SalaryView> selectViewListBySalaryDateId(Connection conn, int salaryDateId)
			throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ? "
				+ "AND e.employment_type <> '일용직' "
				+ "ORDER BY s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryView> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toSalaryView(rs));
				}
				return list;
			}
		}
	}

	public List<EmployeeSearchView> selectEmployeesForAdd(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '재직' WHEN e.status = 'N' THEN '퇴직' ELSE '재직' END AS status_label "
				+ "FROM employee e "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "WHERE e.employment_type <> '일용직' "
				+ "AND e.emp_id NOT IN (SELECT emp_id FROM salary WHERE salary_date_id = ?) "
				+ "ORDER BY e.emp_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<EmployeeSearchView> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toEmployeeSearchView(rs));
				}
				return list;
			}
		}
	}

	public List<EmployeeSearchView> selectEmployeesForAddByKeyword(Connection conn, int salaryDateId, String searchKeyword)
			throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '재직' WHEN e.status = 'N' THEN '퇴직' ELSE '재직' END AS status_label "
				+ "FROM employee e "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "WHERE e.employment_type <> '일용직' "
				+ "AND e.emp_id NOT IN (SELECT emp_id FROM salary WHERE salary_date_id = ?) "
				+ "AND (e.name_kor LIKE ? OR TO_CHAR(e.emp_id) LIKE ?) "
				+ "ORDER BY e.emp_id";
		String keyword = "%" + searchKeyword.trim() + "%";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<EmployeeSearchView> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toEmployeeSearchView(rs));
				}
				return list;
			}
		}
	}

	public boolean existsBySalaryDateIdAndEmpId(Connection conn, int salaryDateId, int empId) throws SQLException {
		String sql = "SELECT 1 FROM salary WHERE salary_date_id = ? AND emp_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			pstmt.setInt(2, empId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	public Integer selectFirstSalaryIdBySalaryDateId(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT MIN(salary_id) AS salary_id FROM salary WHERE salary_date_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int salaryId = rs.getInt("salary_id");
					if (!rs.wasNull()) {
						return salaryId;
					}
				}
				return null;
			}
		}
	}

	public int insert(Connection conn, Salary salary) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String seqSql = "SELECT seq_salary.NEXTVAL FROM dual";
		String insertSql = "INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
		try {
			pstmt = conn.prepareStatement(seqSql);
			rs = pstmt.executeQuery();
			int newSalaryId = 0;
			if (rs.next()) {
				newSalaryId = rs.getInt(1);
			}
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

			pstmt = conn.prepareStatement(insertSql);
			pstmt.setInt(1, newSalaryId);
			pstmt.setInt(2, salary.getSalaryDateId());
			pstmt.setInt(3, salary.getEmpId());
			pstmt.setLong(4, salary.getTotalPay());
			pstmt.setLong(5, salary.getTotalDeduct());
			pstmt.setLong(6, salary.getNetPay());
			pstmt.executeUpdate();

			return newSalaryId;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	public void updateTotals(Connection conn, int salaryId, long totalPay, long totalDeduct, long netPay)
			throws SQLException {
		String sql = "UPDATE salary SET total_pay = ?, total_deduct = ?, net_pay = ?, updated_at = SYSDATE WHERE salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, totalPay);
			pstmt.setLong(2, totalDeduct);
			pstmt.setLong(3, netPay);
			pstmt.setInt(4, salaryId);
			pstmt.executeUpdate();
		}
	}

	private EmployeeSearchView toEmployeeSearchView(ResultSet rs) throws SQLException {
		EmployeeSearchView view = new EmployeeSearchView();
		view.setEmpId(rs.getInt("emp_id"));
		view.setEmploymentType(rs.getString("employment_type"));
		view.setNameKor(rs.getString("name_kor"));
		view.setDeptName(rs.getString("dept_name"));
		view.setPositionName(rs.getString("position_name"));
		view.setStatus(rs.getString("status_label"));
		return view;
	}

	private SalaryView toSalaryView(ResultSet rs) throws SQLException {
		SalaryView view = new SalaryView();
		view.setSalaryId(rs.getInt("salary_id"));
		view.setEmpId(rs.getInt("emp_id"));
		view.setEmploymentType(rs.getString("employment_type"));
		view.setNameKor(rs.getString("name_kor"));
		view.setDeptName(rs.getString("dept_name"));
		view.setTotalPay(rs.getLong("total_pay"));
		view.setTotalDeduct(rs.getLong("total_deduct"));
		view.setNetPay(rs.getLong("net_pay"));
		return view;
	}
}
