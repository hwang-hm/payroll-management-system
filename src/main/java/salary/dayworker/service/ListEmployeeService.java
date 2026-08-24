package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.DayworkerDao;
import salary.dayworker.model.Dayworker;

/**
 * 사원 목록 조회 서비스 (전체 및 키워드 검색)
 * 社員リスト照会サービス (全体およびキーワード検索)
 */
public class ListEmployeeService {

	// 사원 데이터 액세스 DAO / 社員データアクセスDAO
	private DayworkerDao dayworkerDao = new DayworkerDao();

	/**
	 * 키워드(사원명/사번) 기반 사원 목록 조회
	 * キーワード(社員名/社員番号)に基づく社員リスト照会
	 * 
	 * @param keyword 검색 키워드 (null 또는 빈 문자열일 경우 전체 조회) / 検索キーワード (nullまたは空文字の場合は全体照会)
	 * @return 사원 목록 / 社員リスト
	 */
	public List<Dayworker> getEmpList(String keyword) {
		// try-with-resources 구문을 활용한 DB 커넥션 자동 반납 처리
		// try-with-resources 構文を活用したDBコネクション自動返却処理
		try (Connection conn = ConnectionProvider.getConnection()) {
			return dayworkerDao.selectEmpByKeyword(conn, keyword);
		} catch (SQLException e) {
			// SQL 예외 발생 시 RuntimeException으로 래핑하여 상위로 전파
			// SQL例外発生時、RuntimeExceptionにラッピングして上位へ伝播
			throw new RuntimeException("근로자 목록 조회 실패 / 労働者リスト照会失敗", e);
		}
	}
}