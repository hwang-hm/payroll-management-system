package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import salary.model.SalaryDetail;
import salary.util.SqlIn;

public class SalaryDetailDao {

	// 그 사원 급여상세 전부 가져오기
	// その社員の給与詳細をすべて取得
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
	// 給与詳細1行を新規挿入
	public void insert(Connection conn, SalaryDetail detail) throws SQLException {
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, "
				+ "item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, "
				+ "default_amount, memo, amount) "
				+ "VALUES (seq_salary_detail.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			bindInsert(pstmt, detail);
			pstmt.executeUpdate();
		}
	}

	// 급여상세 여러 줄 batch insert
	// 給与詳細複数行の batch insert
	public void insertBatch(Connection conn, List<SalaryDetail> details) throws SQLException {
		if (details == null || details.isEmpty()) {
			return;
		}
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, "
				+ "item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, "
				+ "default_amount, memo, amount) "
				+ "VALUES (seq_salary_detail.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (SalaryDetail detail : details) {
				bindInsert(pstmt, detail);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
	}

	// 원본 salary_detail을 대상 salary_id로 한 방 복사
	// 元の salary_detail を対象 salary_id へ一括コピー
	public void copyFromSalary(Connection conn, int sourceSalaryId, int targetSalaryId) throws SQLException {
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, attendance_type_id, "
				+ "item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, "
				+ "default_amount, memo, amount) "
				+ "SELECT seq_salary_detail.NEXTVAL, ?, salary_item_id, attendance_type_id, "
				+ "item_type, item_name, tax_yn, tax_free_name, tax_free_limit, calc_method, round_unit, "
				+ "default_amount, memo, amount FROM salary_detail WHERE salary_id = ? ORDER BY detail_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, targetSalaryId);
			pstmt.setInt(2, sourceSalaryId);
			pstmt.executeUpdate();
		}
	}

	// 화면에서 고친 금액이랑 계산방법이랑 저장
	// 画面で修正した金額と計算方法を保存
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

	// 금액/산출식 batch update
	// 金額/算出式の batch update
	public void updateAmountAndCalcMethodBatch(Connection conn, List<Integer> detailIds, List<Long> amounts,
			List<String> calcMethods) throws SQLException {
		if (detailIds == null || detailIds.isEmpty()) {
			return;
		}
		String sql = "UPDATE salary_detail SET amount = ?, calc_method = ? WHERE detail_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < detailIds.size(); i++) {
				pstmt.setLong(1, amounts.get(i) != null ? amounts.get(i) : 0L);
				pstmt.setString(2, calcMethods.get(i));
				pstmt.setInt(3, detailIds.get(i));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
	}

	public void deleteBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "DELETE FROM salary_detail WHERE salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			pstmt.executeUpdate();
		}
	}

	public void deleteBySalaryIds(Connection conn, List<Integer> salaryIds) throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		String sql = "DELETE FROM salary_detail WHERE salary_id IN (" + SqlIn.placeholders(salaryIds.size()) + ")";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			SqlIn.setInts(pstmt, 1, salaryIds);
			pstmt.executeUpdate();
		}
	}

	private void bindInsert(PreparedStatement pstmt, SalaryDetail detail) throws SQLException {
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
	}

	// 조회결과를 급여상세로 옮겨담기
	// 照会結果を給与詳細へ転記
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
