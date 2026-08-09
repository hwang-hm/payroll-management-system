package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.JdbcUtil;
import salary.model.SalaryItem;

public class SalaryItemDao {

	// 현재 사용 중(use_yn = 'Y')인 공제 항목 목록 조회
	public List<SalaryItem> selectActiveDeductionItems(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT salary_item_id, item_type, item_name, tax_yn, round_unit, default_amount, remark "
				+ "FROM salary_item WHERE item_type = 'DEDUCT' AND use_yn = 'Y' ORDER BY salary_item_id ASC";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<SalaryItem> list = new ArrayList<>();
			while (rs.next()) {
				SalaryItem item = new SalaryItem();
				item.setSalaryItemId(rs.getInt("salary_item_id"));
				item.setItemType(rs.getString("item_type"));
				item.setItemName(rs.getString("item_name"));
				item.setTaxYn(rs.getString("tax_yn"));
				item.setRoundUnit(rs.getString("round_unit"));
				item.setDefaultAmount(rs.getLong("default_amount"));
				item.setRemark(rs.getString("remark"));
				list.add(item);
			}
			return list;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}