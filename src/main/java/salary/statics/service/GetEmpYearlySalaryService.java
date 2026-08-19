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

public class GetEmpYearlySalaryService {

	private EmpYearlySalaryDao dao = new EmpYearlySalaryDao();

	// 10개년 개인 통계 데이터 가공
	public List<EmpYearlySalaryDTO> get10YearPersonalStats(int empId, int targetYear) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 1. DB 조회 Map
			Map<Integer, EmpYearlySalaryDTO> dbMap = dao.get10YearPersonalStatsMap(conn, empId, targetYear);

			int startYear = targetYear - 9;
			int endYear = targetYear;

			// 2. 10개년 빈 데이터 보장
			List<EmpYearlySalaryDTO> result = new ArrayList<>();
			for (int y = startYear; y <= endYear; y++) {
				EmpYearlySalaryDTO dto = dbMap.getOrDefault(y, new EmpYearlySalaryDTO(y, 0L, 0L, 0L));
				result.add(dto);
			}

			// 3. 연봉(총지급액) 증가율 계산
			for (int i = 0; i < result.size(); i++) {
				EmpYearlySalaryDTO curr = result.get(i);
				if (i > 0) {
					EmpYearlySalaryDTO prev = result.get(i - 1);
					if (prev.getTotalPayThousand() > 0) {
						double growth = ((double) (curr.getTotalPayThousand() - prev.getTotalPayThousand())
								/ prev.getTotalPayThousand()) * 100.0;
						curr.setSalaryGrowthRate(Math.round(growth * 10.0) / 10.0);
					} else {
						curr.setSalaryGrowthRate(null);
					}
				} else {
					curr.setSalaryGrowthRate(null);
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
