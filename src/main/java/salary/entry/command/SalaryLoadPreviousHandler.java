package salary.entry.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryLoadPreviousService;
import salary.util.PayPeriodParams;
import salary.util.RequestParamParser;

// 지난 귀속년월 급여를 이번 달로 불러오기
public class SalaryLoadPreviousHandler implements CommandHandler {

	private final SalaryLoadPreviousService loadPreviousService = new SalaryLoadPreviousService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);
		int sourceYear = RequestParamParser.parseInt(req, "sourceYear", period.getPayYear());
		int sourceMonth = RequestParamParser.parseInt(req, "sourceMonth", period.getPayMonth());
		int sourceSeq = RequestParamParser.parseInt(req, "sourceSeq", 1);

		PayPeriodParams source = new PayPeriodParams(sourceYear, sourceMonth, sourceSeq);
		loadPreviousService.load(period, source);

		return "redirect:/salary/entry.do?" + period.toQueryString();
	}
}
