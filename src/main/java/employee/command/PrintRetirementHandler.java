package employee.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.certificate.service.PrintCertificateService;
import employee.dto.EmployeeDetailData;
import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;

public class PrintRetirementHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/certificate/retirement.jsp";

	private EmpListService empListService = new EmpListService();
	private PrintCertificateService printCertificateService = new PrintCertificateService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return null;
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {

		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null && !pageNoVal.trim().isEmpty()) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		EmpListPage empListPage = empListService.getEmpPage(pageNo);

		request.setAttribute("empListPage", empListPage);
		request.setAttribute("certificateType", "retirement");

		String empIdParam = request.getParameter("empId");

		if (empIdParam == null || empIdParam.trim().isEmpty()) {
			return FORM_VIEW;
		}

		int empId = Integer.parseInt(empIdParam);

		EmployeeDetailData emp = printCertificateService.selectEmp(empId);

		if (emp == null) {
			request.setAttribute("employeeNotFound", true);
			return FORM_VIEW;
		}

		if ("Y".equals(emp.getStatus())) {
			request.setAttribute("workingEmployee", true);
			return FORM_VIEW;
		}

		request.setAttribute("selectedEmpId", empId);
		request.setAttribute("emp", emp);

		return FORM_VIEW;
	}
}