package salary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*import employee.dao.DepartmentDao;
import employee.dao.EmployeeDao;*/
import employee.model.Employee;
import jdbc.connection.ConnectionProvider;
import salary.dao.SalaryDao;
import salary.dao.SalaryDetailDao;
import salary.model.Salary;
import salary.model.SalaryDetail;

public class SalaryEntryService {

	private final SalaryDao salaryDao = new SalaryDao();
	private final SalaryDetailDao salaryDetailDao = new SalaryDetailDao();

	/*
	 * private final EmployeeDao employeeDao = new EmployeeDao(); private final
	 * DepartmentDao departmentDao = new DepartmentDao();
	 */

	// 급여입력/관리 페이지의 전반적인 View용 데이터를 가져온다
	public SalaryEntryData getEntryData(int payYear, int payMonth, int paySeq, long selectedSalaryId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Salary> salaries = salaryDao.selectListByPeriod(conn, payYear, payMonth, paySeq);
			List<SalaryView> salaryList = toSalaryViews(conn, salaries);

			// salary항목을 클릭하지 않았을때 강제로 1번 픽
			if (selectedSalaryId == 0 && !salaryList.isEmpty()) {
				selectedSalaryId = salaryList.get(0).getSalaryId();
			}

			SalaryEntryData data = new SalaryEntryData();
			data.setPayYear(payYear);
			data.setPayMonth(payMonth);
			data.setPaySeq(paySeq);
			data.setSalaryList(salaryList);
			data.setSummary(toSummary(salaries));
			data.setSelectedSalaryId(selectedSalaryId);
			setPeriodDates(data, salaries.isEmpty() ? null : salaries.get(0), payYear, payMonth);

			if (selectedSalaryId > 0) {
				splitDetails(conn, selectedSalaryId, data);
				data.setSelectedSalary(findSalaryView(salaryList, selectedSalaryId));
			}

			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// employee와 department 정보가 필요해서 일단 주석처리. 차후 작업 올라오면 수정 예정.
	// 급여입력/관리의 좌측 표를 만들기 위한 데이터
	private List<SalaryView> toSalaryViews(Connection conn, List<Salary> salaries) throws SQLException {
		List<SalaryView> views = new ArrayList<>();
		for (Salary salary : salaries) {
			// Employee employee = employeeDao.selectById(conn, salary.getEmpId());
			Employee employee = null;
			String deptName = "-";
			/*
			 * if (employee != null && employee.getDeptId() != null) { Department department
			 * = departmentDao.selectById(conn, employee.getDeptId()); if (department !=
			 * null) { deptName = department.getDeptName(); } }
			 */
			SalaryView view = new SalaryView();
			view.setSalaryId(salary.getSalaryId());
			view.setEmpId(salary.getEmpId());
			view.setEmploymentType(employee != null ? employee.getEmploymentType() : null);
			view.setNameKor(employee != null ? employee.getNameKor() : null);
			view.setDeptName(deptName);
			view.setTotalPay(salary.getTotalPay());
			view.setTotalDeduct(salary.getTotalDeduct());
			view.setNetPay(salary.getNetPay());
			views.add(view);
		}
		return views;
	}

	// 아래쪽 급여합계 정보 view용 데이터
	private SalarySummary toSummary(List<Salary> salaries) {
		SalarySummary summary = new SalarySummary();
		summary.setCount(salaries.size());
		long totalPay = 0;
		long totalDeduct = 0;
		long netPay = 0;
		for (Salary salary : salaries) {
			totalPay += salary.getTotalPay() != null ? salary.getTotalPay() : 0;
			totalDeduct += salary.getTotalDeduct() != null ? salary.getTotalDeduct() : 0;
			netPay += salary.getNetPay() != null ? salary.getNetPay() : 0;
		}
		summary.setTotalPay(totalPay);
		summary.setTotalDeduct(totalDeduct);
		summary.setNetPay(netPay);
		return summary;
	}

	// 급여상세 지급항목 공제항목 분기.
	private void splitDetails(Connection conn, long salaryId, SalaryEntryData data) throws SQLException {
		List<SalaryDetail> payDetails = new ArrayList<>();
		List<SalaryDetail> deductDetails = new ArrayList<>();
		for (SalaryDetail detail : salaryDetailDao.selectBySalaryId(conn, salaryId)) {
			if ("PAY".equals(detail.getItemType())) {
				payDetails.add(detail);
			} else {
				deductDetails.add(detail);
			}
		}
		data.setPayDetails(payDetails);
		data.setDeductDetails(deductDetails);
	}

	//
	private SalaryView findSalaryView(List<SalaryView> salaryList, long salaryId) {
		for (SalaryView view : salaryList) {
			if (view.getSalaryId() == salaryId) {
				return view;
			}
		}
		return null;
	}

	// 페이지 위쪽의 급여 기간과 지급일 세팅
	private void setPeriodDates(SalaryEntryData data, Salary periodInfo, int payYear, int payMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (periodInfo != null) {
			data.setStartDate(sdf.format(periodInfo.getStartDate()));
			data.setEndDate(sdf.format(periodInfo.getEndDate()));
			data.setPayDate(sdf.format(periodInfo.getPayDate()));
			return;
		}

		int nextMonth = payMonth == 12 ? 1 : payMonth + 1;
		int nextYear = payMonth == 12 ? payYear + 1 : payYear;
		Calendar cal = Calendar.getInstance();
		cal.set(payYear, payMonth - 1, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		data.setStartDate(String.format("%04d-%02d-01", payYear, payMonth));
		data.setEndDate(String.format("%04d-%02d-%02d", payYear, payMonth, lastDay));
		data.setPayDate(String.format("%04d-%02d-05", nextYear, nextMonth));
	}
}
