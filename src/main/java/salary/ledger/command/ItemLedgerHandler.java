package salary.ledger.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.ItemLedgerService;
import salary.ledger.viewdto.ItemLedgerData;
import salary.util.PeriodRangeParams;

// 항목별 대장 조회 페이지
// 項目別台帳照会ページ
public class ItemLedgerHandler implements CommandHandler {

	private final ItemLedgerService itemLedgerService = new ItemLedgerService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PeriodRangeParams period = PeriodRangeParams.fromRequest(req);
		String itemName = req.getParameter("itemName");

		ItemLedgerData itemLedgerData = itemLedgerService.getItemLedgerData(period, itemName);
		req.setAttribute("itemLedgerData", itemLedgerData);
		return "/WEB-INF/view/salary/ledger/itemLedger.jsp";
	}
}
