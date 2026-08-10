package employee.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.DependentDao;
import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Employee;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class EmpRegisterService {
	private EmpRegisterMapper mapper = new EmpRegisterMapper();
	
	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();
	private DependentDao dependentDao = new DependentDao();
	
	public int registerEmp(EmpRegisterRequest registerReq ) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Employee employee = mapper.toEmp(registerReq.getEmpReq());
			Employee registeredEmp = empDao.insertEmp(conn,employee);
			
			if (registeredEmp == null) {
			    throw new RuntimeException("fail to insert employee");
			}
			
			int empId = registeredEmp.getEmpId();
			
			EmpInfoSalary empInfoSalary = mapper.toEmpInfoSalary(registerReq.getEmpInfoSalaryReq());
			
			  // 근로소득자 → 4대보험 전체 적용
	        if ("WORKER".equals(empInfoSalary.getIncomeTaxType())) {
	            empInfoSalary.setNationalPensionYN("Y");
	            empInfoSalary.setHealthInsuranceYN("Y");
	            empInfoSalary.setLongtermCareYN("Y");
	            empInfoSalary.setEmploymentInsuranceYN("Y");
	        }


	        // 건강보험 → 장기요양보험 자동 적용
	        if ("Y".equals(empInfoSalary.getHealthInsuranceYN())) {
	            empInfoSalary.setLongtermCareYN("Y");
	        }
	        
			empInfoSalaryDao.insertEmpInfoSalary(conn, empId, empInfoSalary);
			
			for (DependentRequest dependentReq : registerReq.getDependentReqs()) {

	                Dependent dependent =
	                        mapper.toDependent(dependentReq);

	                dependentDao.insertDependent(
	                        conn, empId, dependent);
	            }
			
			
			conn.commit();
			return empId;
			
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

