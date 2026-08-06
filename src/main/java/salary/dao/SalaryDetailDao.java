package salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.SalaryDetail;

public class SalaryDetailDao {

	// salaryId로 급여상세 리스트를 가져온다.
	public List<SalaryDetail> selectBySalaryId(Connection conn, long salaryId) throws SQLException {
		String sql = "SELECT * FROM salary_detail WHERE salary_id = ? ORDER BY detail_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<SalaryDetail> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toDetail(rs));
				}
				return list;
			}
		}
	}

	private SalaryDetail toDetail(ResultSet rs) throws SQLException {
		SalaryDetail detail = new SalaryDetail();
		detail.setDetailId(rs.getLong("detail_id"));
		detail.setSalaryId(rs.getLong("salary_id"));
		detail.setSalaryItemId(rs.getLong("salary_item_id"));
		detail.setItemType(rs.getString("item_type"));
		detail.setItemName(rs.getString("item_name"));
		detail.setTaxYn(rs.getString("tax_yn"));
		detail.setTaxFreeName(rs.getString("tax_free_name"));
		detail.setTaxFreeLimit(rs.getLong("tax_free_limit"));
		detail.setCalcMethod(rs.getString("calc_method"));
		detail.setRoundUnit(rs.getString("round_unit"));
		detail.setPayType(rs.getString("pay_type"));
		detail.setDefaultAmount(rs.getLong("default_amount"));
		detail.setMemo(rs.getString("memo"));
		detail.setAmount(rs.getLong("amount"));
		return detail;
	}
}
