package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetAllMonthlySalaryService;
import salary.statics.service.request.MonthlySalaryDTO;

public class GetAllMonthlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/monthlyStaticsAll.jsp";

	private GetAllMonthlySalaryService monthlySalaryService = new GetAllMonthlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET") || req.getMethod().equalsIgnoreCase("POST")) {
			return processForm(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear(); // 기본값: 현재 연도
		}

		// 12개월 통계 조회
		List<MonthlySalaryDTO> statList = monthlySalaryService.get12MonthSalaryStats(targetYear);

		// 합계 및 연평균 인원 계산
		long totalSalarySum = 0;
		double totalEmpSum = 0;
		int validMonthCount = 0;

		for (MonthlySalaryDTO dto : statList) {
			totalSalarySum += dto.getTotalSalaryThousand();
			if (dto.getEmpCount() > 0) {
				totalEmpSum += dto.getEmpCount();
				validMonthCount++;
			}
		}

		double avgEmpCount = validMonthCount > 0 ? (totalEmpSum / validMonthCount) : 0.0;
		avgEmpCount = Math.round(avgEmpCount * 10.0) / 10.0;

		// setAttribute 저장
		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("statList", statList);
		req.setAttribute("totalSalarySum", totalSalarySum);
		req.setAttribute("avgEmpCount", avgEmpCount);

		return FORM_VIEW;
	}

	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;
			return Integer.parseInt(cleanVal);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
