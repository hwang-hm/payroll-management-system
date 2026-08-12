package salary.ledger.command;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.SalaryLedgerService;
import salary.ledger.viewdto.SalaryLedgerData;

// 급여대장 조회 페이지 진입 핸들러.
public class SalaryLedgerHandler implements CommandHandler {

	private final SalaryLedgerService ledgerService = new SalaryLedgerService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int payYear = parseInt(req.getParameter("payYear"), Calendar.getInstance().get(Calendar.YEAR));

		SalaryLedgerData ledgerData = ledgerService.getLedgerData(payYear);
		req.setAttribute("ledgerData", ledgerData);
		return "/WEB-INF/view/salary/ledger/ledger.jsp";
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
