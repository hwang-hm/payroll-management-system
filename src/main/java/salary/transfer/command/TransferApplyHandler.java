package salary.transfer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.transfer.service.TransferApplyService;
import salary.transfer.viewdto.TransferApplyData;
import salary.util.PayPeriodParams;
import salary.util.RequestParamParser;

// 급여이체 신청 화면 열어주는 핸들러.
// 給与振込申請画面を開くHandler。
public class TransferApplyHandler implements CommandHandler {

	private final TransferApplyService applyService = new TransferApplyService();

	// 년월/차수/양식 받아서 이체신청 화면 데이터 세팅
	// 年月/回数/様式を受け取り振込申請画面データを設定
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		PayPeriodParams period = PayPeriodParams.fromRequest(req);
		Integer formId = RequestParamParser.parseNullableInt(req, "formId");

		TransferApplyData applyData = applyService.getApplyData(period, formId);
		String message = req.getParameter("message");
		if (message != null && !message.trim().isEmpty()) {
			applyData.setMessage(message);
		}
		req.setAttribute("applyData", applyData);
		return "/WEB-INF/view/salary/transfer/apply.jsp";
	}
}
