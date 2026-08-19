package salary.ledger.command;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.PayStubService;
import salary.ledger.viewdto.PayStubData;

// 급여명세서 조회 페이지 진입 핸들러.
public class PayStubHandler implements CommandHandler {

	private final PayStubService payStubService = new PayStubService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Calendar now = Calendar.getInstance();
		int payYear = parseInt(req.getParameter("payYear"), now.get(Calendar.YEAR));
		int payMonth = parseInt(req.getParameter("payMonth"), now.get(Calendar.MONTH) + 1);
		int paySeq = parseInt(req.getParameter("paySeq"), 1);
		int salaryId = parseInt(req.getParameter("salaryId"), 0);
		String searchKeyword = req.getParameter("searchKeyword");

		PayStubData payStubData = payStubService.getPayStubData(payYear, payMonth, paySeq, salaryId, searchKeyword);
		req.setAttribute("payStubData", payStubData);
		return "/WEB-INF/view/salary/ledger/payStub.jsp";
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
