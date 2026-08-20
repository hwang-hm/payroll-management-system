package employee.empCard.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import employee.dao.DependentDao;
import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.dao.NationalPensionDao;
import employee.dao.RetirementDao;
import employee.dto.EmployeeDetailData;
import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Retirement;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.dao.SeverancePayDao;
import retirement.model.SeverancePay;
import retirement.retire.service.EmpNotFoundExcpetion;

public class EmpCardService {

	private EmpDao empDao = new EmpDao();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();
	private NationalPensionDao npDao = new NationalPensionDao();
	private DependentDao dependentDao = new DependentDao();
	private RetirementDao retirementDao = new RetirementDao();
	private SeverancePayDao severancePayDao = new SeverancePayDao();

	public EmpCardData getEmpCard(int empId) {

		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();

			EmployeeDetailData emp = empDao.selectByIdJoin(conn, empId);
			if (emp == null) {
			    throw new EmpNotFoundExcpetion();
			}
			
			EmpInfoSalary empInfoSalary = empInfoSalaryDao.selectById(conn, empId);
			List<Dependent> dependents = dependentDao.selectById(conn, empId);
			Retirement retirement = retirementDao.selectById(conn, empId);
			SeverancePay severancePay = severancePayDao.selectById(conn, empId);

			return new EmpCardData(emp, empInfoSalary, dependents, retirement, severancePay);

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			JdbcUtil.close(conn);
		}
	}

	public List<EmployeeDetailData> getEmpList() {

	    Connection conn = null;

	    try {
	        conn = ConnectionProvider.getConnection();

	        return empDao.selectAllJoin(conn);

	    } catch (SQLException e) {
	        throw new RuntimeException(e);

	    } finally {
	        JdbcUtil.close(conn);
	    }
	}
}
