package employee.modify.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import employee.dao.DependentDao;
import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Employee;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class EmpModifyService {

	private EmpModifyMapper mapper = new EmpModifyMapper();

	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();
	private DependentDao dependentDao = new DependentDao();

	public void modifyEmp(int empId, EmpModifyRequest modifyrReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Employee emp = mapper.toEmp(modifyrReq.getEmpReq());
			
			int updatedEmp = empDao.updateEmp(conn, empId, emp);
			if (updatedEmp == 0) {
			    throw new RuntimeException("Failed to update employee information");
			}
			
			EmpInfoSalary empInfoSalary = mapper.toEmpInfoSalary(modifyrReq.getEmpInfoSalaryReq());

			// 근로소득자 → 4대보험 전체 적용
			// 給与所得者 → 4大保険を全体適用
			if ("WORKER".equals(empInfoSalary.getIncomeTaxType())) {
				empInfoSalary.setNationalPensionYN("Y");
				empInfoSalary.setHealthInsuranceYN("Y");
				empInfoSalary.setLongtermCareYN("Y");
				empInfoSalary.setEmploymentInsuranceYN("Y");
			}

			// 건강보험 → 장기요양보험 자동 적용
			// 健康保険 → 介護保険を自動適用
			if ("Y".equals(empInfoSalary.getHealthInsuranceYN())) {
				empInfoSalary.setLongtermCareYN("Y");
			}

			int updatedEmpInfoSalary = empInfoSalaryDao.updateEmpInfosalary(conn, empId, empInfoSalary);
			if (updatedEmpInfoSalary == 0) {
			    throw new RuntimeException("Failed to update employee salary information");
			}
			
			for (DependentRequest dependentReq : modifyrReq.getDependentReqs()) {
				Dependent dependent = mapper.toDependent(dependentReq);
				int updatedDependent = dependentDao.updateDependent(conn, empId, dependent);
				
				if (updatedDependent == 0) {
			        throw new RuntimeException("Failed to update dependent information");
				}
			}

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
