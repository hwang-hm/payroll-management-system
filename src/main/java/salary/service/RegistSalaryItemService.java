package salary.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryItemDao;
import salary.model.SalaryItem;
import salary.service.request.RegistItemRequest;

/**
 * 급여 항목 등록 및 수정 비즈니스 로직 서비스
 * 給与項目登録および更新ビジネスロジックサービス
 */
public class RegistSalaryItemService {
	private SalaryItemDao itemDao = new SalaryItemDao();

	/**
	 * 급여 항목 저장 (ID 존재 여부에 따라 수정 또는 신규 등록 실행, 트랜잭션 적용)
	 * 給与項目の保存（IDの有無に応じて更新または新規登録を実行、トランザクション適用）
	 * 
	 * @param req 등록/수정 요청 DTO / 登録・更新リクエストDTO
	 * @return 저장 성공 여부 / 保存成功有無
	 */
	public boolean saveOrUpdate(RegistItemRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			// 트랜잭션 시작 (수동 커밋 설정)
			// トランザクション開始（手動コミット設定）
			conn.setAutoCommit(false);

			// DTO -> Entity 변환
			// DTO -> Entity変換
			SalaryItem item = toItem(req);

			boolean result;
			// ID가 존재하는 경우 UPDATE, 없는 경우 INSERT 처리
			// IDが存在する場合はUPDATE、存在しない場合はINSERT処理
			if (item.getSalaryItemId() != null) {
				result = itemDao.update(conn, item);
			} else {
				result = itemDao.insert(conn, item);
			}

			// 트랜잭션 커밋
			// トランザクションコミット
			conn.commit();
			return result;
		} catch (SQLException e) {
			// 예외 발생 시 롤백 처리
			// 例外発生時のロールバック処理
			JdbcUtil.rollback(conn);
			throw new RuntimeException("급여항목 저장 실패 / 給与項目の保存に失敗しました", e);
		} finally {
			// DB 커넥션 자원 해제
			// DB接続リソースの解放
			JdbcUtil.close(conn);
		}
	}

	/**
	 * RegistItemRequest DTO 객체를 SalaryItem 엔티티 객체로 변환하는 헬퍼 메서드
	 * RegistItemRequest DTOオブジェクトをSalaryItemエンティティオブジェクトに変換するヘルパーメソッド
	 */
	private SalaryItem toItem(RegistItemRequest req) {
		return new SalaryItem(
				req.getSalaryItemId(),
				req.getAttendanceTypeId(),
				req.getItemType(),
				req.getItemName(),
				req.getTaxYn(),
				req.getTaxFreeName(),
				req.getTaxFreeLimit(),
				req.getCalcMethod(),
				req.getRoundUnit(),
				req.getDefaultAmount(),
				req.getRemark(),
				req.getUseYn(),
				null, // created_at (DB에서 sysdate로 자동 처리 / DBでsysdateにて自動処理)
				null  // updated_at (DB에서 sysdate로 자동 처리 / DBでsysdateにて自動処理)
		);
	}
}