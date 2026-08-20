package salary.entry.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.viewdto.SalaryEntryData;
import salary.entry.service.SalaryEntryService;
import salary.util.PayPeriodParams;
import salary.util.RequestParamParser;

//급여입력/관리 페이지 진입 핸들러.
public class SalaryEntryHandler implements CommandHandler {

	private final SalaryEntryService entryService = new SalaryEntryService();

	// 화면에서 고른 년월/차수/사원으로 급여입력 화면 열어줌
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PayPeriodParams period = PayPeriodParams.fromRequest(req);
		int salaryId = RequestParamParser.parseInt(req, "salaryId", 0);

		SalaryEntryData entryData = entryService.getEntryData(period, salaryId);
		req.setAttribute("entryData", entryData);
		return "/WEB-INF/view/salary/entry/entry.jsp";
	}
}
