package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import employee.dao.EmpInfoSalaryDao;
import employee.model.EmpInfoSalary;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import salary.entry.dao.SalaryAttendanceDao;
import salary.entry.dao.SalaryDao;
import salary.entry.dao.SalaryDateDao;
import salary.entry.dao.SalaryDetailDao;
import salary.entry.dao.SalaryItemDao;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;
import salary.util.SalaryAutoCalcRates;

public class SalaryAddEmployeesService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final SalaryItemDao salaryItemDao = new SalaryItemDao();
	private final SalaryAttendanceDao salaryAttendanceDao = new SalaryAttendanceDao();
	private final EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	// 선택한 사원들을 이번 급여에 추가. 이미 들어가 있으면 건너뜀.
	// 마지막으로 추가된 salaryId 반환 (없으면 null)
	public Integer add(int salaryDateId, List<Integer> empIds) {
		if (empIds == null || empIds.isEmpty()) {
			return null;
		}

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			SalaryDate salaryDate = salaryDateDao.selectById(conn, salaryDateId);
			if (salaryDate == null) {
				throw new IllegalArgumentException("salary_date not found: " + salaryDateId);
			}

			Integer templateSalaryId = salaryDao.selectFirstSalaryIdBySalaryDateId(conn, salaryDateId);
			Integer lastAddedSalaryId = null;

			for (Integer empId : empIds) {
				if (empId == null || salaryDao.existsBySalaryDateIdAndEmpId(conn, salaryDateId, empId)) {
					continue;
				}

				Salary salary = new Salary();
				salary.setSalaryDateId(salaryDateId);
				salary.setEmpId(empId);
				salary.setTotalPay(0L);
				salary.setTotalDeduct(0L);
				salary.setNetPay(0L);

				int salaryId = salaryDao.insert(conn, salary);
				createDetails(conn, salaryId, empId, templateSalaryId, salaryDate);
				recalculateTotals(conn, salaryId);
				lastAddedSalaryId = salaryId;

				if (templateSalaryId == null) {
					templateSalaryId = salaryId;
				}
			}

			conn.commit();
			return lastAddedSalaryId;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	// 구조는 템플릿(또는 salary_item), 금액은 근태·사원정보·항목 기본값에서 채움
	private void createDetails(Connection conn, int salaryId, int empId, Integer templateSalaryId,
			SalaryDate salaryDate) throws SQLException {
		if (templateSalaryId != null) {
			createDetailsFromTemplate(conn, salaryId, templateSalaryId);
		} else {
			createDetailsFromSalaryItems(conn, salaryId);
		}
		applyExternalAmounts(conn, salaryId, empId, salaryDate);
	}

	private void createDetailsFromSalaryItems(Connection conn, int salaryId) throws SQLException {
		for (SalaryItem item : salaryItemDao.selectActiveItems(conn)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(salaryId);
			detail.setSalaryItemId(item.getSalaryItemId());
			detail.setAttendanceTypeId(item.getAttendanceTypeId());
			detail.setItemType(item.getItemType());
			detail.setItemName(item.getItemName());
			detail.setTaxYn(item.getTaxYn());
			detail.setTaxFreeName(item.getTaxFreeName());
			detail.setTaxFreeLimit(item.getTaxFreeLimit());
			detail.setCalcMethod(item.getCalcMethod());
			detail.setRoundUnit(item.getRoundUnit());
			detail.setDefaultAmount(item.getDefaultAmount());
			detail.setMemo(item.getRemark());
			detail.setAmount(0L);
			salaryDetailDao.insert(conn, detail);
		}
	}

	// 첫째 사원 상세 구조만 복사. 금액은 나중에 외부값으로 채움
	private void createDetailsFromTemplate(Connection conn, int salaryId, int templateSalaryId) throws SQLException {
		for (SalaryDetail template : salaryDetailDao.selectBySalaryId(conn, templateSalaryId)) {
			SalaryDetail detail = new SalaryDetail();
			detail.setSalaryId(salaryId);
			detail.setSalaryItemId(template.getSalaryItemId());
			detail.setAttendanceTypeId(template.getAttendanceTypeId());
			detail.setItemType(template.getItemType());
			detail.setItemName(template.getItemName());
			detail.setTaxYn(template.getTaxYn());
			detail.setTaxFreeName(template.getTaxFreeName());
			detail.setTaxFreeLimit(template.getTaxFreeLimit());
			detail.setCalcMethod(template.getCalcMethod());
			detail.setRoundUnit(template.getRoundUnit());
			detail.setDefaultAmount(template.getDefaultAmount());
			detail.setMemo(template.getMemo());
			detail.setAmount(0L);
			salaryDetailDao.insert(conn, detail);
		}
	}

	// 근태수당 → 기본급·고정지급, 공제 6종은 0 유지(자동계산 버튼에서 채움)
	private void applyExternalAmounts(Connection conn, int salaryId, int empId, SalaryDate salaryDate)
			throws SQLException {
		EmpInfoSalary empInfo = empInfoSalaryDao.selectById(conn, empId);
		Map<Integer, Long> attendanceSums = salaryAttendanceDao.selectAllowanceSumByType(
				conn, empId, salaryDate.getPayYear(), salaryDate.getPayMonth());
		if (attendanceSums == null) {
			attendanceSums = Collections.emptyMap();
		}

		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			long amount = resolveInitialAmount(detail, empInfo, attendanceSums);
			salaryDetailDao.updateAmountAndCalcMethod(conn, detail.getDetailId(), amount, detail.getCalcMethod());
		}
	}

	private long resolveInitialAmount(SalaryDetail detail, EmpInfoSalary empInfo,
			Map<Integer, Long> attendanceSums) {
		Integer attendanceTypeId = detail.getAttendanceTypeId();
		if (attendanceTypeId != null) {
			Long attendanceAmount = attendanceSums.get(attendanceTypeId);
			if (attendanceAmount != null && attendanceAmount > 0) {
				return attendanceAmount;
			}
		}

		String itemName = detail.getItemName();
		if ("기본급".equals(itemName) && empInfo != null && empInfo.getBaseSalary() > 0) {
			return empInfo.getBaseSalary();
		}
		if (SalaryAutoCalcRates.isAutoDeductionItem(itemName)) {
			return 0L;
		}
		if ("PAY".equals(detail.getItemType()) && detail.getDefaultAmount() != null
				&& detail.getDefaultAmount() > 0) {
			return detail.getDefaultAmount();
		}
		return 0L;
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
