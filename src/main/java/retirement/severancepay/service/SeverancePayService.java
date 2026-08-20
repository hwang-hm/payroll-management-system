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
	public void saveSeverance(SeveranceCalculateRequest request, SeveranceCalculateResult result) {

		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 1. 퇴직금 최종 결과 저장 - 저장 후 생성된 severance_pay_id 반환
			int severancePayId = severancePayDao.insertSeverancePay(conn, request, result);

			// 2. 기타 과세소득 저장
			if (request.getOtherTaxableIncomes() != null) {
				for (OtherTaxableIncomeRequest income : request.getOtherTaxableIncomes()) {
					otherTaxableIncomeDao.insertOtherTaxableIncome(conn, severancePayId, income);
				}
			}

			// 3. 과세이연계좌 저장
			if (request.getTaxDeferredAccounts() != null) {
				for (TaxDeferredAccountRequest account : request.getTaxDeferredAccounts()) {
					taxDeferredAccountDao.insertTaxDeferredAccount(conn, severancePayId, account);
				}
			}

			// 4. 모두 성공하면 commit
			conn.commit();

		} catch (SQLException e) {

			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);

		} finally {

			JdbcUtil.close(conn);
		}
	}

}
