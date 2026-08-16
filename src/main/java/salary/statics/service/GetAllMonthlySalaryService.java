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

public class GetAllMonthlySalaryService {

	private MonthlySalaryDao dao = new MonthlySalaryDao();

	public List<MonthlySalaryDTO> get12MonthSalaryStats(int targetYear) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 1. DAO에서 Map 형태로 데이터 조회 (Key: 월 1~12)
			Map<Integer, MonthlySalaryDTO> dbMap = dao.getMonthlyStatsMap(conn, targetYear);

			// 2. Map.getOrDefault를 활용해 1월~12월 리스트 구성 (데이터 없는 월은 0으로 채움)
			List<MonthlySalaryDTO> result = new ArrayList<>();
			for (int m = 1; m <= 12; m++) {
				MonthlySalaryDTO dto = dbMap.getOrDefault(m, new MonthlySalaryDTO(m, 0L, 0.0));
				result.add(dto);
			}

			// 3. 전월 대비 증가율 계산 (2월 ~ 12월)
			for (int i = 0; i < result.size(); i++) {
				MonthlySalaryDTO curr = result.get(i);
				if (i > 0) {
					MonthlySalaryDTO prev = result.get(i - 1);

					// 전월 급여가 0보다 클 때만 증가율 계산
					if (prev.getTotalSalaryThousand() > 0) {
						double salGrowth = ((double) (curr.getTotalSalaryThousand() - prev.getTotalSalaryThousand())
								/ prev.getTotalSalaryThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(salGrowth * 10.0) / 10.0);
					} else {
						curr.setSalaryGrowthRate(null);
					}

					// 전월 인원이 0보다 클 때만 증가율 계산
					if (prev.getEmpCount() > 0) {
						double cntGrowth = ((curr.getEmpCount() - prev.getEmpCount()) / prev.getEmpCount()) * 100.0;
						curr.setCountGrowthRate(Math.round(cntGrowth * 10.0) / 10.0);
					} else {
						curr.setCountGrowthRate(null);
					}
				} else {
					// 1월은 전월 비교군이 없으므로 null
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
