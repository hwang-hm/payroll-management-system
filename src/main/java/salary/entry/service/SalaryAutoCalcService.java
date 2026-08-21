package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import employee.dao.EmpInfoSalaryDao;
import employee.model.EmpInfoSalary;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.model.SalaryDetail;
import salary.util.SalaryAutoCalcRates;
import salary.util.SalaryAutoCalcRates.DeductionAmounts;

// 선택 사원 급여상세의 공제 6종을 가라 요율로 자동 계산
public class SalaryAutoCalcService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	// detailId → 계산된 금액. 화면에서 스크롤 유지한 채 값만 갱신할 때 씀
	public Map<Integer, Long> autoCalc(int salaryId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Map<Integer, Long> updated = new LinkedHashMap<>();
			Salary salary = salaryDao.selectById(conn, salaryId);
			if (salary == null) {
				return updated;
			}

			List<SalaryDetail> details = salaryDetailDao.selectBySalaryId(conn, salaryId);
			long baseAmount = findBaseAmount(details);
			EmpInfoSalary empInfo = empInfoSalaryDao.selectById(conn, salary.getEmpId());
			if (baseAmount <= 0 && empInfo != null) {
				baseAmount = empInfo.getBaseSalary();
			}

			boolean np = isYn(empInfo == null ? null : empInfo.getNationalPensionYN());
			boolean hi = isYn(empInfo == null ? null : empInfo.getHealthInsuranceYN());
			boolean lt = isYn(empInfo == null ? null : empInfo.getLongtermCareYN());
			boolean ei = isYn(empInfo == null ? null : empInfo.getEmploymentInsuranceYN());
			if (empInfo == null) {
				np = hi = lt = ei = true;
			}

			DeductionAmounts amounts = SalaryAutoCalcRates.calc(baseAmount, np, hi, lt, ei);
			Map<String, Long> amountMap = SalaryAutoCalcRates.toAmountMap(amounts);

			for (SalaryDetail detail : details) {
				String itemName = detail.getItemName();
				if (!amountMap.containsKey(itemName)) {
					continue;
				}
				long amount = amountMap.get(itemName);
				salaryDetailDao.updateAmountAndCalcMethod(conn, detail.getDetailId(), amount, null);
				updated.put(detail.getDetailId(), amount);
			}

			recalculateTotals(conn, salaryId);
			conn.commit();
			return updated;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private long findBaseAmount(List<SalaryDetail> details) {
		for (SalaryDetail detail : details) {
			if ("기본급".equals(detail.getItemName()) && detail.getAmount() != null && detail.getAmount() > 0) {
				return detail.getAmount();
			}
		}
		return 0L;
	}

	private boolean isYn(String value) {
		return "Y".equalsIgnoreCase(value);
	}

	private void recalculateTotals(Connection conn, int salaryId) throws SQLException {
		long totalPay = 0;
		long totalDeduct = 0;
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			long amount = detail.getAmount() != null ? detail.getAmount() : 0L;
			if ("PAY".equals(detail.getItemType())) {
				totalPay += amount;
			} else {
				totalDeduct += amount;
			}
		}
		salaryDao.updateTotals(conn, salaryId, totalPay, totalDeduct, totalPay - totalDeduct);
	}
}
