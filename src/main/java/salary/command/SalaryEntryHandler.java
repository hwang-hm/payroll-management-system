package salary.command;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.service.SalaryEntryData;
import salary.service.SalaryEntryService;

//급여입력/관리 페이지 진입 핸들러.
public class SalaryEntryHandler implements CommandHandler {

	private final SalaryEntryService entryService = new SalaryEntryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 년,월,급여차수를 JSP에서 가져옴, 없을시 디폴트 값으로 현재시간, 급여차수는 1을 세팅한다.
		int payYear = parseInt(req.getParameter("payYear"), Calendar.getInstance().get(Calendar.YEAR));
		int payMonth = parseInt(req.getParameter("payMonth"), Calendar.getInstance().get(Calendar.MONTH) + 1);
		int paySeq = parseInt(req.getParameter("paySeq"), 1);
		// 클릭한 salary행의 ID
		int salaryId = parseInt(req.getParameter("salaryId"), 0);

		SalaryEntryData entryData = entryService.getEntryData(payYear, payMonth, paySeq, salaryId);
		req.setAttribute("entryData", entryData);
		return "/view/salary/entry.jsp";
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
