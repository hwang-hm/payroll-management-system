package salary.ledger.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.ledger.service.EmpSalaryHistoryService;
import salary.ledger.viewdto.EmpSalaryHistoryEmployee;
import salary.util.RequestParamParser;

// 사원별 급여내역 사원선택 모달 검색
public class EmpSalaryHistorySearchHandler implements CommandHandler {

	private final EmpSalaryHistoryService empSalaryHistoryService = new EmpSalaryHistoryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String keyword = req.getParameter("keyword");
		Integer deptId = RequestParamParser.parseNullableInt(req, "deptId");
		String status = req.getParameter("status");

		List<EmpSalaryHistoryEmployee> employeeList = empSalaryHistoryService.searchEmployees(keyword, deptId, status);
		req.setAttribute("employeeList", employeeList);
		return "/WEB-INF/view/salary/ledger/empSalaryHistoryModalRows.jsp";
	}
}
