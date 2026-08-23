package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.statics.dao.EmpYearlySalaryDao;
import salary.statics.service.request.EmpYearlySalaryDTO;

/**
 * 개인별 연도별 급여 통계 조회 서비스
 * 個人別年度別給与統計照会サービス
 */
public class GetEmpYearlySalaryService {

	private EmpYearlySalaryDao dao = new EmpYearlySalaryDao();

	/**
	 * 특정 사원의 지정 연도 기준 최근 10개년 급여 통계 및 연봉 증가율 계산 목록 조회
	 * 特定社員の指定年度基準直近10年間の給与統計および年収増加率計算リストを照会
	 */
	public List<EmpYearlySalaryDTO> get10YearPersonalStats(int empId, int targetYear) {
		Connection conn = null;
		try {
			// 데이터베이스 연결 객체 생성
			// データベース接続オブジェクトの取得
			conn = ConnectionProvider.getConnection();

			// 1. DB에서 개인별 최근 10개년 급여 통계 Map 조회 (Key: 연도)
			// 1. DBから個人別直近10年間の給与統計Map照会（Key: 年度）
			Map<Integer, EmpYearlySalaryDTO> dbMap = dao.get10YearPersonalStatsMap(conn, empId, targetYear);

			// 조회 대상 10개년 시작 연도 및 종료 연도 계산
			// 照会対象10年間の開始年度および終了年度の計算
			int startYear = targetYear - 9;
			int endYear = targetYear;

			// 2. 10개년 연속 리스트 생성 (DB에 없는 연도는 기본값 0 세팅)
			// 2. 10年間連続のリスト生成（DBに存在しない年度は初期値0を設定）
			List<EmpYearlySalaryDTO> result = new ArrayList<>();
			for (int y = startYear; y <= endYear; y++) {
				EmpYearlySalaryDTO dto = dbMap.getOrDefault(y, new EmpYearlySalaryDTO(y, 0L, 0L, 0L));
				result.add(dto);
			}

			// 3. 전년 대비 연봉(총 지급액) 증가율 계산
			// 3. 前年比年収（支給総額）増加率の計算
			for (int i = 0; i < result.size(); i++) {
				EmpYearlySalaryDTO curr = result.get(i);
				if (i > 0) {
					EmpYearlySalaryDTO prev = result.get(i - 1);
					// 전년도 총 지급액이 0보다 큰 경우에만 증가율 계산 (소수점 첫째 자리 반올림)
					// 前年度支給総額が0より大きい場合のみ増加率を計算（小数第1位四捨五入）
					if (prev.getTotalPayThousand() > 0) {
						double growth = ((double) (curr.getTotalPayThousand() - prev.getTotalPayThousand())
								/ prev.getTotalPayThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(growth * 10.0) / 10.0);
					} else {
						// 전년도 데이터가 없는 경우 null 처리
						// 前年度データが存在しない場合はnull処理
						curr.setSalaryGrowthRate(null);
					}
				} else {
					// 첫 번째 연도는 비교할 전년 데이터가 없어 null 지정
					// 1年目は比較対象の前年データが存在しないためnull指定
					curr.setSalaryGrowthRate(null);
				}
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