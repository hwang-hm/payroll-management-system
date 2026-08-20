package salary.ledger.command;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.SalaryLedgerService;
import salary.ledger.viewdto.SalaryLedgerData;
import salary.util.RequestParamParser;

// 급여대장 조회 페이지 진입 핸들러.
public class SalaryLedgerHandler implements CommandHandler {

	private final SalaryLedgerService ledgerService = new SalaryLedgerService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int payYear = RequestParamParser.parseInt(req, "payYear", Calendar.getInstance().get(Calendar.YEAR));

		SalaryLedgerData ledgerData = ledgerService.getLedgerData(payYear);
		req.setAttribute("ledgerData", ledgerData);
		return "/WEB-INF/view/salary/ledger/ledger.jsp";
	}
}
