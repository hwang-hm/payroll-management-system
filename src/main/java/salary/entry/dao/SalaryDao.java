package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.entry.viewdto.EmployeeSearchView;
import salary.entry.viewdto.SalaryView;
import salary.model.Salary;
import salary.transfer.viewdto.TransferApplyRow;

public class SalaryDao {

	// 이번 급여에 그 사원이 이미 들어가 있는지 확인
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

	// 같은 달 급여 중 제일 앞에 있는 사람 salary_id. 상세항목 복사할 때 씀
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

	// 사원 한 명 급여 행 새로 넣음
	public int insert(Connection conn, Salary salary) throws SQLException {
		String seqSql = "SELECT seq_salary.NEXTVAL FROM dual";
		String insertSql = "INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";

		int newSalaryId;
		try (PreparedStatement seqPstmt = conn.prepareStatement(seqSql); ResultSet rs = seqPstmt.executeQuery()) {
			if (!rs.next()) {
				throw new SQLException("salary_id 시퀀스 조회에 실패했습니다.");
			}
			newSalaryId = rs.getInt(1);
		}

		try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
			insertPstmt.setInt(1, newSalaryId);
			insertPstmt.setInt(2, salary.getSalaryDateId());
			insertPstmt.setInt(3, salary.getEmpId());
			insertPstmt.setLong(4, salary.getTotalPay());
			insertPstmt.setLong(5, salary.getTotalDeduct());
			insertPstmt.setLong(6, salary.getNetPay());
			insertPstmt.executeUpdate();
		}

		return newSalaryId;
	}

	// 지급합계/공제합계/실지급액 다시 저장
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

	// 해당 귀속년월 급여 목록. 일용직은 빼둠
	public List<SalaryView> selectViewListBySalaryDateId(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, s.total_pay, s.total_deduct, s.net_pay " + "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ? " + "AND e.employment_type <> '일용직' " + "ORDER BY s.salary_id";
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

	// 이체신청 화면에 뿌릴 사원+계좌+금액 목록
	public List<TransferApplyRow> selectTransferApplyRowsByPeriod(Connection conn,
			int payYear, int payMonth, int paySeq) throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, e.name_eng, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "e.hire_date, e.mobile_num, e.email, "
				+ "eis.salary_bank, eis.salary_account, "
				+ "pi.salary_bank AS withdraw_bank, pi.salary_account AS withdraw_account, "
				+ "s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s "
				+ "JOIN salary_date sd ON s.salary_date_id = sd.salary_date_id "
				+ "JOIN employee e ON s.emp_id = e.emp_id "
				+ "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id "
				+ "LEFT JOIN employee_information_salary eis ON e.emp_id = eis.emp_id "
				+ "LEFT JOIN (SELECT salary_bank, salary_account FROM payroll_information WHERE ROWNUM = 1) pi "
				+ "ON 1 = 1 "
				+ "WHERE sd.pay_year = ? AND sd.pay_month = ? AND sd.pay_seq = ? "
				+ "AND e.employment_type <> '일용직' "
				+ "ORDER BY s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, payYear);
			pstmt.setInt(2, payMonth);
			pstmt.setInt(3, paySeq);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<TransferApplyRow> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toTransferApplyRow(rs));
				}
				return list;
			}
		}
	}

	// 이번 급여에 아직 안 넣은 사원 목록
	public List<EmployeeSearchView> selectEmployeesForAdd(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '재직' WHEN e.status = 'N' THEN '퇴직' ELSE '재직' END AS status_label "
				+ "FROM employee e " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "WHERE e.employment_type <> '일용직' "
				+ "AND e.emp_id NOT IN (SELECT emp_id FROM salary WHERE salary_date_id = ?) " + "ORDER BY e.emp_id";
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

	// 이름이나 사번으로 아직 안 넣은 사원 찾기
	public List<EmployeeSearchView> selectEmployeesForAddByKeyword(Connection conn, int salaryDateId,
			String searchKeyword) throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '재직' WHEN e.status = 'N' THEN '퇴직' ELSE '재직' END AS status_label "
				+ "FROM employee e " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "WHERE e.employment_type <> '일용직' "
				+ "AND e.emp_id NOT IN (SELECT emp_id FROM salary WHERE salary_date_id = ?) "
				+ "AND (e.name_kor LIKE ? OR TO_CHAR(e.emp_id) LIKE ?) " + "ORDER BY e.emp_id";
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

	// 사원검색 모달에 뿌릴 값 옮겨담기
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

	// 급여목록 한 줄로 옮겨담기
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

	// 이체신청 한 줄로 옮겨담기
	private TransferApplyRow toTransferApplyRow(ResultSet rs) throws SQLException {
		TransferApplyRow row = new TransferApplyRow();
		row.setSalaryId(rs.getInt("salary_id"));
		row.setEmpId(rs.getInt("emp_id"));
		row.setEmploymentType(rs.getString("employment_type"));
		row.setNameKor(rs.getString("name_kor"));
		row.setNameEng(rs.getString("name_eng"));
		row.setDeptName(rs.getString("dept_name"));
		row.setPositionName(rs.getString("position_name"));
		row.setHireDate(rs.getDate("hire_date"));
		row.setMobileNum(rs.getString("mobile_num"));
		row.setEmail(rs.getString("email"));
		row.setSalaryBank(rs.getString("salary_bank"));
		row.setSalaryAccount(rs.getString("salary_account"));
		row.setWithdrawBank(rs.getString("withdraw_bank"));
		row.setWithdrawAccount(rs.getString("withdraw_account"));
		row.setTotalPay(rs.getLong("total_pay"));
		row.setTotalDeduct(rs.getLong("total_deduct"));
		row.setNetPay(rs.getLong("net_pay"));
		return row;
	}
}
