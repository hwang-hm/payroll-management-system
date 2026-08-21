package employee.certificate.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.EmpDao;
import employee.dto.EmployeeDetailData;
import jdbc.connection.ConnectionProvider;

public class PrintCertificateService {
	
	private EmpDao empDao = new EmpDao();

	public EmployeeDetailData selectEmp(int empId) {
		
		try (Connection conn = ConnectionProvider.getConnection()) {
			 return empDao.selectByIdJoin(conn, empId);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
