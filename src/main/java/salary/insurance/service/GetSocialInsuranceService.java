package salary.insurance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.insurance.dao.SocialInsuranceDao;
import salary.insurance.service.request.GetSocialInsuranceDTO;

/**
 * 4대보험 공제 내역 조회 비즈니스 로직 서비스
 * 4大保険控除明細照会ビジネスロジックサービス
 */
public class GetSocialInsuranceService {
	// 4대보험 DAO 객체 / 4大保険DAOオブジェクト
	private SocialInsuranceDao dao = new SocialInsuranceDao();

	/**
	 * 급여 정산 ID 기준 4대보험 공제 내역 목록 조회
	 * 給与計算ID基準の4大保険控除明細一覧照会
	 * 
	 * @param salaryDateId 급여 정산 ID / 給与計算ID
	 * @return 4대보험 공제 내역 DTO 목록 / 4大保険控除明細DTO一覧
	 */
	public List<GetSocialInsuranceDTO> getSocialInsuranceList(int salaryDateId) {
		Connection conn = null;
		try {
			// DB 커넥션 획득 / DB接続の取得
			conn = ConnectionProvider.getConnection();
			
			// DAO를 통한 목록 조회 / DAOを通じた一覧照会
			return dao.getSocialInsuranceList(conn, salaryDateId);
		} catch (SQLException e) {
			// 예외 발생 시 런타임 예외로 전환 / 例外発生時にランタイム例外へ変換
			throw new RuntimeException(e);
		} finally {
			// DB 커넥션 해제 / DB接続の解放
			JdbcUtil.close(conn);
		}
	}

}