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
import salary.statics.service.request.EmpYearlySalaryDTO;

public class GetEmpMonthlySalaryService {

	private EmpMonthlySalaryDao dao = new EmpMonthlySalaryDao();

	// 12개월 개인 통계 데이터 가공
	public List<EmpMonthlySalaryDTO> getMonthlyPersonalStats(int empId, int targetYear) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 1. DB 조회 Map
			Map<Integer, EmpMonthlySalaryDTO> dbMap = dao.getMonthlyPersonalStatsMap(conn, empId, targetYear);

			// 2. Map.getOrDefault를 활용해 1월~12월 리스트 구성 (데이터 없는 월은 0으로 채움)
			List<EmpMonthlySalaryDTO> result = new ArrayList<>();
			for (int m = 1; m <= 12; m++) {
				EmpMonthlySalaryDTO dto = dbMap.getOrDefault(m, new EmpMonthlySalaryDTO(m, 0L, 0L, 0L));
				result.add(dto);
			}

			return result;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
