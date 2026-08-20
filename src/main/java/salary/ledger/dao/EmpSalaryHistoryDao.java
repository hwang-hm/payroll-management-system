package salary.ledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.ledger.viewdto.EmpSalaryHistoryDept;
import salary.ledger.viewdto.EmpSalaryHistoryEmployee;
import salary.ledger.viewdto.EmpSalaryHistoryRow;

public class EmpSalaryHistoryDao {

	// 사원·기간별 월차수 급여내역. 보수월액(기본급)과 4대보험·갑근세는 salary_detail에서 집계
	public List<EmpSalaryHistoryRow> selectHistoryByEmpAndPeriod(Connection conn, int empId, int startYm, int endYm)
			throws SQLException {
		String sql = "SELECT s.salary_id, sd.pay_year, sd.pay_month, sd.pay_seq, "
				+ "s.total_pay, s.total_deduct, s.net_pay, "
				+ "NVL(MAX(CASE WHEN d.item_name = '기본급' THEN d.amount END), 0) AS basic_pay, "
				+ "NVL(MAX(CASE WHEN d.item_name = '국민연금' THEN d.amount END), 0) AS national_pension, "
				+ "NVL(MAX(CASE WHEN d.item_name = '건강보험' THEN d.amount END), 0) AS health_insurance, "
				+ "NVL(MAX(CASE WHEN d.item_name = '장기요양보험' THEN d.amount END), 0) AS long_term_care, "
				+ "NVL(MAX(CASE WHEN d.item_name = '고용보험' THEN d.amount END), 0) AS employment_insurance, "
				+ "NVL(MAX(CASE WHEN d.item_name = '소득세' THEN d.amount END), 0) AS income_tax, "
				+ "NVL(MAX(CASE WHEN d.item_name = '지방소득세' THEN d.amount END), 0) AS resident_tax "
				+ "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "LEFT JOIN salary_detail d ON s.salary_id = d.salary_id "
				+ "WHERE s.emp_id = ? "
				+ "AND (sd.pay_year * 100 + sd.pay_month) BETWEEN ? AND ? "
				+ "GROUP BY s.salary_id, sd.pay_year, sd.pay_month, sd.pay_seq, s.total_pay, s.total_deduct, s.net_pay "
				+ "ORDER BY sd.pay_year, sd.pay_month, sd.pay_seq, s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			pstmt.setInt(2, startYm);
			pstmt.setInt(3, endYm);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<EmpSalaryHistoryRow> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toHistoryRow(rs));
				}
				return list;
			}
		}
	}

	public String selectEmpNameById(Connection conn, int empId) throws SQLException {
		String sql = "SELECT name_kor FROM employee WHERE emp_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("name_kor");
				}
				return null;
			}
		}
	}

	public List<EmpSalaryHistoryDept> selectDepartments(Connection conn) throws SQLException {
		String sql = "SELECT dept_id, dept_name FROM department WHERE use_yn = 'Y' ORDER BY sort_order, dept_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			List<EmpSalaryHistoryDept> list = new ArrayList<>();
			while (rs.next()) {
				EmpSalaryHistoryDept dept = new EmpSalaryHistoryDept();
				dept.setDeptId(rs.getInt("dept_id"));
				dept.setDeptName(rs.getString("dept_name"));
				list.add(dept);
			}
			return list;
		}
	}

	public List<EmpSalaryHistoryEmployee> selectEmployees(Connection conn, String keyword, Integer deptId,
			String status) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.emp_id, e.employment_type, e.name_kor, ")
				.append("NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, ")
				.append("CASE WHEN e.status = 'Y' THEN '재직' WHEN e.status = 'N' THEN '퇴직' ELSE '재직' END AS status_label ")
				.append("FROM employee e ")
				.append("LEFT JOIN department d ON e.dept_id = d.dept_id ")
				.append("LEFT JOIN position p ON e.position_id = p.position_id ")
				.append("WHERE 1 = 1 ");

		boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
		boolean hasDept = deptId != null && deptId > 0;
		boolean hasStatus = status != null && !status.trim().isEmpty();

		if (hasKeyword) {
			sql.append("AND (e.name_kor LIKE ? OR TO_CHAR(e.emp_id) LIKE ?) ");
		}
		if (hasDept) {
			sql.append("AND e.dept_id = ? ");
		}
		if (hasStatus) {
			sql.append("AND e.status = ? ");
		}
		sql.append("ORDER BY e.emp_id");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			int idx = 1;
			if (hasKeyword) {
				String like = "%" + keyword.trim() + "%";
				pstmt.setString(idx++, like);
				pstmt.setString(idx++, like);
			}
			if (hasDept) {
				pstmt.setInt(idx++, deptId);
			}
			if (hasStatus) {
				pstmt.setString(idx++, status);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				List<EmpSalaryHistoryEmployee> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toEmployee(rs));
				}
				return list;
			}
		}
	}

	private EmpSalaryHistoryRow toHistoryRow(ResultSet rs) throws SQLException {
		EmpSalaryHistoryRow row = new EmpSalaryHistoryRow();
		row.setSalaryId(rs.getInt("salary_id"));
		row.setPayYear(rs.getInt("pay_year"));
		row.setPayMonth(rs.getInt("pay_month"));
		row.setPaySeq(rs.getInt("pay_seq"));
		row.setBasicPay(rs.getLong("basic_pay"));
		row.setTotalPay(rs.getLong("total_pay"));
		row.setTotalDeduct(rs.getLong("total_deduct"));
		row.setNetPay(rs.getLong("net_pay"));
		row.setNationalPension(rs.getLong("national_pension"));
		row.setHealthInsurance(rs.getLong("health_insurance"));
		row.setLongTermCare(rs.getLong("long_term_care"));
		row.setEmploymentInsurance(rs.getLong("employment_insurance"));
		row.setIncomeTax(rs.getLong("income_tax"));
		row.setResidentTax(rs.getLong("resident_tax"));
		return row;
	}

	private EmpSalaryHistoryEmployee toEmployee(ResultSet rs) throws SQLException {
		EmpSalaryHistoryEmployee emp = new EmpSalaryHistoryEmployee();
		emp.setEmpId(rs.getInt("emp_id"));
		emp.setEmploymentType(rs.getString("employment_type"));
		emp.setNameKor(rs.getString("name_kor"));
		emp.setDeptName(rs.getString("dept_name"));
		emp.setPositionName(rs.getString("position_name"));
		emp.setStatus(rs.getString("status_label"));
		return emp;
	}
}
