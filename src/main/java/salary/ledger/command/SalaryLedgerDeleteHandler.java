package salary.ledger.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryDeleteService;
import salary.util.RequestParamParser;

// 급여대장에서 특정 귀속년월·차수 급여 전체 삭제
// 給与台帳で特定帰属年月・回数の給与を全削除
public class SalaryLedgerDeleteHandler implements CommandHandler {

	private final SalaryDeleteService deleteService = new SalaryDeleteService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		int salaryDateId = RequestParamParser.parseInt(req, "salaryDateId", 0);
		int payYear = RequestParamParser.parseInt(req, "payYear", 0);
		if (salaryDateId > 0) {
			deleteService.deleteAllBySalaryDateId(salaryDateId);
		}

		String redirect = "redirect:/salary/ledger.do";
		if (payYear > 0) {
			redirect += "?payYear=" + payYear;
		}
		return redirect;
	}
}
