package salary.dayworker.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.service.request.GetSalaryListDTO;

public class GetSalaryListService {
	private SalaryDao salaryDao = new SalaryDao();

	public List<GetSalaryListDTO> getSalaryList(int salaryDateId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return salaryDao.selectAllByDate(conn, salaryDateId);
		} catch (SQLException e) {
			throw new RuntimeException("급여 목록 조회 실패", e);
		}
	}

}
