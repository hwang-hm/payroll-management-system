package salary.entry.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalarySearchEmployeeService;
import salary.entry.viewdto.EmployeeSearchView;

// 급여입력에서 사원 추가할 때 검색 모달 열어주는 핸들러.
// 給与入力で社員追加時に検索モーダルを開くHandler。
public class SalarySearchEmployeeHandler implements CommandHandler {

	private final SalarySearchEmployeeService searchEmployeeService = new SalarySearchEmployeeService();

	// 검색 결과만 모달 jsp로 넘겨줌
	// 検索結果のみモーダル jsp へ渡す
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int salaryDateId = Integer.parseInt(req.getParameter("salaryDateId"));
		String searchKeyword = req.getParameter("searchKeyword");

		List<EmployeeSearchView> employeeList = searchEmployeeService.search(salaryDateId, searchKeyword);
		req.setAttribute("employeeList", employeeList);

		return "/WEB-INF/view/salary/entry/employeeModal.jsp";
	}
}
