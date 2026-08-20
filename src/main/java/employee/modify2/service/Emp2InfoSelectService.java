package employee.modify2.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.RetirementDao;
import employee.model.Retirement;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.dao.SeverancePayDao;
import retirement.model.SeverancePay;

public class Emp2InfoSelectService {
	
	private RetirementDao  retirementDao = new RetirementDao();
	private SeverancePayDao severancePayDao = new SeverancePayDao();
	
	public Retirement getRetirement(int empId) {
		Connection conn = null;
		
		try {
            conn = ConnectionProvider.getConnection();
      
            Retirement retirement = retirementDao.selectById(conn, empId);
   
            return retirement;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JdbcUtil.close(conn);
        }
	}
	
	public SeverancePay getSeverancePay(int empId) {

	    Connection conn = null;

	    try {
	        conn = ConnectionProvider.getConnection();

	        return severancePayDao.selectById(conn, empId);

	    } catch (SQLException e) {
	        throw new RuntimeException(e);

	    } finally {
	        JdbcUtil.close(conn);
	    }
	}
	
	
}
