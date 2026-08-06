package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import salary.model.SalaryDetail;

public class SalaryDetailDao {

	public boolean insertDayworkerSalaryDetail(Connection conn, SalaryDetail detail) throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "INSERT INTO salary_detail (" + "detail_id, " + "salary_id, " + "salary_item_id, " + "item_type, "
				+ "item_name, " + "tax_yn, " + "tax_free_name, " + "tax_free_limit, " + "calc_method, " + "round_unit, "
				+ "pay_type, " + "default_amount, " + "memo, " + "amount" + ") VALUES ("
				+ "SEQ_SALARY_DETAIL.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + ")";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, detail.getSalaryId());
			pstmt.setLong(2, detail.getSalaryItemId());

			pstmt.setString(3, detail.getItemType());
			pstmt.setString(4, detail.getItemName());

			pstmt.setString(5, detail.getTaxYn());
			pstmt.setString(6, detail.getTaxFreeName());

			pstmt.setLong(7, detail.getTaxFreeLimit());

			pstmt.setString(8, detail.getCalcMethod());
			pstmt.setString(9, detail.getRoundUnit());

			pstmt.setString(10, detail.getPayType());

			pstmt.setLong(11, detail.getDefaultAmount());

			pstmt.setString(12, detail.getMemo());

			pstmt.setLong(13, detail.getAmount());

			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				return true;
			}

			return false;

		} finally {
			JdbcUtil.close(pstmt);
		}
	}

}
