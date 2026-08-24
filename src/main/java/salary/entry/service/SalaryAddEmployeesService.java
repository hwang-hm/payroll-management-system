package salary.entry.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import salary.util.SalaryTotals;

public class SalaryAddEmployeesService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDateDao salaryDateDao = new SalaryDateDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private final SalaryItemDao salaryItemDao = new SalaryItemDao();
	private final SalaryAttendanceDao salaryAttendanceDao = new SalaryAttendanceDao();
	private final EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	// 선택한 사원들을 이번 급여에 추가. 이미 들어가 있으면 건너뜀.
	// 選択した社員を今回の給与に追加。既に含まれていればスキップ。
	// 마지막으로 추가된 salaryId 반환 (없으면 null)
	// 最後に追加された salaryId を返却 (なければ null)
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

			Set<Integer> existingEmpIds = salaryDao.selectEmpIdsBySalaryDateId(conn, salaryDateId);
			List<Integer> toAdd = new ArrayList<>();
			for (Integer empId : empIds) {
				if (empId != null && !existingEmpIds.contains(empId)) {
					toAdd.add(empId);
				}
			}
			if (toAdd.isEmpty()) {
				conn.commit();
				return null;
			}

			Integer templateSalaryId = salaryDao.selectFirstSalaryIdBySalaryDateId(conn, salaryDateId);
			List<SalaryDetail> structureTemplates = loadStructureTemplates(conn, templateSalaryId);

			Map<Integer, EmpInfoSalary> empInfoMap = empInfoSalaryDao.selectByEmpIds(conn, toAdd);
			Map<Integer, Map<Integer, Long>> attendanceByEmp = salaryAttendanceDao.selectAllowanceSumByEmpIds(
					conn, toAdd, salaryDate.getPayYear(), salaryDate.getPayMonth());

			Integer lastAddedSalaryId = null;
			for (Integer empId : toAdd) {
				EmpInfoSalary empInfo = empInfoMap.get(empId);
				Map<Integer, Long> attendanceSums = attendanceByEmp.getOrDefault(empId, Collections.emptyMap());
				List<SalaryDetail> details = buildDetailsWithAmounts(structureTemplates, empInfo, attendanceSums);
				long[] totals = SalaryTotals.fromDetails(details);

				Salary salary = new Salary();
				salary.setSalaryDateId(salaryDateId);
				salary.setEmpId(empId);
				salary.setTotalPay(totals[0]);
				salary.setTotalDeduct(totals[1]);
				salary.setNetPay(totals[2]);

				int salaryId = salaryDao.insert(conn, salary);
				for (SalaryDetail detail : details) {
					detail.setSalaryId(salaryId);
				}
				salaryDetailDao.insertBatch(conn, details);
				lastAddedSalaryId = salaryId;
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

	private List<SalaryDetail> loadStructureTemplates(Connection conn, Integer templateSalaryId)
			throws SQLException {
		if (templateSalaryId != null) {
			return salaryDetailDao.selectBySalaryId(conn, templateSalaryId);
		}
		List<SalaryDetail> templates = new ArrayList<>();
		for (SalaryItem item : salaryItemDao.selectActiveItems(conn)) {
			SalaryDetail detail = new SalaryDetail();
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
			templates.add(detail);
		}
		return templates;
	}

	private List<SalaryDetail> buildDetailsWithAmounts(List<SalaryDetail> templates, EmpInfoSalary empInfo,
			Map<Integer, Long> attendanceSums) {
		List<SalaryDetail> details = new ArrayList<>(templates.size());
		for (SalaryDetail template : templates) {
			SalaryDetail detail = new SalaryDetail();
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
			detail.setAmount(resolveInitialAmount(detail, empInfo, attendanceSums));
			details.add(detail);
		}
		return details;
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
		if (SalaryAutoCalcRates.isBasicPayItem(itemName) && empInfo != null && empInfo.getBaseSalary() > 0) {
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
}
