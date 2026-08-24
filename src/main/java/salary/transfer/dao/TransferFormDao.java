package salary.transfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import salary.model.TransferForm;
import salary.model.TransferFormItem;

public class TransferFormDao {

	// 저장된 이체양식 목록
	// 保存済み振込様式一覧
	public List<TransferForm> selectAllForms(Connection conn) throws SQLException {
		String sql = "SELECT form_id, form_name FROM transfer_form ORDER BY form_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			List<TransferForm> list = new ArrayList<>();
			while (rs.next()) {
				TransferForm form = new TransferForm();
				form.setFormId(rs.getInt("form_id"));
				form.setFormName(rs.getString("form_name"));
				list.add(form);
			}
			return list;
		}
	}

	// 양식 하나 찾아오기
	// 様式を1件取得
	public TransferForm selectFormById(Connection conn, int formId) throws SQLException {
		String sql = "SELECT form_id, form_name FROM transfer_form WHERE form_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, formId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				TransferForm form = new TransferForm();
				form.setFormId(rs.getInt("form_id"));
				form.setFormName(rs.getString("form_name"));
				return form;
			}
		}
	}

	// 이체양식 새로 만들기
	// 振込様式を新規作成
	public int insertForm(Connection conn, String formName) throws SQLException {
		String seqSql = "SELECT seq_transfer_form.NEXTVAL FROM dual";
		String insertSql = "INSERT INTO transfer_form (form_id, form_name) VALUES (?, ?)";
		int formId;
		try (PreparedStatement seqPstmt = conn.prepareStatement(seqSql); ResultSet rs = seqPstmt.executeQuery()) {
			if (!rs.next()) {
				throw new SQLException("transfer_form 시퀀스 조회 실패");
			}
			formId = rs.getInt(1);
		}
		try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
			pstmt.setInt(1, formId);
			pstmt.setString(2, formName);
			pstmt.executeUpdate();
		}
		return formId;
	}

	// 양식 이름만 수정
	// 様式名のみ修正
	public void updateFormName(Connection conn, int formId, String formName) throws SQLException {
		String sql = "UPDATE transfer_form SET form_name = ? WHERE form_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, formName);
			pstmt.setInt(2, formId);
			pstmt.executeUpdate();
		}
	}

	// 그 양식에 들어있는 표시항목들
	// その様式に含まれる表示項目
	public List<TransferFormItem> selectItemsByFormId(Connection conn, int formId) throws SQLException {
		String sql = "SELECT item_id, form_id, item_code, display_order "
				+ "FROM transfer_form_item WHERE form_id = ? ORDER BY display_order, item_id";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, formId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<TransferFormItem> list = new ArrayList<>();
				while (rs.next()) {
					TransferFormItem item = new TransferFormItem();
					item.setItemId(rs.getInt("item_id"));
					item.setFormId(rs.getInt("form_id"));
					item.setItemCode(rs.getString("item_code"));
					item.setDisplayOrder(rs.getInt("display_order"));
					list.add(item);
				}
				return list;
			}
		}
	}

	// 양식 항목 지우고 다시 넣을 때 씀
	// 様式項目を消して再投入するときに使用
	public void deleteItemsByFormId(Connection conn, int formId) throws SQLException {
		String sql = "DELETE FROM transfer_form_item WHERE form_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, formId);
			pstmt.executeUpdate();
		}
	}

	// 양식에 표시할 항목 한 줄 추가
	// 様式に表示する項目1行を追加
	public void insertItem(Connection conn, int formId, String itemCode, int displayOrder) throws SQLException {
		String sql = "INSERT INTO transfer_form_item (item_id, form_id, item_code, display_order) "
				+ "VALUES (seq_transfer_form_item.NEXTVAL, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, formId);
			pstmt.setString(2, itemCode);
			pstmt.setInt(3, displayOrder);
			pstmt.executeUpdate();
		}
	}

	// 양식 표시항목 batch insert
	// 様式表示項目の batch insert
	public void insertItemsBatch(Connection conn, int formId, List<String> itemCodes) throws SQLException {
		if (itemCodes == null || itemCodes.isEmpty()) {
			return;
		}
		String sql = "INSERT INTO transfer_form_item (item_id, form_id, item_code, display_order) "
				+ "VALUES (seq_transfer_form_item.NEXTVAL, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int order = 1;
			for (String itemCode : itemCodes) {
				pstmt.setInt(1, formId);
				pstmt.setString(2, itemCode);
				pstmt.setInt(3, order++);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
	}
}
