package salary.ledger.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.PayStubService;
import salary.ledger.viewdto.PayStubData;
import salary.util.PayPeriodParams;
import salary.util.RequestParamParser;

// 급여명세서 조회 페이지 진입 핸들러.
// 給与明細書照会ページ進入Handler。
public class PayStubHandler implements CommandHandler {

	private final PayStubService payStubService = new PayStubService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PayPeriodParams period = PayPeriodParams.fromRequest(req);
		int salaryId = RequestParamParser.parseInt(req, "salaryId", 0);
		String searchKeyword = req.getParameter("searchKeyword");

		PayStubData payStubData = payStubService.getPayStubData(period, salaryId, searchKeyword);
		req.setAttribute("payStubData", payStubData);
		return "/WEB-INF/view/salary/ledger/payStub.jsp";
	}
}
