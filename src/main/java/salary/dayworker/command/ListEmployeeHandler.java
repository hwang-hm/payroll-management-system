package salary.dayworker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.model.Dayworker;
import salary.dayworker.service.ListEmployeeService;

public class ListEmployeeHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/components/empSearchModal.jsp";

	private ListEmployeeService listService = new ListEmployeeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		// 전체 사원 목록 조회 (또는 키워드 검색)
		String keyword = req.getParameter("keyword");
		// 1. DB 목록 조회
		List<Dayworker> workerList = listService.getEmpList(keyword);

		// 2. request 영역에 데이터 저장
		req.setAttribute("empList", workerList);

		// 3. HTML 조각 JSP로 포워딩
		return FORM_VIEW;
	}

}
