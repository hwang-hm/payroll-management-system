package salary.statics.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import salary.statics.dao.SalaryCompositionDao;
import salary.statics.service.request.SalaryCompositionDTO;

public class SalaryCompositionService {

	private SalaryCompositionDao dao = new SalaryCompositionDao();

	public SalaryCompositionDTO getSalaryComposition(int empId, int year, int month) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			return dao.selectSalaryComposition(conn, empId, year, month);
		} catch (SQLException e) {
			throw new RuntimeException("급여항목 구성 통계 조회 중 오류 발생", e);
		}
	}
}
