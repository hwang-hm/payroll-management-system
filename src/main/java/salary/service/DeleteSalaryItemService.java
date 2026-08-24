package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;

/**
 * 급여 항목 삭제 비즈니스 로직 서비스
 * 給与項目削除ビジネスロジックサービス
 */
public class DeleteSalaryItemService {

	private SalaryItemDao itemDao = new SalaryItemDao();

	/**
	 * 급여 항목 삭제 실행 (트랜잭션 처리)
	 * 給与項目の削除実行（トランザクション処理）
	 * 
	 * @param salaryItemId 삭제할 급여 항목 ID / 削除対象の給与項目ID
	 * @return 삭제 성공 여부 / 削除成功有無
	 */
	public boolean deleteItem(int salaryItemId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			// 트랜잭션 시작 (수동 커밋 설정)
			// トランザクション開始（手動コミット設定）
			conn.setAutoCommit(false);

			// 삭제 수행
			// 削除実行
			boolean result = itemDao.delete(conn, salaryItemId);

			// 트랜잭션 커밋
			// トランザクションコミット
			conn.commit();
			return result;
		} catch (SQLException e) {
			// 예외 발생 시 롤백 처리
			// 例外発生時のロールバック処理
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여항목 삭제 실패 / 給与項目の削除に失敗しました", e);
		} finally {
			// DB 커넥션 자원 해제
			// DB接続リソースの解放
			JdbcUtil.close(conn);
		}
	}

}