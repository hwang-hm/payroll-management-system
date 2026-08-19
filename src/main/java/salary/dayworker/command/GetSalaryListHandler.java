package salary.dayworker.command;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.service.GetSalaryListService;
import salary.dayworker.service.request.GetSalaryListDTO;

public class GetSalaryListHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/components/_employeeListRows.jsp";

	private GetSalaryListService listService = new GetSalaryListService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		String salaryDateIdParam = req.getParameter("salary_date_id");

		List<GetSalaryListDTO> salaryList;

		if (salaryDateIdParam != null && !salaryDateIdParam.trim().isEmpty()) {
			try {
				Integer salaryDateId = Integer.parseInt(salaryDateIdParam);
				// DB 목록 조회
				salaryList = listService.getSalaryList(salaryDateId);
			} catch (NumberFormatException e) {
				// 숫자가 아닌 파라미터가 들어온 경우 빈 리스트 처리
				salaryList = Collections.emptyList();
			}
		} else {
			// salary_date_id가 전달되지 않은 경우 빈 리스트 세팅
			salaryList = Collections.emptyList();
		}

		// 3. request 영역에 데이터 저장
		req.setAttribute("salaryList", salaryList);

		// 4. HTML 조각 JSP로 포워딩
		return FORM_VIEW;
	}
}
