package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetEmpMonthlySalaryService;
import salary.statics.service.request.EmpMonthlySalaryDTO;
import salary.statics.service.request.MonthlySalaryDTO;

public class GetEmpMonthlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/empMonthlyStatics.jsp";
	private GetEmpMonthlySalaryService service = new GetEmpMonthlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 일반 페이지 요청 처리
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		Integer empId = parseInt(req.getParameter("empId"));
		String empName = req.getParameter("empName");

		// 처음 들어왔을 때 사원이 지정되지 않은 경우 첫 번째 사원 자동 지정
		if (empId == null) {
			empId = 1;
		}

		List<EmpMonthlySalaryDTO> statList = null;

		if (empId != null) {
			statList = service.getMonthlyPersonalStats(empId, targetYear);
		}

		// 합계 계산
		long paySum = 0L;
		long deductSum = 0L;
		long netSum = 0L;

		for (EmpMonthlySalaryDTO dto : statList) {
			paySum += dto.getTotalPayThousand();
			deductSum += dto.getTotalDeductThousand();
			netSum += dto.getNetPayThousand();
		}

		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("selectedEmpId", empId);
		req.setAttribute("selectedEmpName", empName);
		req.setAttribute("statList", statList);
		req.setAttribute("paySum", paySum);
		req.setAttribute("deductSum", deductSum);
		req.setAttribute("netSum", netSum);

		return FORM_VIEW;
	}

	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			return Integer.parseInt(val.replaceAll("[^0-9-]", ""));
		} catch (Exception e) {
			return null;
		}
	}

}
