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

public class EmpModifyService {

	private EmpModifyMapper mapper = new EmpModifyMapper();

	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();
	private NationalPensionDao npDao = new NationalPensionDao();
	private DependentDao dependentDao = new DependentDao();

	public void modifyEmp(int empId, EmpModifyRequest modifyrReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Employee emp = mapper.toEmp(modifyrReq.getEmpReq());
			
			int updatedEmp = empDao.updateEmp(conn, empId, emp);
			if (updatedEmp == 0) {
			    throw new RuntimeException("사원 정보 수정 실패");
			}
			
			EmpInfoSalary empInfoSalary = mapper.toEmpInfoSalary(modifyrReq.getEmpInfoSalaryReq());

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

			int updatedEmpInfoSalary = empInfoSalaryDao.updateEmpInfosalary(conn, empId, empInfoSalary);
			if (updatedEmpInfoSalary == 0) {
			    throw new RuntimeException("사원 급여 정보 수정 실패");
			}
			
			for (DependentRequest dependentReq : modifyrReq.getDependentReqs()) {
				Dependent dependent = mapper.toDependent(dependentReq);
				int updatedDependent = dependentDao.updateDependent(conn, empId, dependent);
				
				if (updatedDependent == 0) {
			        throw new RuntimeException("부양가족 정보 수정 실패");
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
