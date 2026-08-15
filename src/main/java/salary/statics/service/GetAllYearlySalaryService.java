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

public class GetAllYearlySalaryService {
	private YearlySalaryDao dao = new YearlySalaryDao();

	public List<YearlySalaryDTO> get10YearSalaryStats(int targetYear) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 1. DB에서 선택 연도 기준 과거 10개년 데이터 조회 (targetYear - 9 ~ targetYear)
			Map<Integer, YearlySalaryDTO> dbDataMap = dao.getYearlyStatsMap(conn, targetYear);

			int startYear = targetYear - 9;
			int endYear = targetYear;

			// 2. 10개년 연속 리스트 생성 (DB에 없는 연도는 기본값 0 세팅)
			List<YearlySalaryDTO> result = new ArrayList<>();
			for (int y = startYear; y <= endYear; y++) {
				YearlySalaryDTO dto = dbDataMap.getOrDefault(y, new YearlySalaryDTO(y, 0L, 0.0));
				result.add(dto);
			}

			// 3. 전년 대비 증가율 계산
			for (int i = 0; i < result.size(); i++) {
				YearlySalaryDTO curr = result.get(i);
				if (i > 0) {
					YearlySalaryDTO prev = result.get(i - 1);

					// 급여 증가율
					if (prev.getTotalSalaryThousand() > 0) {
						double salGrowth = ((double) (curr.getTotalSalaryThousand() - prev.getTotalSalaryThousand())
								/ prev.getTotalSalaryThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(salGrowth * 10.0) / 10.0); // 소수점 첫째 자리까지 반올림
					} else {
						curr.setSalaryGrowthRate(null); // 전년도 데이터가 없으면 null 처리
					}

					// 인원 증가율
					if (prev.getAvgEmpCount() > 0) {
						double cntGrowth = ((curr.getAvgEmpCount() - prev.getAvgEmpCount()) / prev.getAvgEmpCount())
								* 100.0;
						curr.setCountGrowthRate(Math.round(cntGrowth * 10.0) / 10.0);
					} else {
						curr.setCountGrowthRate(null); // 전년도 데이터가 없으면 null 처리
					}
				} else {
					// 첫 번째 연도는 비교할 전년 데이터가 없어 null 지정
					curr.setSalaryGrowthRate(null);
					curr.setCountGrowthRate(null);
				}
			}

			return result;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
