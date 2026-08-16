package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.dayworker.model.Dayworker;

public class DayworkerDao {
	public List<Dayworker> selectAllDayworkers(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Dayworker> list = new ArrayList<>();

		// 일용직 구분(employment_type) 조건 조회
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

	// 키워드 유무에 따른 전체/검색 사원 조회 Method
	public List<Dayworker> selectEmpByKeyword(Connection conn, String keyword) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Dayworker> list = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.employment_type, e.emp_id, e.name_kor, e.dept_id, ")
				.append("d.dept_name AS dept_name, e.position_id, p.position_name AS position_name, e.status ")
				.append("FROM employee e ").append("LEFT JOIN department d ON e.dept_id = d.dept_id ")
				.append("LEFT JOIN position p ON e.position_id = p.position_id ");

		// 1. keyword 존재 여부 체크
		boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

		// 2. keyword가 있을 때만 WHERE 절 동적 추가 (사원명 또는 사번 검색)
		if (hasKeyword) {
			sql.append("WHERE (e.name_kor LIKE ? OR CAST(e.emp_id AS CHAR) LIKE ?) ");
		}

		sql.append("ORDER BY e.emp_id ASC");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			// 3. keyword가 존재할 경우에만 바인딩 변수(?) 세팅
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
