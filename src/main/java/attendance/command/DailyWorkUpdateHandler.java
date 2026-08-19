package attendance.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/** 일용직 근무기록 수정 Handler / 日雇い勤務記録修正Handler */
public class DailyWorkUpdateHandler implements CommandHandler {

	private final DailyWorkService dailyWorkService = new DailyWorkService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/attendance/daily/record.do";
		}

		try {
			DailyWork dailyWork = new DailyWork();
			dailyWork.setDailyWorkId(parseInt(req.getParameter("dailyWorkId")));
			dailyWork.setEmpId(parseInt(req.getParameter("empId")));
			dailyWork.setWorkDate(parseDate(req.getParameter("workDate")));
			dailyWork.setProjectName(trim(req.getParameter("projectName")));
			dailyWork.setDailyWage(parseInt(req.getParameter("dailyWage")));
			dailyWork.setPaymentRate(parseDouble(req.getParameter("paymentRate")));

			if (dailyWorkService.getDailyWork(dailyWork.getDailyWorkId()) == null) {
				return "redirect:/attendance/daily/record.do?error=notFound";
			}

			boolean updated = dailyWorkService.modifyDailyWork(dailyWork);
			return "redirect:/attendance/daily/record.do?empId="
					+ dailyWork.getEmpId()
					+ (updated ? "&result=updated" : "&error=updateFailed");
		} catch (IllegalArgumentException e) {
			return "redirect:/attendance/daily/record.do?error=invalidInput";
		}
	}

	private int parseInt(String value) {
		if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException();
		try { return Integer.parseInt(value.trim().replace(",", "")); }
		catch (NumberFormatException e) { throw new IllegalArgumentException(e); }
	}

	private double parseDouble(String value) {
		if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException();
		try { return Double.parseDouble(value.trim()); }
		catch (NumberFormatException e) { throw new IllegalArgumentException(e); }
	}

	private Date parseDate(String value) {
		if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException();
		try { return Date.valueOf(value.trim()); }
		catch (IllegalArgumentException e) { throw new IllegalArgumentException(e); }
	}

	private String trim(String value) {
		return value == null ? "" : value.trim();
	}
}
