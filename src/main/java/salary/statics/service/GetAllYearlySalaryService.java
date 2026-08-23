package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.statics.dao.YearlySalaryDao;
import salary.statics.service.request.YearlySalaryDTO;

/**
 * 전체 연도별 급여 통계 조회 서비스
 * 全体年度別給与統計照会サービス
 */
public class GetAllYearlySalaryService {
	private YearlySalaryDao dao = new YearlySalaryDao();

	/**
	 * 지정 연도 기준 과거 10개년 급여 통계 및 전년 대비 증감률 계산 목록 조회
	 * 指定年度基準の過去10年間の給与統計および前年比増減率計算リストを照会
	 */
	public List<YearlySalaryDTO> get10YearSalaryStats(int targetYear) {
		Connection conn = null;
		try {
			// 데이터베이스 연결 객체 생성
			// データベース接続オブジェクトの取得
			conn = ConnectionProvider.getConnection();

			// 1. DB에서 선택 연도 기준 과거 10개년 데이터 조회 (targetYear - 9 ~ targetYear)
			// 1. DBから選択年度基準の過去10年間のデータ照会（targetYear - 9 〜 targetYear）
			Map<Integer, YearlySalaryDTO> dbDataMap = dao.getYearlyStatsMap(conn, targetYear);

			int startYear = targetYear - 9;
			int endYear = targetYear;

			// 2. 10개년 연속 리스트 생성 (DB에 없는 연도는 기본값 0 세팅)
			// 2. 10年間連続のリスト生成（DBに存在しない年度は初期値0を設定）
			List<YearlySalaryDTO> result = new ArrayList<>();
			for (int y = startYear; y <= endYear; y++) {
				YearlySalaryDTO dto = dbDataMap.getOrDefault(y, new YearlySalaryDTO(y, 0L, 0.0));
				result.add(dto);
			}

			// 3. 전년 대비 증가율 계산
			// 3. 前年比増加率の計算
			for (int i = 0; i < result.size(); i++) {
				YearlySalaryDTO curr = result.get(i);
				if (i > 0) {
					YearlySalaryDTO prev = result.get(i - 1);

					// 급여 증가율 계산 (전년도 급여가 0보다 클 때만 계산, 소수점 첫째 자리 반올림)
					// 給与増加率の計算（前年給与が0より大きい場合のみ計算、小数第1位四捨五入）
					if (prev.getTotalSalaryThousand() > 0) {
						double salGrowth = ((double) (curr.getTotalSalaryThousand() - prev.getTotalSalaryThousand())
								/ prev.getTotalSalaryThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(salGrowth * 10.0) / 10.0);
					} else {
						// 전년도 데이터가 없으면 null 처리
						// 前年度データが存在しない場合はnull処理
						curr.setSalaryGrowthRate(null);
					}

					// 인원 증가율 계산 (전년도 인원이 0보다 클 때만 계산)
					// 人数増加率の計算（前年人数が0より大きい場合のみ計算）
					if (prev.getAvgEmpCount() > 0) {
						double cntGrowth = ((curr.getAvgEmpCount() - prev.getAvgEmpCount()) / prev.getAvgEmpCount())
								* 100.0;
						curr.setCountGrowthRate(Math.round(cntGrowth * 10.0) / 10.0);
					} else {
						// 전년도 데이터가 없으면 null 처리
						// 前年度データが存在しない場合はnull処理
						curr.setCountGrowthRate(null);
					}
				} else {
					// 첫 번째 연도는 비교할 전년 데이터가 없어 null 지정
					// 1年目は比較対象の前年データが存在しないためnull指定
					curr.setSalaryGrowthRate(null);
					curr.setCountGrowthRate(null);
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