package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetEmpYearlySalaryService;
import salary.statics.service.request.EmpYearlySalaryDTO;

public class GetEmpYearlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/empYearlyStatics.jsp";
	private GetEmpYearlySalaryService service = new GetEmpYearlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 일반 페이지 요청 처리
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		Integer empId = parseInt(req.getParameter("empId"));
		String empName = req.getParameter("empName");

		// 처음 들어왔을 때 사원이 지정되지 않은 경우 첫 번째 사원 자동 지정
		if (empId == null) {
			empId = 1;
		}

		List<EmpYearlySalaryDTO> statList = null;

		if (empId != null) {
			statList = service.get10YearPersonalStats(empId, targetYear);
		}

		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("selectedEmpId", empId);
		req.setAttribute("selectedEmpName", empName);
		req.setAttribute("statList", statList);

		return FORM_VIEW;
	}

	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			return Integer.parseInt(val.replaceAll("[^0-9-]", ""));
		} catch (Exception e) {
			return null;
		}
	}

}
