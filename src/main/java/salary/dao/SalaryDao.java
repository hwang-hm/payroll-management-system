package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.service.SalaryView;

public class SalaryDao {

	public List<SalaryView> selectViewListBySalaryDateId(Connection conn, int salaryDateId)
			throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ? "
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
