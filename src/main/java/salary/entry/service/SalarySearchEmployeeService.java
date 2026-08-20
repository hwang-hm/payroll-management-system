package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.viewdto.EmployeeSearchView;

public class SalarySearchEmployeeService {

	private final SalaryDao salaryDao = new SalaryDao();

	// 이번 급여에 아직 안 넣은 사원 찾기. 검색어 없으면 그냥 전체
	public List<EmployeeSearchView> search(int salaryDateId, String searchKeyword) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
				return salaryDao.selectEmployeesForAdd(conn, salaryDateId);
			}
			return salaryDao.selectEmployeesForAddByKeyword(conn, salaryDateId, searchKeyword);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
