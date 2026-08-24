package retirement.command;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.dao.EmpDao;
import employee.dao.EmpInfoSalaryDao;
import employee.dto.EmployeeDetailData;
import employee.model.EmpInfoSalary;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import retirement.severancepay.service.SeveranceCalculateService;
import retirement.severancepay.service.SeveranceSalaryResult;

public class SeverancePayHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/retirement/severancePay.jsp";

	private EmpDao empDao = new EmpDao();
	private SeveranceCalculateService calculateService = new SeveranceCalculateService();
	private EmpInfoSalaryDao empInfoSalaryDao = new EmpInfoSalaryDao();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		}

		if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		}

		return FORM_VIEW;
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			// 사원선택 모달 목록
			// 社員選択モーダル一覧
			List<EmployeeDetailData> employeeList = empDao.selectAllJoin(conn);
			req.setAttribute("employeeList", employeeList);
			String empIdValue = req.getParameter("empId");

			if (empIdValue == null || empIdValue.trim().isEmpty()) {
				return FORM_VIEW;
			}

			int empId = Integer.parseInt(empIdValue);

			EmployeeDetailData selectedEmp = empDao.selectByIdJoin(conn, empId);

			if (selectedEmp == null) {

				req.setAttribute("error", "사원 정보를 찾을 수 없습니다.");
				return FORM_VIEW;
			}

			// 급여 계좌정보 조회
			// 給与口座情報の照会
			EmpInfoSalary empInfoSalary = empInfoSalaryDao.selectBankByEmpId(conn, empId);
			req.setAttribute("empInfoSalary", empInfoSalary);

			// 선택사원 관련 정보 세팅
			// 選択社員関連情報の設定
			setEmployeeData(req, selectedEmp);
			return FORM_VIEW;

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			JdbcUtil.close(conn);
		}
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {

		Connection conn = null;
		try {

			conn = ConnectionProvider.getConnection();

			// 1. 사원번호
			// 1. 社員番号
			String empIdValue = req.getParameter("empId");

			if (empIdValue == null || empIdValue.trim().isEmpty()) {
				req.setAttribute("salaryLoadError", "사원을 선택해주세요.");
				return FORM_VIEW;
			}

			int empId = Integer.parseInt(empIdValue);

			// 2. 사원목록
			// 2. 社員一覧
			List<EmployeeDetailData> employeeList = empDao.selectAllJoin(conn);
			req.setAttribute("employeeList", employeeList);

			// 3. 선택한 사원
			// 3. 選択した社員
			EmployeeDetailData selectedEmp = empDao.selectByIdJoin(conn, empId);

			if (selectedEmp == null) {
				req.setAttribute("salaryLoadError", "사원 정보를 찾을 수 없습니다.");
				return FORM_VIEW;
			}

			// 4. 사원 상단 정보
			// 4. 社員上部情報
			setEmployeeData(req, selectedEmp);

			// 5. 급여내역 계산
			// 5. 給与明細の計算
			SeveranceSalaryResult salaryResult = calculateService.loadSalary(empId);
			req.setAttribute("salaryResult", salaryResult);

			EmpInfoSalary empInfoSalary = empInfoSalaryDao.selectBankByEmpId(conn, empId);
			req.setAttribute("empInfoSalary", empInfoSalary);
			
			return FORM_VIEW;

		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {

			JdbcUtil.close(conn);
		}
	}

	private void setEmployeeData(HttpServletRequest req, EmployeeDetailData selectedEmp) {

		Date calculationDate;

		if (selectedEmp.getRetireDate() != null) {
			calculationDate = selectedEmp.getRetireDate();

		} else {
			calculationDate = new Date(System.currentTimeMillis());
		}

		LocalDate hireDate = selectedEmp.getHireDate().toLocalDate();

		long serviceDays = ChronoUnit.DAYS.between(hireDate, calculationDate.toLocalDate()) + 1;
		int serviceYears = (int) Math.ceil(serviceDays / 365.0);

		if (serviceYears < 1) {
			serviceYears = 1;
		}

		req.setAttribute("selectedEmp", selectedEmp);
		req.setAttribute("serviceDays", serviceDays);
		req.setAttribute("serviceYears", serviceYears);
	}
}