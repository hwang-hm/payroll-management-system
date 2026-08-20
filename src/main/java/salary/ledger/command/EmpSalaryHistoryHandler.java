package salary.ledger.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.EmpSalaryHistoryService;
import salary.ledger.viewdto.EmpSalaryHistoryData;
import salary.util.PeriodRangeParams;
import salary.util.RequestParamParser;

// 사원별 급여내역 조회 페이지
public class EmpSalaryHistoryHandler implements CommandHandler {

	private final EmpSalaryHistoryService empSalaryHistoryService = new EmpSalaryHistoryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PeriodRangeParams period = PeriodRangeParams.fromRequest(req);
		int empId = RequestParamParser.parseInt(req, "empId", 0);

		EmpSalaryHistoryData historyData = empSalaryHistoryService.getHistoryData(period.getStartYear(),
				period.getStartMonth(), period.getEndYear(), period.getEndMonth(), empId);
		req.setAttribute("historyData", historyData);
		return "/WEB-INF/view/salary/ledger/empSalaryHistory.jsp";
	}
}
