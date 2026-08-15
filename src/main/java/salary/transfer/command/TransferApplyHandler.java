package salary.transfer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.transfer.service.TransferApplyService;
import salary.transfer.viewdto.TransferApplyData;

public class TransferApplyHandler implements CommandHandler {

	private final TransferApplyService applyService = new TransferApplyService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int payYear = parseInt(req.getParameter("payYear"), TransferApplyService.currentYear());
		int payMonth = parseInt(req.getParameter("payMonth"), TransferApplyService.currentMonth());
		int paySeq = parseInt(req.getParameter("paySeq"), 1);
		Integer formId = parseNullableInt(req.getParameter("formId"));

		TransferApplyData applyData = applyService.getApplyData(payYear, payMonth, paySeq, formId);
		String message = req.getParameter("message");
		if (message != null && !message.trim().isEmpty()) {
			applyData.setMessage(message);
		}
		req.setAttribute("applyData", applyData);
		return "/WEB-INF/view/salary/transfer/apply.jsp";
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

	private Integer parseNullableInt(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
