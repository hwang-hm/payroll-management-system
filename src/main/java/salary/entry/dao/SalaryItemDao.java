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
	private SalaryItem toSalaryItem(ResultSet rs) throws SQLException {
		SalaryItem item = new SalaryItem();
		item.setSalaryItemId(rs.getInt("salary_item_id"));
		item.setAttendanceTypeId(rs.getInt("attendance_type_id"));
		item.setItemType(rs.getString("item_type"));
		item.setItemName(rs.getString("item_name"));
		item.setTaxYn(rs.getString("tax_yn"));
		item.setTaxFreeName(rs.getString("tax_free_name"));
		item.setTaxFreeLimit(rs.getLong("tax_free_limit"));
		item.setCalcMethod(rs.getString("calc_method"));
		item.setRoundUnit(rs.getString("round_unit"));
		item.setDefaultAmount(rs.getLong("default_amount"));
		item.setRemark(rs.getString("remark"));
		item.setUseYn(rs.getString("use_yn"));
		return item;
	}
}
