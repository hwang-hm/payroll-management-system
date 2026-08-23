package retirement.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;
import retirement.specification.service.PrintSpecificationData;
import retirement.specification.service.PrintSpecificationService;

public class PrintSpecificationHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/retirement/specification.jsp";

	private EmpListService empListService = new EmpListService();
	private PrintSpecificationService printSpecificationService = new PrintSpecificationService();

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

		String empIdParam = request.getParameter("empId");
		if (empIdParam == null || empIdParam.trim().isEmpty()) {

			return FORM_VIEW;
		}

		int empId = Integer.parseInt(empIdParam);

		PrintSpecificationData specificationData = printSpecificationService.getSpecificationData(empId);
		request.setAttribute("specificationData", specificationData);

		return FORM_VIEW;
	}

}
