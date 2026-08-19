package salary.statics.command;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.SalaryCompositionService;
import salary.statics.service.request.SalaryCompositionDTO;

public class SalaryCompositionHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/salaryCompositionStatics.jsp";

	// Service 계층 의존성 주입
	private SalaryCompositionService service = new SalaryCompositionService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LocalDate lastMonth = LocalDate.now().minusMonths(1); // 현재 날짜 기준 전달(1달 전)

		String yearParam = req.getParameter("year");
		String monthParam = req.getParameter("month");
		String empIdParam = req.getParameter("empId");
		String empNameParam = req.getParameter("empName");

		int selectedYear = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : lastMonth.getYear();
		int selectedMonth = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam)
				: lastMonth.getMonthValue();
		int empId = (empIdParam != null && !empIdParam.trim().isEmpty()) ? Integer.parseInt(empIdParam) : 1;

		req.setAttribute("selectedYear", selectedYear);
		req.setAttribute("selectedMonth", selectedMonth);
		req.setAttribute("selectedEmpId", empId);
		req.setAttribute("selectedEmpName", empNameParam);

		// Service 호출
		SalaryCompositionDTO statData = service.getSalaryComposition(empId, selectedYear, selectedMonth);
		req.setAttribute("statData", statData);

		return FORM_VIEW;
	}

}
