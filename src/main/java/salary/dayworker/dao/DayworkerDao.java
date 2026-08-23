package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.dayworker.model.Dayworker;

/**
 * 일용직 사원 데이터 액세스 객체
 * 日雇い社員データアクセスオブジェクト
 */
public class DayworkerDao {
	
	// 전체 일용직 사원 목록 조회 / 全日雇い社員リスト照会
	public List<Dayworker> selectAllDayworkers(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Dayworker> list = new ArrayList<>();

		String sql = "SELECT e.employment_type, e.emp_id, e.name_kor, e.dept_id, "
				+ "d.dept_name AS dept_name, e.position_id, p.position_name AS position_name, e.status "
				+ "FROM employee e " + "LEFT JOIN department d ON e.dept_id = d.dept_id "
				+ "LEFT JOIN position p ON e.position_id = p.position_id " + "WHERE e.employment_type = '일용직' "
				+ "ORDER BY e.emp_id ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Dayworker(rs.getString("employment_type"), rs.getInt("emp_id"), rs.getString("name_kor"),
						rs.getObject("dept_id") != null ? rs.getInt("dept_id") : null, rs.getString("dept_name"),
						rs.getObject("position_id") != null ? rs.getInt("position_id") : null,
						rs.getString("position_name"), rs.getString("status")));
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 키워드(사원명/사번) 검색 및 전체 사원 조회 / 키워드(社員名/社員番号)検索および社員照会
	public List<Dayworker> selectEmpByKeyword(Connection conn, String keyword) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Dayworker> list = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.employment_type, e.emp_id, e.name_kor, e.dept_id, ")
				.append("d.dept_name AS dept_name, e.position_id, p.position_name AS position_name, e.status ")
				.append("FROM employee e ").append("LEFT JOIN department d ON e.dept_id = d.dept_id ")
				.append("LEFT JOIN position p ON e.position_id = p.position_id ");

		boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

		// 키워드가 있을 경우 검색 조건 추가 / キーワードが存在する場合、検索条件を追加
		if (hasKeyword) {
			sql.append("WHERE (e.name_kor LIKE ? OR CAST(e.emp_id AS CHAR) LIKE ?) ");
		}

		sql.append("ORDER BY e.emp_id ASC");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			if (hasKeyword) {
				String searchPattern = "%" + keyword.trim() + "%";
				pstmt.setString(1, searchPattern);
				pstmt.setString(2, searchPattern);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Dayworker(rs.getString("employment_type"), rs.getInt("emp_id"), rs.getString("name_kor"),
						rs.getObject("dept_id") != null ? rs.getInt("dept_id") : null, rs.getString("dept_name"),
						rs.getObject("position_id") != null ? rs.getInt("position_id") : null,
						rs.getString("position_name"), rs.getString("status")));
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
