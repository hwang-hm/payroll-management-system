package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetAllYearlySalaryService;
import salary.statics.service.request.YearlySalaryDto;

public class GetAllYearlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/yearlyStaticsAll.jsp";

	private GetAllYearlySalaryService yearlySalaryService = new GetAllYearlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	// 화면 이동 및 연도별 통계 데이터 조회
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		// 선택된 귀속년도 파라미터 수신 (없으면 현재 연도 기준)
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		// 10개년 보정 통계 데이터 조회
		List<YearlySalaryDto> statList = yearlySalaryService.get10YearSalaryStats(targetYear);

		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("statList", statList);

		return FORM_VIEW;
	}

	/**
	 * String -> Integer 안전 변환 null, 빈값(""), 공백(" "), 숫자가 아닌 문자 포함 시 null 반환
	 */
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
