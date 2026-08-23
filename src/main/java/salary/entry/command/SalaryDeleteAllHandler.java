package salary.entry.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryDeleteService;
import salary.util.PayPeriodParams;

// 급여입력에서 이번 귀속년월 급여 전체 삭제
public class SalaryDeleteAllHandler implements CommandHandler {

	private final SalaryDeleteService deleteService = new SalaryDeleteService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);
		deleteService.deleteAll(period);
		return "redirect:/salary/entry.do?" + period.toQueryString();
	}
}
