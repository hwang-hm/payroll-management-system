package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.SalaryItem;

public class SalaryItemDao {

	// 사용중인 급여항목만 가져옴. 사원 추가할 때 상세 만들려고
	// 使用中の給与項目のみ取得。社員追加時に詳細を作るため
	public List<SalaryItem> selectActiveItems(Connection conn) throws SQLException {
		String sql = "SELECT salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, "
				+ "tax_free_limit, calc_method, round_unit, default_amount, remark, use_yn "
				+ "FROM salary_item WHERE use_yn = 'Y' "
				+ "ORDER BY CASE item_type WHEN 'PAY' THEN 1 ELSE 2 END, salary_item_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			List<SalaryItem> list = new ArrayList<>();
			while (rs.next()) {
				list.add(toSalaryItem(rs));
			}
			return list;
		}
	}

	// 조회결과를 급여항목으로 옮겨담기
	// 照会結果を給与項目へ転記
	private SalaryItem toSalaryItem(ResultSet rs) throws SQLException {
		SalaryItem item = new SalaryItem();
		item.setSalaryItemId(rs.getInt("salary_item_id"));
		int attendanceTypeId = rs.getInt("attendance_type_id");
		if (!rs.wasNull()) {
			item.setAttendanceTypeId(attendanceTypeId);
		}
		item.setItemType(rs.getString("item_type"));
		item.setItemName(rs.getString("item_name"));
		item.setTaxYn(rs.getString("tax_yn"));
		item.setTaxFreeName(rs.getString("tax_free_name"));
		long taxFreeLimit = rs.getLong("tax_free_limit");
		if (!rs.wasNull()) {
			item.setTaxFreeLimit(taxFreeLimit);
		}
		item.setCalcMethod(rs.getString("calc_method"));
		item.setRoundUnit(rs.getString("round_unit"));
		long defaultAmount = rs.getLong("default_amount");
		if (!rs.wasNull()) {
			item.setDefaultAmount(defaultAmount);
		}
		item.setRemark(rs.getString("remark"));
		item.setUseYn(rs.getString("use_yn"));
		return item;
	}
}
