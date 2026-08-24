package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDateDao;
import salary.model.SalaryDate;

/**
 * 급여 정산일자 저장 및 수정 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 給与精算日時の保存および更新ビジネスロジックを処理するサービス類です。
 */
public class SaveSalaryDateService {
	private SalaryDateDao salaryDateDao = new SalaryDateDao();

	/**
	 * 정산일자 정보를 신규 저장하거나 기존 데이터를 수정합니다.
	 * 精算日時情報を新規保存、または既存データを更新します。
	 * 
	 * @param salaryDate 정산일자 domain 객체 / 精算日時 domainオブジェクト
	 * @return 저장/수정 성공 여부 (true/false) / 保存・更新の成功有無 (true/false)
	 */
	public boolean saveOrUpdate(SalaryDate salaryDate) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			// 트랜잭션 수동 관리 시작
			// トランザクションの手動管理を開始
			conn.setAutoCommit(false);

			// PK(salaryDateId)가 없는 경우, 동일 귀속연/월/차수 데이터가 존재하는지 확인
			// PK(salaryDateId)がない場合、同一の帰属年/月/回数のデータが存在するか確認
			if (salaryDate.getSalaryDateId() == null) {
				SalaryDate existData = salaryDateDao.selectByYearMonthSeq(conn, salaryDate.getPayYear(),
						salaryDate.getPayMonth(), salaryDate.getPaySeq());

				if (existData != null) {
					// DB에 이미 동일 귀속년월/차수가 존재하면 해당 PK를 주입하여 수정 모드로 전환
					// DBに既に同一の帰属年月/回数が存在する場合は該当するPKをセットし更新モードへ転換
					salaryDate.setSalaryDateId(existData.getSalaryDateId());
				}
			}

			boolean result;
			if (salaryDate.getSalaryDateId() != null) {
				// PK가 존재하면 기존 데이터 수정 (UPDATE)
				// PKが存在する場合は既存 データの更新 (UPDATE)
				result = salaryDateDao.update(conn, salaryDate);
			} else {
				// PK가 존재하지 않으면 신규 데이터 등록 (INSERT)
				// PKが存在しない場合は新規 データの登録 (INSERT)
				result = salaryDateDao.insert(conn, salaryDate);
			}

			// 트랜잭션 커밋
			// トランザクションのコミット
			conn.commit();
			return result;

		} catch (SQLException e) {
			// 오류 발생 시 트랜잭션 롤백
			// エラー発生時のトランザクションロールバック
			JdbcUtil.rollback(conn);
			throw new RuntimeException("SalaryDate 저장 처리 실패 / SalaryDateの保存処理に失敗しました", e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}