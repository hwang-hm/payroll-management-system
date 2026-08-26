package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import salary.entry.viewdto.EmployeeSearchView;
import salary.entry.viewdto.SalaryView;
import salary.model.Salary;
import salary.util.SqlIn;

public class SalaryDao {

	// 이번 급여에 그 사원이 이미 들어가 있는지 확인
	// 今回の給与にその社員が既に含まれているか確認
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

	// 해당 귀속년월에 이미 들어간 사원번호 전부
	// 該当帰属年月に既に含まれている社員番号すべて
	public Set<Integer> selectEmpIdsBySalaryDateId(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT emp_id FROM salary WHERE salary_date_id = ?";
		Set<Integer> empIds = new HashSet<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					empIds.add(rs.getInt("emp_id"));
				}
			}
		}
		return empIds;
	}

	// 같은 달 급여 중 제일 앞에 있는 사람 salary_id. 상세항목 복사할 때 씀
	// 同月給与のうち先頭の人の salary_id。詳細項目コピー時に使用
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
	// 社員1名分の給与行を新規挿入
	public int insert(Connection conn, Salary salary) throws SQLException {
		String seqSql = "SELECT seq_salary.NEXTVAL FROM dual";
		String insertSql = "INSERT INTO salary (salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";

		int newSalaryId;
		try (PreparedStatement seqPstmt = conn.prepareStatement(seqSql); ResultSet rs = seqPstmt.executeQuery()) {
			if (!rs.next()) {
				throw new SQLException("salary_id シーケンスの取得に失敗しました。");
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

	public Salary selectById(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT salary_id, salary_date_id, emp_id, total_pay, total_deduct, net_pay "
				+ "FROM salary WHERE salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return toSalary(rs);
				}
				return null;
			}
		}
	}

	// 해당 귀속년월 급여 id 전부 (일용직 포함)
	// 該当帰属年月の給与 id すべて (日雇い含む)
	public List<Integer> selectIdsBySalaryDateId(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT salary_id FROM salary WHERE salary_date_id = ? ORDER BY salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<Integer> list = new ArrayList<>();
				while (rs.next()) {
					list.add(rs.getInt("salary_id"));
				}
				return list;
			}
		}
	}

	// 해당 귀속년월 급여 헤더. 일용직은 빼둠
	// 該当帰属年月の給与ヘッダー。日雇いは除外
	public List<Salary> selectBySalaryDateIdExcludingDayworkers(Connection conn, int salaryDateId)
			throws SQLException {
		String sql = "SELECT s.salary_id, s.salary_date_id, s.emp_id, s.total_pay, s.total_deduct, s.net_pay "
				+ "FROM salary s JOIN employee e ON s.emp_id = e.emp_id "
				+ "WHERE s.salary_date_id = ? AND e.employment_type <> '日雇い' ORDER BY s.salary_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<Salary> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toSalary(rs));
				}
				return list;
			}
		}
	}

	public void deleteById(Connection conn, int salaryId) throws SQLException {
		String sql = "DELETE FROM salary WHERE salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			pstmt.executeUpdate();
		}
	}

	// 여러 salary 한 번에 삭제
	// 複数 salary を一括削除
	public void deleteByIds(Connection conn, List<Integer> salaryIds) throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		String sql = "DELETE FROM salary WHERE salary_id IN (" + SqlIn.placeholders(salaryIds.size()) + ")";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			SqlIn.setInts(pstmt, 1, salaryIds);
			pstmt.executeUpdate();
		}
	}

	// 이번 귀속년월·목록에 속한 salary만 골라 반환 (삭제 검증용)
	// 今回帰属年月・一覧に属する salary のみ選んで返却 (削除検証用)
	public List<Integer> selectIdsBySalaryDateIdAndIds(Connection conn, int salaryDateId, List<Integer> salaryIds)
			throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return new ArrayList<>();
		}
		String sql = "SELECT salary_id FROM salary WHERE salary_date_id = ? AND salary_id IN ("
				+ SqlIn.placeholders(salaryIds.size()) + ")";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryDateId);
			SqlIn.setInts(pstmt, 2, salaryIds);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<Integer> list = new ArrayList<>();
				while (rs.next()) {
					list.add(rs.getInt("salary_id"));
				}
				return list;
			}
		}
	}

	// 지급합계/공제합계/실지급액 다시 저장
	// 支給合計/控除合計/実支給額を再保存
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
	// 該当帰属年月の給与一覧。日雇いは除外
	public List<SalaryView> selectViewListBySalaryDateId(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT s.salary_id, s.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, s.total_pay, s.total_deduct, s.net_pay " + "FROM salary s "
				+ "JOIN employee e ON s.emp_id = e.emp_id " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "WHERE s.salary_date_id = ? " + "AND e.employment_type <> '日雇い' " + "ORDER BY s.salary_id";
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

	// 이번 급여에 아직 안 넣은 사원 목록
	// 今回の給与にまだ未追加の社員一覧
	public List<EmployeeSearchView> selectEmployeesForAdd(Connection conn, int salaryDateId) throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '在職' WHEN e.status = 'N' THEN '退職' ELSE '在職' END AS status_label "
				+ "FROM employee e " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "WHERE e.employment_type <> '日雇い' "
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
	// 氏名または社員番号で未追加の社員を検索
	public List<EmployeeSearchView> selectEmployeesForAddByKeyword(Connection conn, int salaryDateId,
			String searchKeyword) throws SQLException {
		String sql = "SELECT e.emp_id, e.employment_type, e.name_kor, "
				+ "NVL(d.dept_name, '-') AS dept_name, NVL(p.position_name, '-') AS position_name, "
				+ "CASE WHEN e.status = 'Y' THEN '在職' WHEN e.status = 'N' THEN '退職' ELSE '在職' END AS status_label "
				+ "FROM employee e " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "WHERE e.employment_type <> '日雇い' "
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
	// 社員検索モーダル表示用の値へ転記
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
	// 給与一覧1行へ転記
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

	private Salary toSalary(ResultSet rs) throws SQLException {
		Salary salary = new Salary();
		salary.setSalaryId(rs.getInt("salary_id"));
		salary.setSalaryDateId(rs.getInt("salary_date_id"));
		salary.setEmpId(rs.getInt("emp_id"));
		salary.setTotalPay(rs.getLong("total_pay"));
		salary.setTotalDeduct(rs.getLong("total_deduct"));
		salary.setNetPay(rs.getLong("net_pay"));
		return salary;
	}

}
