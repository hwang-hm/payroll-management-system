package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.statics.dao.MonthlySalaryDao;
import salary.statics.service.request.MonthlySalaryDTO;

/**
 * 전체 월별 급여 통계 조회 서비스
 * 全体月別給与統計照会サービス
 */
public class GetAllMonthlySalaryService {

	private MonthlySalaryDao dao = new MonthlySalaryDao();

	/**
	 * 지정 연도의 1월~12월 급여 통계 및 전월 대비 증감률 계산 목록 조회
	 * 指定年度の1月〜12月の給与統計および前月比増減率計算リストを照会
	 */
	public List<MonthlySalaryDTO> get12MonthSalaryStats(int targetYear) {
		Connection conn = null;
		try {
			// 데이터베이스 연결 객체 생성
			// データベース接続オブジェクトの取得
			conn = ConnectionProvider.getConnection();

			// 1. DAO에서 Map 형태로 데이터 조회 (Key: 월 1~12)
			// 1. DAOからMap形式でデータ照会（Key: 月 1〜12）
			Map<Integer, MonthlySalaryDTO> dbMap = dao.getMonthlyStatsMap(conn, targetYear);

			// 2. Map.getOrDefault를 활용해 1월~12월 리스트 구성 (데이터 없는 월은 0으로 채움)
			// 2. Map.getOrDefaultを活用して1月〜12月のリストを構成（データのない月は0で補填）
			List<MonthlySalaryDTO> result = new ArrayList<>();
			for (int m = 1; m <= 12; m++) {
				MonthlySalaryDTO dto = dbMap.getOrDefault(m, new MonthlySalaryDTO(m, 0L, 0.0));
				result.add(dto);
			}

			// 3. 전월 대비 증가율 계산 (2월 ~ 12월)
			// 3. 前月比増加率の計算（2月〜12月）
			for (int i = 0; i < result.size(); i++) {
				MonthlySalaryDTO curr = result.get(i);
				if (i > 0) {
					MonthlySalaryDTO prev = result.get(i - 1);

					// 전월 급여가 0보다 클 때만 증가율 계산
					// 前月給与が0より大きい場合のみ増加率を計算
					if (prev.getTotalSalaryThousand() > 0) {
						double salGrowth = ((double) (curr.getTotalSalaryThousand() - prev.getTotalSalaryThousand())
								/ prev.getTotalSalaryThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(salGrowth * 10.0) / 10.0);
					} else {
						curr.setSalaryGrowthRate(null);
					}

					// 전월 인원이 0보다 클 때만 증가율 계산
					// 前月人数が0より大きい場合のみ増加率を計算
					if (prev.getEmpCount() > 0) {
						double cntGrowth = ((curr.getEmpCount() - prev.getEmpCount()) / prev.getEmpCount()) * 100.0;
						curr.setCountGrowthRate(Math.round(cntGrowth * 10.0) / 10.0);
					} else {
						curr.setCountGrowthRate(null);
					}
				} else {
					// 1월은 전월 비교군이 없으므로 null
					// 1月は前月比較対象がないためnull
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