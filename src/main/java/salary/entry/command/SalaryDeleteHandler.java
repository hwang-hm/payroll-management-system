package salary.entry.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryDeleteService;
import salary.util.PayPeriodParams;

// 급여입력에서 선택한 사원 급여 삭제
// 給与入力で選択した社員の給与を削除
public class SalaryDeleteHandler implements CommandHandler {

	private final SalaryDeleteService deleteService = new SalaryDeleteService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);
		String[] salaryIdArr = req.getParameterValues("salaryIds");
		List<Integer> salaryIds = new ArrayList<>();
		if (salaryIdArr != null) {
			for (String salaryId : salaryIdArr) {
				salaryIds.add(Integer.parseInt(salaryId));
			}
		}

		deleteService.deleteSelected(period, salaryIds);
		return "redirect:/salary/entry.do?" + period.toQueryString();
	}
}
