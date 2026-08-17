package attendance.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/** 일용직 근무기록 등록 Handler / 日雇い勤務記録登録Handler */
public class DailyWorkRegisterHandler implements CommandHandler {

	private final DailyWorkService dailyWorkService = new DailyWorkService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/attendance/daily/record.do";
		}

		try {
			DailyWork dailyWork = createDailyWork(req);
			boolean registered = dailyWorkService.addDailyWork(dailyWork);
			return "redirect:/attendance/daily/record.do?empId="
					+ dailyWork.getEmpId()
					+ (registered ? "&result=registered" : "&error=registerFailed");
		} catch (IllegalArgumentException e) {
			return "redirect:/attendance/daily/record.do?error=invalidInput";
		}
	}

	private DailyWork createDailyWork(HttpServletRequest req) {
		DailyWork dailyWork = new DailyWork();
		dailyWork.setEmpId(parseInt(req.getParameter("empId")));
		dailyWork.setWorkDate(parseDate(req.getParameter("workDate")));
		dailyWork.setProjectName(trim(req.getParameter("projectName")));
		dailyWork.setDailyWage(parseInt(req.getParameter("dailyWage")));
		dailyWork.setPaymentRate(parseDouble(req.getParameter("paymentRate")));
		return dailyWork;
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
