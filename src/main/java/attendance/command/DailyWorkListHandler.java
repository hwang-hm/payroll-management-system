package attendance.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.model.DailyWorkerInfo;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/**
 * 일용직 근무기록 관리 화면 요청을 처리하는 Handler
 * 日雇い勤務記録管理画面のリクエストを処理するHandler
 */
public class DailyWorkListHandler implements CommandHandler {

	private final DailyWorkService dailyWorkService = new DailyWorkService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		List<DailyWorkerInfo> dailyWorkers = dailyWorkService.getDailyWorkers();
		req.setAttribute("dailyWorkers", dailyWorkers);
		req.setAttribute("today", LocalDate.now().toString());

		Integer selectedEmpId = parseOptionalInt(req.getParameter("empId"));
		if (selectedEmpId != null) {
			req.setAttribute("selectedEmpId", selectedEmpId);
			req.setAttribute("employeeDailyWorks",
					dailyWorkService.getWorksByEmpId(selectedEmpId));
		}

		Integer dailyWorkId = parseOptionalInt(req.getParameter("dailyWorkId"));
		if (dailyWorkId != null) {
			DailyWork selectedDailyWork = dailyWorkService.getDailyWork(dailyWorkId);
			if (selectedDailyWork != null) {
				req.setAttribute("selectedDailyWork", selectedDailyWork);
				req.setAttribute("selectedEmpId", selectedDailyWork.getEmpId());
				req.setAttribute("employeeDailyWorks",
						dailyWorkService.getWorksByEmpId(selectedDailyWork.getEmpId()));
			}
		}

		return "/WEB-INF/view/attendance/dailyWorkRecord.jsp";
	}

	private Integer parseOptionalInt(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			int parsed = Integer.parseInt(value.trim());
			return parsed > 0 ? parsed : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
