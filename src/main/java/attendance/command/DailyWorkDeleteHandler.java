package attendance.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/** 일용직 근무기록 삭제 Handler / 日雇い勤務記録削除Handler */
public class DailyWorkDeleteHandler implements CommandHandler {

	private final DailyWorkService dailyWorkService = new DailyWorkService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/attendance/daily/record.do";
		}

		try {
			int dailyWorkId = Integer.parseInt(req.getParameter("dailyWorkId"));
			DailyWork saved = dailyWorkService.getDailyWork(dailyWorkId);
			if (saved == null) {
				return "redirect:/attendance/daily/record.do?error=notFound";
			}
			boolean deleted = dailyWorkService.removeDailyWork(dailyWorkId);
			return "redirect:/attendance/daily/record.do?empId=" + saved.getEmpId()
					+ (deleted ? "&result=deleted" : "&error=deleteFailed");
		} catch (NumberFormatException e) {
			return "redirect:/attendance/daily/record.do?error=invalidInput";
		} catch (RuntimeException e) {
			return "redirect:/attendance/daily/record.do?error=deleteFailed";
		}
	}
}
