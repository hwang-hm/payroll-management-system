package salary.entry.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryAddEmployeesService;

public class SalaryAddEmployeesHandler implements CommandHandler {

	private final SalaryAddEmployeesService addEmployeesService = new SalaryAddEmployeesService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		int salaryDateId = Integer.parseInt(req.getParameter("salaryDateId"));
		int payYear = Integer.parseInt(req.getParameter("payYear"));
		int payMonth = Integer.parseInt(req.getParameter("payMonth"));
		int paySeq = Integer.parseInt(req.getParameter("paySeq"));

		String[] empIdArr = req.getParameterValues("empIds");
		List<Integer> empIds = new ArrayList<>();
		if (empIdArr != null) {
			for (String empId : empIdArr) {
				empIds.add(Integer.parseInt(empId));
			}
		}

		addEmployeesService.add(salaryDateId, empIds);

		return "redirect:/salary/entry.do?payYear=" + payYear + "&payMonth=" + payMonth + "&paySeq=" + paySeq;
	}
}
