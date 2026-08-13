package retirement.retire.service;

import java.sql.Connection;
import java.sql.SQLException;

import employee.dao.EmpDao;
import employee.model.Employee;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import retirement.dao.RetirementEmpDao;

public class EmpRetireService {

    private RetirementEmpDao retirementEmpDao = new RetirementEmpDao();
    private EmpDao empDao = new EmpDao();

    public void retireEmp(EmpRetireRequest empRetireReq) {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            Employee emp = empDao.selectById(conn, empRetireReq.getEmpId());
            if (emp == null) {
                throw new EmpNotFoundExcpetion();
            }

            if (!canModify(empRetireReq.getEmpId(), emp.getEmpId())) {
                throw new PermissonDeniedException();
            }

            retirementEmpDao.updateRetiredStatus(conn, empRetireReq.getEmpId(), empRetireReq.getRetirementDate());
            retirementEmpDao.insertRetirement(conn, empRetireReq);

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

    private boolean canModify(Integer empRetireReqId, Integer empId) {
        return empId.equals(empRetireReqId);
    }
}
