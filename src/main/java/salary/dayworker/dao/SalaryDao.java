package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.dayworker.service.request.GetSalaryListDTO;
import salary.model.Salary;

public class SalaryDao {

	// 기존 급여 정보 조회
	public Salary selectByDateAndEmp(Connection conn, int salaryDateId, int empId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay "
				+ "FROM salary WHERE salary_date_id = ? AND emp_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDateId);
			pstmt.setInt(2, empId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Salary s = new Salary();
				s.setSalaryId(rs.getInt("salary_id"));
				s.setSalaryDateId(rs.getInt("salary_date_id"));
				s.setEmpId(rs.getInt("emp_id"));
				s.setTotalPay(rs.getLong("total_pay"));
				s.setTotalDeduct(rs.getLong("total_deduct"));
				s.setNetPay(rs.getLong("net_pay"));
				return s;
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 기존 급여 리스트 조회
	public List<GetSalaryListDTO> selectAllByDate(Connection conn, int salaryDateId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT s.salary_id, s.salary_date_id, s.emp_id, s.total_pay, s.total_deduct, s.net_pay, "
				+ "e.name_kor, e.employment_type, " + "d.dept_name " + "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ?";

		List<GetSalaryListDTO> salaryList = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryDateId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				GetSalaryListDTO dto = new GetSalaryListDTO(rs.getInt("salary_id"), rs.getInt("salary_date_id"),
						rs.getInt("emp_id"), rs.getLong("total_pay"), rs.getLong("total_deduct"), rs.getLong("net_pay"),
						rs.getString("name_kor"), rs.getString("employment_type"), rs.getString("dept_name"));
				salaryList.add(dto);

			}
			return salaryList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 신규 급여 헤더 저장 (시퀀스 사용)
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

			salary.setSalaryId(newSalaryId);

			pstmt = conn.prepareStatement(insertSql);
			pstmt.setLong(1, salary.getSalaryId());
			pstmt.setLong(2, salary.getSalaryDateId());
			pstmt.setLong(3, salary.getEmpId());
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

	// 기존 급여 헤더 수정
	public boolean update(Connection conn, Salary salary) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "UPDATE salary SET total_pay = ?, total_deduct = ?, net_pay = ?, updated_at = SYSDATE "
				+ "WHERE salary_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, salary.getTotalPay());
			pstmt.setLong(2, salary.getTotalDeduct());
			pstmt.setLong(3, salary.getNetPay());
			pstmt.setLong(4, salary.getSalaryId());
			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}