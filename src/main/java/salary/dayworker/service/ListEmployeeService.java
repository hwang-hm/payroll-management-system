package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.DayworkerDao;
import salary.dayworker.model.Dayworker;

public class ListEmployeeService {
	private DayworkerDao dayworkerDao = new DayworkerDao();

	public List<Dayworker> getEmpList(String keyword) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return dayworkerDao.selectEmpByKeyword(conn, keyword);
		} catch (SQLException e) {
			throw new RuntimeException("근로자 목록 조회 실패", e);
		}
	}

}
