package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.model.SalaryItem;

/**
 * 급여 항목 데이터 액세스 객체 (DAO)
 * 給与項目データアクセスオブジェクト (DAO)
 */
public class SalaryItemDao {

	/**
	 * 현재 사용 중(use_yn = 'Y')인 공제 항목 목록 조회
	 * 現在使用中(use_yn = 'Y')の控除項目一覧の照会
	 * 
	 * @param conn DB 커넥션 / DBコネクション
	 * @return 활성화된 공제 항목 리스트 / 活性化された控除項目リスト
	 * @throws SQLException DB 처리 예외 / DB処理例外
	 */
	public List<SalaryItem> selectActiveDeductionItems(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 사용 중인 공제(DEDUCT) 항목 조회 SQL / 使用中の控除(DEDUCT)項目照会SQL
		String sql = "SELECT salary_item_id, item_type, item_name, tax_yn, round_unit, default_amount, remark "
				+ "FROM salary_item WHERE item_type = 'DEDUCT' AND use_yn = 'Y' ORDER BY salary_item_id ASC";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			List<SalaryItem> list = new ArrayList<>();
			while (rs.next()) {
				// 조회 결과 엔티티 매핑 / 照会結果エンティティマッピング
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
			// DB 자원 반납 / DBリソースの返却
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}