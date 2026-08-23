package salary.transfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import salary.model.TransferHistory;
import salary.util.SqlIn;

public class TransferHistoryDao {

	// 그 급여건에 이체 이력이 있는지 확인
	public boolean existsBySalaryId(Connection conn, int salaryId) throws SQLException {
		String sql = "SELECT 1 FROM transfer_history WHERE salary_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, salaryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 이체 이력이 하나라도 있는 salary_id 집합
	public Set<Integer> selectSalaryIdsHavingHistory(Connection conn, List<Integer> salaryIds) throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return Collections.emptySet();
		}
		String sql = "SELECT DISTINCT salary_id FROM transfer_history WHERE salary_id IN ("
				+ SqlIn.placeholders(salaryIds.size()) + ")";
		Set<Integer> ids = new HashSet<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			SqlIn.setInts(pstmt, 1, salaryIds);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ids.add(rs.getInt("salary_id"));
				}
			}
		}
		return ids;
	}

	// 해당 salary들의 이체 이력 삭제
	public void deleteBySalaryIds(Connection conn, List<Integer> salaryIds) throws SQLException {
		if (salaryIds == null || salaryIds.isEmpty()) {
			return;
		}
		String sql = "DELETE FROM transfer_history WHERE salary_id IN ("
				+ SqlIn.placeholders(salaryIds.size()) + ")";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			SqlIn.setInts(pstmt, 1, salaryIds);
			pstmt.executeUpdate();
		}
	}

	// 이체 한 건 이력 저장
	public int insert(Connection conn, TransferHistory history) throws SQLException {
		String sql = "INSERT INTO transfer_history (transfer_id, salary_id, transfer_date, withdraw_bank, "
				+ "withdraw_account, deposit_bank, deposit_account, depositor_name, transfer_amount, "
				+ "deposit_memo, transfer_result) VALUES (seq_transfer_history.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			bindInsert(pstmt, history);
			pstmt.executeUpdate();
		}
		return 0;
	}

	// 이체 이력 batch insert
	public void insertBatch(Connection conn, List<TransferHistory> histories) throws SQLException {
		if (histories == null || histories.isEmpty()) {
			return;
		}
		String sql = "INSERT INTO transfer_history (transfer_id, salary_id, transfer_date, withdraw_bank, "
				+ "withdraw_account, deposit_bank, deposit_account, depositor_name, transfer_amount, "
				+ "deposit_memo, transfer_result) VALUES (seq_transfer_history.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (TransferHistory history : histories) {
				bindInsert(pstmt, history);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
	}

	private void bindInsert(PreparedStatement pstmt, TransferHistory history) throws SQLException {
		pstmt.setInt(1, history.getSalaryId());
		pstmt.setTimestamp(2, toTimestamp(history.getTransferDate()));
		pstmt.setString(3, history.getWithdrawBank());
		pstmt.setString(4, history.getWithdrawAccount());
		pstmt.setString(5, history.getDepositBank());
		pstmt.setString(6, history.getDepositAccount());
		pstmt.setString(7, history.getDepositorName());
		pstmt.setLong(8, history.getTransferAmount());
		pstmt.setString(9, history.getDepositMemo());
		pstmt.setString(10, history.getTransferResult());
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
