package salary.transfer.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.transfer.service.TransferResultService;
import salary.transfer.viewdto.TransferHistoryPage;
import salary.util.RequestParamParser;

// 급여이체 결과 조회 화면 핸들러.
public class TransferResultHandler implements CommandHandler {

	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private final TransferResultService resultService = new TransferResultService();

	// 조회기간이랑 페이지 받아서 결과화면 열어줌. 날짜 없으면 이번달로
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Date startDate = parseDate(req.getParameter("startDate"));
		Date endDate = parseDate(req.getParameter("endDate"));
		int pageNo = RequestParamParser.parseInt(req, "pageNo", 1);

		if (startDate == null || endDate == null) {
			Date[] defaults = TransferResultService.defaultPeriod();
			if (startDate == null) {
				startDate = defaults[0];
			}
			if (endDate == null) {
				endDate = defaults[1];
			}
		}

		TransferHistoryPage resultPage = resultService.getResultPage(startDate, endDate, pageNo);
		req.setAttribute("resultPage", resultPage);
		return "/WEB-INF/view/salary/transfer/result.jsp";
	}

	// yyyy-MM-dd 문자열을 날짜로 바꿈. 이상하면 null
	private Date parseDate(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
			format.setLenient(false);
			return format.parse(value.trim());
		} catch (ParseException e) {
			return null;
		}
	}
}
