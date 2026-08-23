package salary.dao;

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
	 * 1. 급여 항목 단건 조회 (PK 기준)
	 * 1. 給与項目単件照会（PK基準）
	 */
	public SalaryItem selectById(Connection conn, int salaryItemId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM salary_item WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryItemId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return toEntity(rs);
			}
			return null;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 2. 항목 유형별(지급: PAY / 공제: DEDUCT) 급여 항목 목록 조회
	 * 2. 項目タイプ別（支給: PAY / 控除: DEDUCT）給与項目一覧照会
	 */
	public List<SalaryItem> selectByType(Connection conn, String itemType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SalaryItem> list = new ArrayList<>();
		String sql = "SELECT * FROM salary_item WHERE item_type = ? ORDER BY salary_item_id ASC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemType);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(toEntity(rs));
			}
			return list;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 3. 신규 급여 항목 등록 (INSERT)
	 * 3. 新規給与項目の登録（INSERT）
	 */
	public boolean insert(Connection conn, SalaryItem item) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO salary_item ("
				+ "salary_item_id, attendance_type_id, item_type, item_name, tax_yn, tax_free_name, tax_free_limit, "
				+ " calc_method, round_unit, default_amount, remark, use_yn, created_at"
				+ ") VALUES (seq_salary_item.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, item.getAttendanceTypeId());
			pstmt.setString(2, item.getItemType());
			pstmt.setString(3, item.getItemName());
			pstmt.setString(4, item.getTaxYn());
			pstmt.setString(5, item.getTaxFreeName());
			pstmt.setObject(6, item.getTaxFreeLimit());
			pstmt.setString(7, item.getCalcMethod());
			pstmt.setString(8, item.getRoundUnit());
			pstmt.setObject(9, item.getDefaultAmount());
			pstmt.setString(10, item.getRemark());
			pstmt.setString(11, item.getUseYn());

			// 등록 처리 성공 여부 반환 (영향을 받은 행 수가 1 이상이면 true)
			// 登録処理の成功有無を返却（影響を受けた行数が1以上ならtrue）
			return pstmt.executeUpdate() > 0;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 4. 급여 항목 정보 수정 (UPDATE)
	 * 4. 給与項目情報の更新（UPDATE）
	 */
	public boolean update(Connection conn, SalaryItem item) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "UPDATE salary_item SET "
				+ "attendance_type_id = ?, item_name = ?, tax_yn = ?, tax_free_name = ?, tax_free_limit = ?, "
				+ "calc_method = ?, round_unit = ?, default_amount = ?, remark = ?, use_yn = ?, updated_at = sysdate "
				+ "WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, item.getAttendanceTypeId());
			pstmt.setString(2, item.getItemName());
			pstmt.setString(3, item.getTaxYn());
			pstmt.setString(4, item.getTaxFreeName());
			pstmt.setObject(5, item.getTaxFreeLimit());
			pstmt.setString(6, item.getCalcMethod());
			pstmt.setString(7, item.getRoundUnit());
			pstmt.setObject(8, item.getDefaultAmount());
			pstmt.setString(9, item.getRemark());
			pstmt.setString(10, item.getUseYn());
			pstmt.setLong(11, item.getSalaryItemId());

			// 수정 처리 성공 여부 반환 (영향을 받은 행 수가 1 이상이면 true)
			// 更新処理の成功有無を返却（影響を受けた行数が1以上ならtrue）
			return pstmt.executeUpdate() > 0;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * 5. 급여 항목 삭제 (DELETE)
	 * 5. 給与項目の削除（DELETE）
	 */
	public boolean delete(Connection conn, int salaryItemId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM salary_item WHERE salary_item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salaryItemId);

			// 삭제 처리 성공 여부 반환 (영향을 받은 행 수가 1 이상이면 true)
			// 削除処理の成功有無を返却（影響を受けた行数が1以上ならtrue）
			return pstmt.executeUpdate() > 0;
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(pstmt);
		}
	}

	/**
	 * ResultSet의 현재 행 데이터를 SalaryItem 엔티티 객체로 매핑하는 헬퍼 메서드
	 * ResultSetの現在行データをSalaryItemエンティティオブジェクトへマッピングするヘルパーメソッド
	 */
	private SalaryItem toEntity(ResultSet rs) throws SQLException {
		SalaryItem item = new SalaryItem(
				rs.getInt("salary_item_id"),
				rs.getObject("attendance_type_id") != null ? rs.getInt("attendance_type_id") : null,
				rs.getString("item_type"),
				rs.getString("item_name"),
				rs.getString("tax_yn"),
				rs.getString("tax_free_name"),
				rs.getObject("tax_free_limit") != null ? rs.getLong("tax_free_limit") : null,
				rs.getString("calc_method"),
				rs.getString("round_unit"),
				rs.getObject("default_amount") != null ? rs.getLong("default_amount") : null,
				rs.getString("remark"),
				rs.getString("use_yn"),
				rs.getDate("created_at"),
				rs.getDate("updated_at")
		);
		return item;
	}
}