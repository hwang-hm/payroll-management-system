package retirement.list.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import retirement.dao.RetirementEmpDao;


public class RetirementListService {
	private RetirementEmpDao empDao = new RetirementEmpDao();
	private int size = 10;
	
	public RetirementEmpPage  getRetirementEmpPage (int pageNum) {
		int firstRow = 0;
		int endRow = 0;
		List<RetirementEmpListData> retirementEmpListData = null;
		
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = empDao.selectCount(conn);
			
			if (total > 0) {
				firstRow = (pageNum - 1) * size + 1;
				endRow = firstRow + size - 1;
				retirementEmpListData = empDao.select(conn, firstRow, endRow);
			}
			
			return new RetirementEmpPage (total, pageNum, size, retirementEmpListData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
