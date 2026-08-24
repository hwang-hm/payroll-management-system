package employee.list.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import employee.dao.EmpDao;
import jdbc.connection.ConnectionProvider;

public class EmpListService {

	private EmpDao empDao = new EmpDao();
	private int size = 10;

	public EmpListPage getEmpPage(int pageNum) {

		int firstRow = 0;
		int endRow = 0;
		List<EmpListData> empListData = null;

		try (Connection conn = ConnectionProvider.getConnection()) {

			int total = empDao.selectCount(conn);

			int regularCount = empDao.selectCountByEmpType(conn, "正社員");
			int contractCount = empDao.selectCountByEmpType(conn, "契約社員");
			int temporaryCount = empDao.selectCountByEmpType(conn, "臨時職");
			int dispatchedCount = empDao.selectCountByEmpType(conn, "派遣社員");
			int commissionedCount = empDao.selectCountByEmpType(conn, "委嘱職");
			int dailyCount = empDao.selectCountByEmpType(conn, "日雇い");

			int activeCount = empDao.selectCountByStatus(conn, "Y");
			int retiredCount = empDao.selectCountByStatus(conn, "N");

			if (total > 0) {
				firstRow = (pageNum - 1) * size + 1;
				endRow = firstRow + size - 1;
				empListData = empDao.select(conn, firstRow, endRow);
			}

			return new EmpListPage(total, pageNum, size, empListData, regularCount, contractCount, temporaryCount,
					dispatchedCount, commissionedCount, dailyCount, activeCount, retiredCount);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}