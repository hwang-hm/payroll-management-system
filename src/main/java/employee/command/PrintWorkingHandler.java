package employee.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.certificate.service.PrintCertificateService;
import employee.dto.EmployeeDetailData;
import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;

public class PrintWorkingHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/certificate/working.jsp";

	private EmpListService empListService = new EmpListService();
	private PrintCertificateService printCertificateService = new PrintCertificateService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return null;
	}

	// 증명서 화면 조회
	private String processForm(HttpServletRequest request, HttpServletResponse response) {

		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;

		if (pageNoVal != null && !pageNoVal.trim().isEmpty()) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		EmpListPage empListPage = empListService.getEmpPage(pageNo);

		request.setAttribute("empListPage", empListPage);
		request.setAttribute("certificateType", "working");

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

		// 퇴직자는 재직증명서 발급 불가
		if ("N".equals(emp.getStatus())) {
			request.setAttribute("retiredEmployee", true);
			return FORM_VIEW;
		}

		request.setAttribute("selectedEmpId", empId);
		request.setAttribute("emp", emp);

		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		String empIdParam = request.getParameter("empId");

		if (empIdParam == null || empIdParam.trim().isEmpty()) {
			return "redirect:/emp/certificate/working.do";
		}

		int empId = Integer.parseInt(empIdParam);

		EmployeeDetailData emp = printCertificateService.selectEmp(empId);
		if (emp == null) {
			request.setAttribute("employeeNotFound", true);
			return FORM_VIEW;
		}

		if ("N".equals(emp.getStatus())) {
			request.setAttribute("retiredEmployee", true);
			return FORM_VIEW;
		}

		String purpose = request.getParameter("purpose");
		String issueDepartment = request.getParameter("issueDepartment");
		printCertificateService.issueCertificate(empId, "WORKING", purpose, issueDepartment);

		return "redirect:/emp/certificate/working.do?empId=" + empId;
	}
}