package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDateDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.util.PayPeriodParams;

public class SalaryLoadPreviousService {

	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDateService salaryDateService = new SalaryDateService();
	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	// 선택한 지난 귀속년월 급여를 이번 달로 복사. 이미 있는 사원은 건너뜀
	// 選択した過去帰属年月の給与を今月へコピー。既にいる社員はスキップ
	public void load(PayPeriodParams target, PayPeriodParams source) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			SalaryDate sourceDate = salaryDateDao.selectByPeriod(conn, source.getPayYear(), source.getPayMonth(),
					source.getPaySeq());
			if (sourceDate == null) {
				conn.commit();
				return;
			}

			SalaryDate targetDate = salaryDateService.ensureByPeriod(conn, target);
			if (sourceDate.getSalaryDateId() == targetDate.getSalaryDateId()) {
				conn.commit();
				return;
			}

			Set<Integer> existingEmpIds = salaryDao.selectEmpIdsBySalaryDateId(conn, targetDate.getSalaryDateId());
			List<Salary> sourceSalaries = salaryDao.selectBySalaryDateIdExcludingDayworkers(conn,
					sourceDate.getSalaryDateId());

			for (Salary sourceSalary : sourceSalaries) {
				if (existingEmpIds.contains(sourceSalary.getEmpId())) {
					continue;
				}
				Salary copied = new Salary();
				copied.setSalaryDateId(targetDate.getSalaryDateId());
				copied.setEmpId(sourceSalary.getEmpId());
				copied.setTotalPay(sourceSalary.getTotalPay());
				copied.setTotalDeduct(sourceSalary.getTotalDeduct());
				copied.setNetPay(sourceSalary.getNetPay());
				int newSalaryId = salaryDao.insert(conn, copied);
				salaryDetailDao.copyFromSalary(conn, sourceSalary.getSalaryId(), newSalaryId);
				existingEmpIds.add(sourceSalary.getEmpId());
			}

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
