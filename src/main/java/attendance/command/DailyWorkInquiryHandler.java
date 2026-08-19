package attendance.command;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.model.DailyWorkerInfo;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/**
 * 일용직 근무조회 화면 요청을 처리하는 Handler
 * 日雇い勤務照会画面のリクエストを処理するHandler
 */
public class DailyWorkInquiryHandler implements CommandHandler {

	// 일용직 근무 Service / 日雇い勤務Service
	private final DailyWorkService dailyWorkService =
			new DailyWorkService();

	/**
	 * 사원과 연월을 기준으로 일용직 근무내역을 조회한다.
	 * 社員と年月を基準に日雇い勤務内訳を照会する。
	 */
	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		List<DailyWorkerInfo> dailyWorkers =
				dailyWorkService.getDailyWorkers();

		req.setAttribute("dailyWorkers", dailyWorkers);

		LocalDate today = LocalDate.now();
		int selectedYear = parseInt(
				req.getParameter("year"), today.getYear());
		int selectedMonth = parseMonth(
				req.getParameter("month"), today.getMonthValue());
		Integer selectedEmpId = parseOptionalId(
				req.getParameter("empId"));

		List<DailyWork> monthlyWorks = Collections.emptyList();

		if (selectedEmpId != null) {
			monthlyWorks = dailyWorkService.getMonthlyWorks(
					selectedEmpId, selectedYear, selectedMonth);
		}

		req.setAttribute("selectedEmpId", selectedEmpId);
		req.setAttribute("selectedYear", selectedYear);
		req.setAttribute("selectedMonth", selectedMonth);
		req.setAttribute("monthlyWorks", monthlyWorks);

		return "/WEB-INF/view/attendance/dailyWorkList.jsp";
	}

	private int parseInt(String value, int defaultValue) {
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			int parsedValue = Integer.parseInt(value.trim());
			return parsedValue > 0 ? parsedValue : defaultValue;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private int parseMonth(String value, int defaultValue) {
		int month = parseInt(value, defaultValue);
		return month >= 1 && month <= 12 ? month : defaultValue;
	}

	private Integer parseOptionalId(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			int parsedValue = Integer.parseInt(value.trim());
			return parsedValue > 0 ? parsedValue : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
