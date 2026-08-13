package salary.transfer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.TransferHistoryDao;
import salary.model.TransferHistory;
import salary.transfer.viewdto.TransferApplyRow;

public class TransferExecuteService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final TransferHistoryDao transferHistoryDao = new TransferHistoryDao();

	public String execute(int payYear, int payMonth, int paySeq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			List<TransferApplyRow> rows = salaryDao.selectTransferApplyRowsByPeriod(conn, payYear, payMonth, paySeq);
			if (rows.isEmpty()) {
				return "이체할 급여 대상이 없습니다.";
			}

			TransferApplyRow sample = rows.get(0);
			if (isBlank(sample.getWithdrawBank()) || isBlank(sample.getWithdrawAccount())) {
				return "회사 출금계좌(payroll_information)가 없습니다.";
			}

			int successCount = 0;
			int skipNoAccount = 0;
			int skipAlready = 0;
			String memo = String.format("급여%04d-%02d", payYear, payMonth);
			Date today = new Date();

			for (TransferApplyRow row : rows) {
				if (isBlank(row.getSalaryBank()) || isBlank(row.getSalaryAccount())) {
					skipNoAccount++;
					continue;
				}
				if (transferHistoryDao.existsSuccessBySalaryId(conn, row.getSalaryId())) {
					skipAlready++;
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
				history.setTransferResult("성공");
				transferHistoryDao.insert(conn, history);
				successCount++;
			}

			conn.commit();
			return String.format("이체 완료: 성공 %d건, 계좌없음 %d건, 기이체 %d건", successCount, skipNoAccount,
					skipAlready);
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
