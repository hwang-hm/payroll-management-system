package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.statics.dao.EmpMonthlySalaryDao;
import salary.statics.service.request.EmpMonthlySalaryDTO;

/**
 * 개인별 월별 급여 통계 조회 서비스
 * 個人別月別給与統計照会サービス
 */
public class GetEmpMonthlySalaryService {

	private EmpMonthlySalaryDao dao = new EmpMonthlySalaryDao();

	/**
	 * 특정 사원의 지정 연도 12개월 급여, 공제액, 실지급액 통계 가공 목록 조회
	 * 特定社員の指定年度12ヶ月の給与、控除額、手取り額統計加工リストを照会
	 */
	public List<EmpMonthlySalaryDTO> getMonthlyPersonalStats(int empId, int targetYear) {
		Connection conn = null;
		try {
			// 데이터베이스 연결 객체 생성
			// データベース接続オブジェクトの取得
			conn = ConnectionProvider.getConnection();

			// 1. DB에서 개인별 월별 급여 통계 Map 조회 (Key: 월 1~12)
			// 1. DBから個人別月別給与統計Map照会（Key: 月 1〜12）
			Map<Integer, EmpMonthlySalaryDTO> dbMap = dao.getMonthlyPersonalStatsMap(conn, empId, targetYear);

			// 2. Map.getOrDefault를 활용해 1월~12월 리스트 구성 (데이터 없는 월은 기본값 0 세팅)
			// 2. Map.getOrDefaultを活用して1月〜12月のリストを構成（データのない月は初期値0を設定）
			List<EmpMonthlySalaryDTO> result = new ArrayList<>();
			for (int m = 1; m <= 12; m++) {
				EmpMonthlySalaryDTO dto = dbMap.getOrDefault(m, new EmpMonthlySalaryDTO(m, 0L, 0L, 0L));
				result.add(dto);
			}

			return result;

		} catch (SQLException e) {
			// 예외 발생 시 RuntimeException으로 래핑하여 던짐
			// 例外発生時、RuntimeExceptionにラッピングしてスロー
			throw new RuntimeException(e);
		} finally {
			// 데이터베이스 연결 자원 해제
			// データベース接続リソースの解放
			JdbcUtil.close(conn);
		}
	}

}