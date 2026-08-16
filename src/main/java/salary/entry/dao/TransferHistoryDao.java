package salary.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import salary.model.TransferHistory;

public class TransferHistoryDao {

	public boolean existsSuccessBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT 1 FROM transfer_history WHERE salary_id = ? AND transfer_result = '성공'";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	public int insert(Connection conn, TransferHistory history) throws SQLException {
		String seqSql = "SELECT seq_transfer_history.NEXTVAL FROM dual";
		String insertSql = "INSERT INTO transfer_history (transfer_id, salary_id, transfer_date, withdraw_bank, "
				+ "withdraw_account, deposit_bank, deposit_account, depositor_name, transfer_amount, "
				+ "deposit_memo, transfer_result) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int transferId;
		try (PreparedStatement seqPstmt = conn.prepareStatement(seqSql); ResultSet rs = seqPstmt.executeQuery()) {
			if (!rs.next()) {
				throw new SQLException("transfer_history 시퀀스 조회 실패");
			}
			transferId = rs.getInt(1);
		}

		try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
			pstmt.setInt(1, transferId);
			pstmt.setInt(2, history.getSalaryId());
			pstmt.setTimestamp(3, toTimestamp(history.getTransferDate()));
			pstmt.setString(4, history.getWithdrawBank());
			pstmt.setString(5, history.getWithdrawAccount());
			pstmt.setString(6, history.getDepositBank());
			pstmt.setString(7, history.getDepositAccount());
			pstmt.setString(8, history.getDepositorName());
			pstmt.setLong(9, history.getTransferAmount());
			pstmt.setString(10, history.getDepositMemo());
			pstmt.setString(11, history.getTransferResult());
			pstmt.executeUpdate();
		}
		return transferId;
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
