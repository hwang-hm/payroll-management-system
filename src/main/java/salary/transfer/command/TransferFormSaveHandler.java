package salary.transfer.command;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.transfer.service.TransferApplyService;
import salary.util.PayPeriodParams;
import salary.util.RequestParamParser;

// 이체양식 저장 버튼 핸들러.
// 振込様式保存ボタンのHandler。
public class TransferFormSaveHandler implements CommandHandler {

	private final TransferApplyService applyService = new TransferApplyService();

	// 양식 저장하고 신청화면으로 돌아감
	// 様式を保存し申請画面へ戻る
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.sendRedirect(req.getContextPath() + "/salary/transfer/apply.do");
			return null;
		}

		Integer formId = RequestParamParser.parseNullableIntOrZero(req, "formId");
		String formName = req.getParameter("formName");
		String[] itemCodes = req.getParameterValues("itemCodes");

		int savedFormId = applyService.saveForm(formId, formName, itemCodes);
		PayPeriodParams period = PayPeriodParams.fromRequest(req);

		String message = URLEncoder.encode("様式を保存しました。", StandardCharsets.UTF_8.name());
		res.sendRedirect(req.getContextPath() + "/salary/transfer/apply.do?" + period.toQueryString()
				+ "&formId=" + savedFormId + "&message=" + message);
		return null;
	}
}
