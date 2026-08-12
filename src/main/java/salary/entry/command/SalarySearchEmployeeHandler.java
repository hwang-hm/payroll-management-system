package salary.entry.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalarySearchEmployeeService;
import salary.entry.viewdto.EmployeeSearchView;

public class SalarySearchEmployeeHandler implements CommandHandler {

	private final SalarySearchEmployeeService searchEmployeeService = new SalarySearchEmployeeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int salaryDateId = Integer.parseInt(req.getParameter("salaryDateId"));
		String searchKeyword = req.getParameter("searchKeyword");

		List<EmployeeSearchView> employeeList = searchEmployeeService.search(salaryDateId, searchKeyword);
		req.setAttribute("employeeList", employeeList);

		return "/WEB-INF/view/salary/entry/employeeModal.jsp";
	}
}
