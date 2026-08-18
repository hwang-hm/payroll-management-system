package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.model.SalaryDetail;

public class SalaryDetailDao {

	// 그 사원 급여상세 전부 가져오기
	public List<SalaryDetail> selectBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT * FROM salary_detail WHERE salary_id = ? ORDER BY detail_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryDetail> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toDetail(rs));
				}
				return list;
			}
		}
	}

	// 급여상세 한 줄 새로 넣음
	public void insert(Connection conn, SalaryDetail detail) throws SQLException {
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, "
				+ "item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, "
				+ "default_amount, memo, amount) "
				+ "VALUES (seq_salary_detail.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, detail.getSalaryId());
			if (detail.getSalaryItemId() != null) {
				pstmt.setInt(2, detail.getSalaryItemId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			if (detail.getAttendanceTypeId() != null) {
				pstmt.setInt(3, detail.getAttendanceTypeId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}
			pstmt.setString(4, detail.getItemType());
			pstmt.setString(5, detail.getItemName());
			pstmt.setString(6, detail.getTaxYn());
			pstmt.setString(7, detail.getTaxFreeName());
			pstmt.setLong(8, detail.getTaxFreeLimit() != null ? detail.getTaxFreeLimit() : 0L);
			pstmt.setString(9, detail.getCalcMethod());
			pstmt.setString(10, detail.getRoundUnit() != null ? detail.getRoundUnit() : "NONE");
			pstmt.setLong(11, detail.getDefaultAmount() != null ? detail.getDefaultAmount() : 0L);
			pstmt.setString(12, detail.getMemo());
			pstmt.setLong(13, detail.getAmount() != null ? detail.getAmount() : 0L);
			pstmt.executeUpdate();
		}
	}

	// 화면에서 고친 금액이랑 계산방법이랑 저장
	public void updateAmountAndCalcMethod(Connection conn, int detailId, long amount, String calcMethod)
			throws SQLException {
		String sql = "UPDATE salary_detail SET amount = ?, calc_method = ? WHERE detail_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, amount);
			pstmt.setString(2, calcMethod);
			pstmt.setInt(3, detailId);
			pstmt.executeUpdate();
		}
	}

	// 조회결과를 급여상세로 옮겨담기
	private SalaryDetail toDetail(ResultSet rs) throws SQLException {
		SalaryDetail detail = new SalaryDetail();
		detail.setDetailId(rs.getInt("detail_id"));
		detail.setSalaryId(rs.getInt("salary_id"));
		int salaryItemId = rs.getInt("salary_item_id");
		if (!rs.wasNull()) {
			detail.setSalaryItemId(salaryItemId);
		}
		int attendanceTypeId = rs.getInt("attendance_type_id");
		if (!rs.wasNull()) {
			detail.setAttendanceTypeId(attendanceTypeId);
		}
		detail.setItemType(rs.getString("item_type"));
		detail.setItemName(rs.getString("item_name"));
		detail.setTaxYn(rs.getString("tax_yn"));
		detail.setTaxFreeName(rs.getString("tax_free_name"));
		detail.setTaxFreeLimit(rs.getLong("tax_free_limit"));
		detail.setCalcMethod(rs.getString("calc_method"));
		detail.setRoundUnit(rs.getString("round_unit"));
		detail.setDefaultAmount(rs.getLong("default_amount"));
		detail.setMemo(rs.getString("memo"));
		detail.setAmount(rs.getLong("amount"));
		return detail;
	}
}
