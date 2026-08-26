package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import salary.util.SalaryTotals;

// 선택 사원 급여상세의 공제 6종을 가라 요율로 자동 계산
// 選択社員の給与詳細の控除6種を仮レートで自動計算
public class SalaryAutoCalcService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	// detailId → 계산된 금액. 화면에서 스크롤 유지한 채 값만 갱신할 때 씀
	// detailId → 計算済み金額。画面でスクロール維持のまま値だけ更新するときに使用
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

			List<Integer> detailIds = new ArrayList<>();
			List<Long> amountList = new ArrayList<>();
			List<String> calcMethods = new ArrayList<>();

			for (SalaryDetail detail : details) {
				String canonical = SalaryAutoCalcRates.canonicalName(detail.getItemName());
				if (canonical == null || !amountMap.containsKey(canonical)) {
					continue;
				}
				long amount = amountMap.get(canonical);
				detail.setAmount(amount);
				detail.setCalcMethod(null);
				detailIds.add(detail.getDetailId());
				amountList.add(amount);
				calcMethods.add(null);
				updated.put(detail.getDetailId(), amount);
			}

			salaryDetailDao.updateAmountAndCalcMethodBatch(conn, detailIds, amountList, calcMethods);
			long[] totals = SalaryTotals.fromDetails(details);
			salaryDao.updateTotals(conn, salaryId, totals[0], totals[1], totals[2]);
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
			if (SalaryAutoCalcRates.isBasicPayItem(detail.getItemName()) && detail.getAmount() != null
					&& detail.getAmount() > 0) {
				return detail.getAmount();
			}
		}
		return 0L;
	}

	// 미설정(null/blank)은 적용(Y). 명시적 N만 제외
	// 未設定(null/blank)は適用(Y)。明示的な N のみ除外
	private boolean isYn(String value) {
		if (value == null || value.trim().isEmpty()) {
			return true;
		}
		return !"N".equalsIgnoreCase(value.trim());
	}
}
