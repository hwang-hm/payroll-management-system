package salary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;
import salary.model.SalaryItem;

/**
 * 급여 항목 조회 비즈니스 로직 서비스
 * 給与項目照会ビジネスロジックサービス
 */
public class GetSalaryItemService {

	private SalaryItemDao itemDao = new SalaryItemDao();

	/**
	 * PK 기준 급여 항목 단일 조회
	 * PK基準の給与項目単件照会
	 * 
	 * @param itemId 급여 항목 ID / 給与項目ID
	 * @return 조회된 급여 항목 엔티티 / 照会された給与項目エンティティ
	 */
	public SalaryItem getItemById(int itemId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return itemDao.selectById(conn, itemId);
		} catch (SQLException e) {
			throw new RuntimeException("급여항목 조회 실패 PK: " + itemId + " / 給与項目の照会に失敗しました PK: " + itemId, e);
		} finally {
			// DB 커넥션 자원 해제
			// DB接続リソースの解放
			JdbcUtil.close(conn);
		}
	}

	/**
	 * 항목 유형별(지급/공제) 급여 항목 목록 조회
	 * 項目タイプ別（支給/控除）の給与項目一覧照会
	 * 
	 * @param itemType 항목 유형 (PAY: 지급, DEDUCT: 공제) / 項目タイプ (PAY: 支給, DEDUCT: 控除)
	 * @return 급여 항목 목록 / 給与項目一覧
	 */
	public List<SalaryItem> getItemsByType(String itemType) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return itemDao.selectByType(conn, itemType);
		} catch (SQLException e) {
			throw new RuntimeException("급여항목 목록 조회 실패 / 給与項目一覧の照会に失敗しました", e);
		} finally {
			// DB 커넥션 자원 해제
			// DB接続リソースの解放
			JdbcUtil.close(conn);
		}
	}

}