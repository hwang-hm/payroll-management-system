package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.model.SalaryItem;

public class SalaryItemDao {

	// 1. 단건 조회 (PK)
	public SalaryItem selectById(Connection conn, int salaryItemId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM salary_item WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryItemId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return toEntity(rs);
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 2. 전체 목록 조회 (타입별: PAY / DEDUCT)
	public List<SalaryItem> selectByType(Connection conn, String itemType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SalaryItem> list = new ArrayList<>();
		String sql = "SELECT * FROM salary_item WHERE item_type = ? ORDER BY salary_item_id ASC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemType);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(toEntity(rs));
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 3. 신규 등록 (INSERT)
	public boolean insert(Connection conn, SalaryItem item) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary_item ("
				+ "salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, "
				+ " calc_method, round_unit, default_amount, remark, use_yn, created_at"
				+ ") VALUES (seq_salary_item.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, item.getAttendanceTypeId());
			pstmt.setString(2, item.getItemType());
			pstmt.setString(3, item.getItemName());
			pstmt.setString(4, item.getTaxYn());
			pstmt.setString(5, item.getTaxFreeName());
			pstmt.setObject(6, item.getTaxFreeLimit());
			pstmt.setString(7, item.getCalcMethod());
			pstmt.setString(8, item.getRoundUnit());
			pstmt.setObject(9, item.getDefaultAmount());
			pstmt.setString(10, item.getRemark());
			pstmt.setString(11, item.getUseYn());

			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// 4. 정보 수정 (UPDATE)
	public boolean update(Connection conn, SalaryItem item) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "UPDATE salary_item SET "
				+ "attendance_type_id = ?, item_name = ?, tax_yn = ?, tax_free_name = ?, tax_free_limit = ?, "
				+ "calc_method = ?, round_unit = ?, default_amount = ?, remark = ?, use_yn = ?, updated_at = sysdate "
				+ "WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, item.getAttendanceTypeId());
			pstmt.setString(2, item.getItemName());
			pstmt.setString(3, item.getTaxYn());
			pstmt.setString(4, item.getTaxFreeName());
			pstmt.setObject(5, item.getTaxFreeLimit());
			pstmt.setString(6, item.getCalcMethod());
			pstmt.setString(7, item.getRoundUnit());
			pstmt.setObject(8, item.getDefaultAmount());
			pstmt.setString(9, item.getRemark());
			pstmt.setString(10, item.getUseYn());
			pstmt.setLong(11, item.getSalaryItemId());

			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// 5. 삭제 (DELETE)
	public boolean delete(Connection conn, int salaryItemId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM salary_item WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryItemId);
			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// ResultSet -> SalaryItem 매핑 헬퍼
	private SalaryItem toEntity(ResultSet rs) throws SQLException {
		SalaryItem item = new SalaryItem(rs.getInt("salary_item_id"),
				rs.getObject("attendance_type_id") != null ? rs.getInt("attendance_type_id") : null,
				rs.getString("item_type"), rs.getString("item_name"), rs.getString("tax_yn"),
				rs.getString("tax_free_name"),
				rs.getObject("tax_free_limit") != null ? rs.getLong("tax_free_limit") : null,
				rs.getString("calc_method"), rs.getString("round_unit"),
				rs.getObject("default_amount") != null ? rs.getLong("default_amount") : null, rs.getString("remark"),
				rs.getString("use_yn"), rs.getDate("created_at"), rs.getDate("updated_at"));
		return item;
	}
}