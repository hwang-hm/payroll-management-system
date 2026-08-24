package retirement.severancepay.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.dao.OtherTaxableIncomeDao;
import retirement.dao.SeverancePayDao;
import retirement.dao.TaxDeferredAccountDao;

public class SeverancePayService {

	private SeverancePayDao severancePayDao = new SeverancePayDao();
	private OtherTaxableIncomeDao otherTaxableIncomeDao = new OtherTaxableIncomeDao();
	private TaxDeferredAccountDao taxDeferredAccountDao = new TaxDeferredAccountDao();

	// DB 저장만 수행
	// DB保存のみ実行
	public void saveSeverance(SeveranceCalculateRequest request, SeveranceCalculateResult result) {

		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 1. 퇴직금 최종 결과 저장 - 저장 후 생성된 severance_pay_id 반환
			// 1. 退職金最終結果の保存 - 保存後に生成された severance_pay_id を返却
			int severancePayId = severancePayDao.insertSeverancePay(conn, request, result);

			// 2. 기타 과세소득 저장
			// 2. その他課税所得の保存
			if (request.getOtherTaxableIncomes() != null) {
				for (OtherTaxableIncomeRequest income : request.getOtherTaxableIncomes()) {
					otherTaxableIncomeDao.insertOtherTaxableIncome(conn, severancePayId, income);
				}
			}

			// 3. 과세이연계좌 저장
			// 3. 課税繰延口座の保存
			if (request.getTaxDeferredAccounts() != null) {
				for (TaxDeferredAccountRequest account : request.getTaxDeferredAccounts()) {
					taxDeferredAccountDao.insertTaxDeferredAccount(conn, severancePayId, account);
				}
			}

			// 4. 모두 성공하면 commit
			// 4. すべて成功すれば commit
			conn.commit();

		} catch (SQLException e) {

			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);

		} finally {

			JdbcUtil.close(conn);
		}
	}

}
