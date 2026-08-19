package salary.transfer.command;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.transfer.service.TransferApplyService;
import salary.transfer.service.TransferExecuteService;

// 급여이체하기 버튼 누르면 실제로 이체 돌리는 핸들러.
public class TransferExecuteHandler implements CommandHandler {

	private final TransferExecuteService executeService = new TransferExecuteService();

	// 이체 실행하고 결과 메시지 붙여서 신청화면으로 다시 보냄
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.sendRedirect(req.getContextPath() + "/salary/transfer/apply.do");
			return null;
		}

		int payYear = parseInt(req.getParameter("payYear"), TransferApplyService.currentYear());
		int payMonth = parseInt(req.getParameter("payMonth"), TransferApplyService.currentMonth());
		int paySeq = parseInt(req.getParameter("paySeq"), 1);
		Integer formId = parseNullableInt(req.getParameter("formId"));

		String resultMessage = executeService.execute(payYear, payMonth, paySeq);
		String message = URLEncoder.encode(resultMessage, StandardCharsets.UTF_8.name());

		StringBuilder redirect = new StringBuilder();
		redirect.append(req.getContextPath()).append("/salary/transfer/apply.do?");
		redirect.append("payYear=").append(payYear);
		redirect.append("&payMonth=").append(payMonth);
		redirect.append("&paySeq=").append(paySeq);
		if (formId != null) {
			redirect.append("&formId=").append(formId);
		}
		redirect.append("&message=").append(message);
		res.sendRedirect(redirect.toString());
		return null;
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
