package employee.modify2.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.EmpDao;
import employee.dao.RetirementDao;
import employee.model.Employee;
import employee.model.Retirement;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class EmpModify2Service {

		private EmpModify2Mapper mapper = new EmpModify2Mapper();
		
		private EmpDao empDao = new EmpDao();
		private RetirementDao retirementDao = new RetirementDao();
		
		public void modifyEmp2(int empId, EmpModify2Request empModifyReq) {
			Connection conn = null;
			
			try {
				conn = ConnectionProvider.getConnection();
	            conn.setAutoCommit(false); 
	            
	            Employee emp = mapper.toEmp(empModifyReq.getEmpReq());
				
				int updatedEmp = empDao.updateEmp(conn, empId, emp);
				if (updatedEmp == 0) {
				    throw new RuntimeException("사원 정보 수정 실패");
				}
				
	            Retirement retirement = mapper.toRetirement(empModifyReq.getRetirementReq());
	            
	            int updatedRetirement = retirementDao.updateRetirement(conn, empId, retirement);
				
	            
	            conn.commit();
	            
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				JdbcUtil.rollback(conn);
				throw e;
			} finally {
				JdbcUtil.close(conn);
			}
			
		}
}
