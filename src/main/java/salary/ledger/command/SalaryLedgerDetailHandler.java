package salary.ledger.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.SalaryLedgerDetailService;
import salary.ledger.viewdto.SalaryLedgerDetailData;
import salary.util.RequestParamParser;

// 급여대장 상세(사원별 고정항목) 페이지 진입 핸들러.
// 給与台帳詳細(社員別固定項目)ページ進入Handler。
public class SalaryLedgerDetailHandler implements CommandHandler {

	private final SalaryLedgerDetailService detailService = new SalaryLedgerDetailService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int salaryDateId = RequestParamParser.parseInt(req, "salaryDateId", 0);
		SalaryLedgerDetailData detailData = detailService.getDetailData(salaryDateId);
		req.setAttribute("detailData", detailData);
		return "/WEB-INF/view/salary/ledger/ledgerDetail.jsp";
	}
}
