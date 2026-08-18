package salary.transfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import salary.model.TransferHistory;

public class TransferHistoryDao {

	// 그 급여건이 이미 이체 성공했는지 확인. 중복이체 막으려고
	public boolean existsSuccessBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT 1 FROM transfer_history WHERE salary_id = ? AND transfer_result = '성공'";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 이체 한 건 이력 저장
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

	// 조회기간 안 이체건 몇 개인지. 페이지 계산용
	public int selectCountByPeriod(Connection conn, Date startDate, Date endDate) throws SQLException {
		String sql = "SELECT COUNT(*) FROM transfer_history WHERE TRUNC(transfer_date) BETWEEN ? AND ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, toSqlDate(startDate));
			pstmt.setDate(2, toSqlDate(endDate));
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		}
	}

	// 조회기간 안 이체결과, 한 페이지분만큼만 가져옴
	public List<TransferHistory> selectByPeriod(Connection conn, Date startDate, Date endDate, int firstRow,
			int endRow) throws SQLException {
		String sql = "SELECT * FROM (SELECT ROWNUM AS rnum, a.* FROM ("
				+ "SELECT transfer_id, salary_id, transfer_date, withdraw_bank, withdraw_account, "
				+ "deposit_bank, deposit_account, depositor_name, transfer_amount, deposit_memo, transfer_result "
				+ "FROM transfer_history WHERE TRUNC(transfer_date) BETWEEN ? AND ? "
				+ "ORDER BY transfer_date DESC, transfer_id DESC"
				+ ") a WHERE ROWNUM <= ?) WHERE rnum >= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, toSqlDate(startDate));
			pstmt.setDate(2, toSqlDate(endDate));
			pstmt.setInt(3, endRow);
			pstmt.setInt(4, firstRow);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<TransferHistory> list = new ArrayList<>();
				while (rs.next()) {
					list.add(toHistory(rs));
				}
				return list;
			}
		}
	}

	// 조회결과를 이체이력으로 옮겨담기
	private TransferHistory toHistory(ResultSet rs) throws SQLException {
		TransferHistory history = new TransferHistory();
		history.setTransferId(rs.getInt("transfer_id"));
		history.setSalaryId(rs.getInt("salary_id"));
		history.setTransferDate(rs.getTimestamp("transfer_date"));
		history.setWithdrawBank(rs.getString("withdraw_bank"));
		history.setWithdrawAccount(rs.getString("withdraw_account"));
		history.setDepositBank(rs.getString("deposit_bank"));
		history.setDepositAccount(rs.getString("deposit_account"));
		history.setDepositorName(rs.getString("depositor_name"));
		history.setTransferAmount(rs.getLong("transfer_amount"));
		history.setDepositMemo(rs.getString("deposit_memo"));
		history.setTransferResult(rs.getString("transfer_result"));
		return history;
	}

	// Date를 Timestamp로 바꿔서 넣기
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	// Date를 sql Date로 바꿔서 조회조건에 쓰기
	private java.sql.Date toSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
}
