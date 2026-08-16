package employee.register2.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.EmpDao;
import employee.dao.RetirementDao;
import employee.model.Employee;
import employee.model.Retirement;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.retire.service.EmpNotFoundExcpetion;
import retirement.retire.service.PermissonDeniedException;

public class EmpRegister2Service {
	
	private EmpRegister2Mapper mapper2 = new EmpRegister2Mapper(); 
	private EmpDao empDao = new EmpDao();
	private RetirementDao retirementDao = new RetirementDao();
	
	
	public void registereEmp2(EmpRegister2Request empRegisterReq2) {
        Connection conn = null;
        
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            
            Retirement retirement = mapper2.toRetirement(empRegisterReq2.getRetirementReq());
            Employee emp = empDao.selectById(conn, retirement.getEmpId());
            
            if (emp == null) {
                throw new EmpNotFoundExcpetion();
            }
            
            
            retirementDao.updateRetiredStatus(conn, retirement.getEmpId(), retirement.getRetirementDate());
            retirementDao.insertRetirement(conn, retirement);

            conn.commit();

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException(e);

        } catch (PermissonDeniedException e) {
            JdbcUtil.rollback(conn);
            throw e;

        } finally {
            JdbcUtil.close(conn);
        }
    }
	
	public Employee getEmp(int empId) {
		Connection conn = null;
		
		try {
            conn = ConnectionProvider.getConnection();
      
            Employee emp = empDao.selectById(conn, empId);
            
            if (emp == null) {
                throw new EmpNotFoundExcpetion();
            }
            
            return emp;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JdbcUtil.close(conn);
        }
	}
	
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

}
