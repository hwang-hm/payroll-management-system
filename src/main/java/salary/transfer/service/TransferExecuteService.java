package salary.transfer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.model.TransferHistory;
import salary.transfer.dao.TransferApplyDao;
import salary.transfer.dao.TransferHistoryDao;
import salary.transfer.viewdto.TransferApplyRow;
import salary.util.PayPeriodParams;

public class TransferExecuteService {

	private final TransferApplyDao transferApplyDao = new TransferApplyDao();
	private final TransferHistoryDao transferHistoryDao = new TransferHistoryDao();

	// 해당 귀속년월 대상으로 이체 돌림. 계좌없거나 이미 이체된 사람은 건너뜀
	// 該当帰属年月を対象に振込実行。口座なしまたは既振込の人はスキップ
	public String execute(PayPeriodParams period) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			List<TransferApplyRow> rows = transferApplyDao.selectTransferApplyRowsByPeriod(conn, period.getPayYear(),
					period.getPayMonth(), period.getPaySeq());
			if (rows.isEmpty()) {
				return "振込対象の給与がありません。";
			}

			TransferApplyRow sample = rows.get(0);
			if (isBlank(sample.getWithdrawBank()) || isBlank(sample.getWithdrawAccount())) {
				return "会社出金口座(payroll_information)がありません。";
			}

			List<Integer> candidateIds = new ArrayList<>();
			for (TransferApplyRow row : rows) {
				if (!isBlank(row.getSalaryBank()) && !isBlank(row.getSalaryAccount())) {
					candidateIds.add(row.getSalaryId());
				}
			}
			Set<Integer> alreadyTransferred = transferHistoryDao.selectSalaryIdsHavingHistory(conn, candidateIds);

			int successCount = 0;
			int skipNoAccount = 0;
			int skipAlreadyTransferred = 0;
			String memo = String.format("給与%04d-%02d", period.getPayYear(), period.getPayMonth());
			Date today = new Date();
			List<TransferHistory> toInsert = new ArrayList<>();

			for (TransferApplyRow row : rows) {
				if (isBlank(row.getSalaryBank()) || isBlank(row.getSalaryAccount())) {
					skipNoAccount++;
					continue;
				}
				if (alreadyTransferred.contains(row.getSalaryId())) {
					skipAlreadyTransferred++;
					continue;
				}

				TransferHistory history = new TransferHistory();
				history.setSalaryId(row.getSalaryId());
				history.setTransferDate(today);
				history.setWithdrawBank(row.getWithdrawBank());
				history.setWithdrawAccount(row.getWithdrawAccount());
				history.setDepositBank(row.getSalaryBank());
				history.setDepositAccount(row.getSalaryAccount());
				history.setDepositorName(row.getNameKor());
				history.setTransferAmount(row.getNetPay());
				history.setDepositMemo(memo);
				history.setTransferResult("成功");
				toInsert.add(history);
				successCount++;
			}

			transferHistoryDao.insertBatch(conn, toInsert);
			conn.commit();
			return String.format("振込完了: %d件、口座なし %d件、既振込 %d件", successCount, skipNoAccount,
					skipAlreadyTransferred);
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 빈 문자열인지 확인
	// 空文字列か確認
	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
