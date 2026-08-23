package salary.dayworker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import salary.model.SalaryDetail;

/**
 * 급여 상세 내역 데이터 액세스 객체 (DAO)
 * 給与詳細履歴データアクセスオブジェクト (DAO)
 */
public class SalaryDetailDao {

	/**
	 * 급여 ID(salary_id) 기준 상세 내역 전체 조회
	 * 給与ID(salary_id)に基づく詳細履歴の全体照会
	 * 
	 * @param conn DB 커넥션 / DBコネクション
	 * @param salaryId 급여 헤더 ID / 給与ヘッダーID
	 * @return 급여 상세 내역 리스트 / 給与詳細履歴リスト
	 */
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
				sd.setDetailId(rs.getInt("detail_id"));
				sd.setSalaryId(rs.getInt("salary_id"));
				
				// salary_item_id Nullable 처리 / salary_item_id Nullable 処理
				sd.setSalaryItemId(rs.getObject("salary_item_id") != null ? rs.getInt("salary_item_id") : null);
				
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
			// 자원 해제 / リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 급여 수정/재저장 시 기존 상세 항목 일괄 삭제
	 * 給与修正/再保存時、既存の詳細項目を一括削除
	 * 
	 * @param conn DB 커넥션 / DBコネクション
	 * @param salaryId 급여 헤더 ID / 給与ヘッダーID
	 */
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

	/**
	 * 급여 상세 항목 단건 등록
	 * 給与詳細項目の単件登録
	 * 
	 * @param conn DB 커넥션 / DBコネクション
	 * @param detail 저장할 급여 상세 객체 / 保存する給与詳細オブジェクト
	 * @return 등록 성공 여부 / 登録成功有無
	 */
	public boolean insert(Connection conn, SalaryDetail detail) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary_detail (detail_id, salary_id, salary_item_id, item_type, item_name, tax_yn, round_unit, default_amount, memo, amount) "
				+ "VALUES (seq_salary_detail.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, detail.getSalaryId());
			
			// FK 참조 항목 ID Null 처리 (직접 입력 항목 대응)
			// FK参照項目ID Null処理 (直接入力項目対応)
			if (detail.getSalaryItemId() != null) {
				pstmt.setLong(2, detail.getSalaryItemId());
			} else {
				pstmt.setNull(2, java.sql.Types.NULL);
			}
			
			pstmt.setString(3, detail.getItemType());
			pstmt.setString(4, detail.getItemName());
			pstmt.setString(5, detail.getTaxYn());
			
			// 기본값 보장 처리 (RoundUnit, DefaultAmount)
			// デフォルト値保証処理 (RoundUnit, DefaultAmount)
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