package attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import attendance.model.VacationType;

/**
 * 휴가항목 데이터베이스 작업을 담당하는 DAO 클래스
 * 休暇項目のデータベース処理を担当するDAOクラス
 */
public class VacationTypeDao {

    /**
     * 전체 휴가항목을 조회한다.
     * すべての休暇項目を照会する。
     */
	public List<VacationType> selectAll(Connection conn) throws SQLException {
		
		 // 조회된 휴가항목을 저장할 목록
	    // 照会した休暇項目を保存するリスト
	    List<VacationType> vacationTypes = new ArrayList<>();

	    // 휴가항목 번호의 오름차순으로 전체 휴가항목 조회
	    // 休暇項目番号の昇順ですべての休暇項目を照会
	    String sql = "SELECT vacation_type_id, vacation_name, "
	            + "apply_start_date, apply_end_date, "
	            + "vacation_days, use_yn "
	            + "FROM vacation_type "
	            + "ORDER BY vacation_type_id";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {

	            VacationType vacationType = new VacationType();

	            vacationType.setVacationTypeId(
	                    rs.getInt("vacation_type_id"));

	            vacationType.setVacationName(
	                    rs.getString("vacation_name"));

	            vacationType.setApplyStartDate(
	                    rs.getDate("apply_start_date"));

	            vacationType.setApplyEndDate(
	                    rs.getDate("apply_end_date"));

	            vacationType.setVacationDays(
	                    rs.getString("vacation_days"));

	            vacationType.setUseYn(
	                    rs.getString("use_yn"));

	            vacationTypes.add(vacationType);
	        }
	    }

	    return vacationTypes;
	}
	
	/**
	 * 새로운 휴가항목을 추가한다.
	 * 新しい休暇項目を追加する。
	 *
	 * @param conn DB 연결 객체 / DB接続オブジェクト
	 * @param vacationType 추가할 휴가항목 / 追加する休暇項目
	 * @return 추가된 행의 개수 / 追加された行数
	 * @throws SQLException SQL 실행 오류 / SQL実行エラー
	 */
	
	public int insert(
	        Connection conn,
	        VacationType vacationType) throws SQLException {

	    // 휴가항목 번호는 Oracle 시퀀스를 이용하여 자동 생성한다.
	    // 休暇項目番号はOracleシーケンスを利用して自動生成する。
	    String sql = "INSERT INTO vacation_type ("
	            + "vacation_type_id, "
	            + "vacation_name, "
	            + "apply_start_date, "
	            + "apply_end_date, "
	            + "vacation_days, "
	            + "use_yn"
	            + ") VALUES ("
	            + "vacation_type_seq.NEXTVAL, "
	            + "?, ?, ?, ?, ?"
	            + ")";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(
	                1, vacationType.getVacationName());

	        pstmt.setDate(
	                2, vacationType.getApplyStartDate());

	        pstmt.setDate(
	                3, vacationType.getApplyEndDate());

	        pstmt.setString(
	                4, vacationType.getVacationDays());

	        pstmt.setString(
	                5, vacationType.getUseYn());

	        return pstmt.executeUpdate();
	    }
	}
	
	/**
	 * 기존 휴가항목을 수정한다.
	 * 既存の休暇項目を修正する。
	 *
	 * @param conn DB 연결 객체 / DB接続オブジェクト
	 * @param vacationType 수정할 휴가항목 / 修正する休暇項目
	 * @return 수정된 행의 개수 / 修正された行数
	 * @throws SQLException SQL 실행 오류 / SQL実行エラー
	 */
	
	public int update(
	        Connection conn,
	        VacationType vacationType) throws SQLException {

	    String sql = "UPDATE vacation_type "
	            + "SET vacation_name = ?, "
	            + "apply_start_date = ?, "
	            + "apply_end_date = ?, "
	            + "vacation_days = ?, "
	            + "use_yn = ? "
	            + "WHERE vacation_type_id = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(
	                1, vacationType.getVacationName());

	        pstmt.setDate(
	                2, vacationType.getApplyStartDate());

	        pstmt.setDate(
	                3, vacationType.getApplyEndDate());

	        pstmt.setString(
	                4, vacationType.getVacationDays());

	        pstmt.setString(
	                5, vacationType.getUseYn());

	        pstmt.setInt(
	                6, vacationType.getVacationTypeId());

	        return pstmt.executeUpdate();
	    }
	}
	
	/**
	 * 휴가항목 번호에 해당하는 휴가항목을 삭제한다.
	 * 休暇項目番号に該当する休暇項目を削除する。
	 *
	 * @param conn DB 연결 객체 / DB接続オブジェクト
	 * @param vacationTypeId 삭제할 휴가항목 번호 / 削除する休暇項目番号
	 * @return 삭제된 행의 개수 / 削除された行数
	 * @throws SQLException SQL 실행 오류 / SQL実行エラー
	 */
	public int delete(
	        Connection conn,
	        int vacationTypeId) throws SQLException {

	    String sql = "DELETE FROM vacation_type "
	            + "WHERE vacation_type_id = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, vacationTypeId);

	        return pstmt.executeUpdate();
	    }
	}
}
