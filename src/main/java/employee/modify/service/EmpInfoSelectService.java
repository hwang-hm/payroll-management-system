package employee.modify.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import employee.dao.DependentDao;
import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.dao.NationalPensionDao;
import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Employee;
import employee.model.NationalPensionInfo;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.retire.service.EmpNotFoundExcpetion;

public class EmpInfoSelectService {
	
	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();
	private NationalPensionDao npDao = new NationalPensionDao();
	private DependentDao dependentDao = new DependentDao();

	
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
	
	public EmpInfoSalary getEmpInfoSalary(int empId) {
		Connection conn = null;
		
		try {
            conn = ConnectionProvider.getConnection();
      
            EmpInfoSalary empInfoSalary = empInfoSalaryDao.selectById(conn, empId);
          
            return empInfoSalary;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            JdbcUtil.close(conn);
        }
	}
	
	public NationalPensionInfo getNationalPensionInfo(int empId) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			NationalPensionInfo nationalPensionInfo = npDao.selectById(conn, empId);
			
			return nationalPensionInfo;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public List<Dependent> getDependent(int empId) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			List<Dependent> dependent = dependentDao.selectById(conn, empId);
			
			return dependent;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
