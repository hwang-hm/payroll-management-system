package salary.entry.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryAddEmployeesService;
import salary.util.PayPeriodParams;

// 사원검색 모달에서 고른 사람들을 이번 급여에 추가하는 핸들러.
// 社員検索モーダルで選んだ人を今回の給与に追加するHandler。
public class SalaryAddEmployeesHandler implements CommandHandler {

	private final SalaryAddEmployeesService addEmployeesService = new SalaryAddEmployeesService();

	// 고른 사원 추가하고 급여입력 화면으로 다시 감
	// 選んだ社員を追加し、給与入力画面へ戻る
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		int salaryDateId = Integer.parseInt(req.getParameter("salaryDateId"));
		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);

		String[] empIdArr = req.getParameterValues("empIds");
		List<Integer> empIds = new ArrayList<>();
		if (empIdArr != null) {
			for (String empId : empIdArr) {
				empIds.add(Integer.parseInt(empId));
			}
		}

		Integer addedSalaryId = addEmployeesService.add(salaryDateId, empIds);

		String redirect = "redirect:/salary/entry.do?" + period.toQueryString();
		if (addedSalaryId != null) {
			redirect += "&salaryId=" + addedSalaryId;
		}
		return redirect;
	}
}
