package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import salary.statics.dao.SalaryCompositionDao;
import salary.statics.service.request.SalaryCompositionDTO;

/**
 * 급여 구성 항목별 통계 조회 서비스
 * 給与構成項目別統計照会サービス
 */
public class SalaryCompositionService {

	// 급여 구성 항목 DAO 객체 생성
	// 給与構成項目DAOオブジェクトの生成
	private SalaryCompositionDao dao = new SalaryCompositionDao();

	/**
	 * 특정 사원의 지정 연월 급여 구성 항목 및 비율 데이터 조회
	 * 特定社員の指定年月における給与構成項目および割合データの照会
	 */
	public SalaryCompositionDTO getSalaryComposition(int empId, int year, int month) {
		// try-with-resources 구문을 활용한 DB 연결 및 자동 자원 해제
		// try-with-resources構文を活用したDB接続および自動リソース解放
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 급여 구성 항목 및 비율 데이터 조회 DAO 메서드 호출
			// 給与構成項目および割合データ照会DAOメソッドの呼び出し
			return dao.selectSalaryComposition(conn, empId, year, month);
		} catch (SQLException e) {
			// 예외 발생 시 Custom 메시지를 포함한 RuntimeException으로 래핑하여 던짐
			// 例外発生時、カスタムメッセージを含むRuntimeExceptionにラッピングしてスロー
			throw new RuntimeException("급여항목 구성 통계 조회 중 오류 발생", e);
		}
	}
}