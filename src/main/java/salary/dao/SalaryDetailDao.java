package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.JdbcUtil;
import salary.model.SalaryDetail;

public class SalaryDetailDao {

	// salary_id 기준 상세 내역 전체 조회
	public List<SalaryDetail> selectBySalaryId(Connection conn, long salaryId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT detail_id, salary_id, salary_item_id, item_type, item_name, tax_yn, round_unit, default_amount, memo, amount "
				+ "FROM salary_detail WHERE salary_id = ? ORDER BY detail_id ASC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, salaryId);
			rs = pstmt.executeQuery();
			List<SalaryDetail> list = new ArrayList<>();
			while (rs.next()) {
				SalaryDetail sd = new SalaryDetail();
				sd.setDetailId(rs.getLong("detail_id"));
				sd.setSalaryId(rs.getLong("salary_id"));
				sd.setSalaryItemId(rs.getObject("salary_item_id") != null ? rs.getLong("salary_item_id") : null);
				sd.setItemType(rs.getString("item_type"));
				sd.setItemName(rs.getString("item_name"));
				sd.setTaxYn(rs.getString("tax_yn"));
				sd.setRoundUnit(rs.getString("round_unit"));
				sd.setDefaultAmount(rs.getLong("default_amount"));
				sd.setMemo(rs.getString("memo"));
				sd.setAmount(rs.getLong("amount"));
				list.add(sd);
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 재저장 시 기존 상세 항목 삭제
	public void deleteBySalaryId(Connection conn, long salaryId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM salary_detail WHERE salary_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, salaryId);
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// 상세 항목 단건 저장
	public boolean insert(Connection conn, SalaryDetail detail) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, item_type, item_name, tax_yn, round_unit, default_amount, memo, amount) "
				+ "VALUES (seq_salary_detail.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, detail.getSalaryId());
			if (detail.getSalaryItemId() != null) {
				pstmt.setLong(2, detail.getSalaryItemId());
			} else {
				pstmt.setNull(2, java.sql.Types.NULL);
			}
			pstmt.setString(3, detail.getItemType());
			pstmt.setString(4, detail.getItemName());
			pstmt.setString(5, detail.getTaxYn());
			pstmt.setString(6, detail.getRoundUnit() != null ? detail.getRoundUnit() : "NONE");
			pstmt.setLong(7, detail.getDefaultAmount() != null ? detail.getDefaultAmount() : 0L);
			pstmt.setString(8, detail.getMemo());
			pstmt.setLong(9, detail.getAmount());

			return pstmt.executeUpdate() > 0;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}