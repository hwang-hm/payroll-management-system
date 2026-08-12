package salary.register.command;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.register.service.SalaryRegisterService;
import salary.register.viewdto.SalaryRegisterData;

// 급여대장 페이지 진입 핸들러.
public class SalaryRegisterHandler implements CommandHandler {

	private final SalaryRegisterService registerService = new SalaryRegisterService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int payYear = parseInt(req.getParameter("payYear"), Calendar.getInstance().get(Calendar.YEAR));

		SalaryRegisterData registerData = registerService.getRegisterData(payYear);
		req.setAttribute("registerData", registerData);
		return "/view/salary/register.jsp";
	}

	private int parseInt(String value, int defaultValue) {
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
