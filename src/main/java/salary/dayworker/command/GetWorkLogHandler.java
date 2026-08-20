package salary.dayworker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

public class GetWorkLogHandler implements CommandHandler {
	private DailyWorkService dailyWorkService = new DailyWorkService();
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/components/_workLogList.jsp";

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		try {
			String empIdStr = req.getParameter("empId");
			String yearStr = req.getParameter("payYear");
			String monthStr = req.getParameter("payMonth");

			// 파라미터가 비어있으면 빈 폼 반환
			if (empIdStr == null || yearStr == null || monthStr == null) {
				return FORM_VIEW;
			}

			int empId = Integer.parseInt(empIdStr);
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);

			// 1. 해당 사원의 해당 연월 근무 내역 조회
			List<DailyWork> workLogList = dailyWorkService.getMonthlyWorks(empId, year, month);

			// 2. request 영역에 근무내역 전달
			req.setAttribute("workLogList", workLogList);
			req.setAttribute("workDaysCount", workLogList != null ? workLogList.size() : 0);

		} catch (NumberFormatException e) {
			System.err.println("[GetWorkLogHandler] 파라미터 변환 오류: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return FORM_VIEW;
	}

}
