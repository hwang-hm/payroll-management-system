package employee.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.model.Employee;
import employee.model.Retirement;
import employee.modify.service.EmpInfoSelectService;
import employee.modify.service.EmpRequest;
import employee.modify2.service.CreateRequest2;
import employee.modify2.service.Emp2InfoSelectService;
import employee.modify2.service.EmpModify2Request;
import employee.modify2.service.EmpModify2Service;
import employee.modify2.service.RetirementRequest;
import mvc.command.CommandHandler;
import retirement.model.SeverancePay;

public class Modify2EmpHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/modify/modify2.jsp";

	private EmpModify2Service empModify2Service = new EmpModify2Service();
	private EmpInfoSelectService empInfoSelectService = new EmpInfoSelectService();
	private Emp2InfoSelectService emp2InfoSelectService = new Emp2InfoSelectService();
	private CreateRequest2 createReq = new CreateRequest2();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		String empIdParam = request.getParameter("empId");

		if (empIdParam == null || empIdParam.trim().isEmpty()) {
			request.setAttribute("modifyAccessDenied", true);
			return FORM_VIEW;
		}

		int empId = Integer.parseInt(empIdParam);
		Employee emp = empInfoSelectService.getEmp(empId);
		Retirement retirement = emp2InfoSelectService.getRetirement(empId);
		SeverancePay severancePay = emp2InfoSelectService.getSeverancePay(empId);

		request.setAttribute("emp", emp);
		request.setAttribute("retirement", retirement);
		request.setAttribute("severancePay", severancePay);

		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		String empIdParam = request.getParameter("empId");
		int empId = Integer.parseInt(empIdParam);
		
		EmpRequest empReq = createReq.createEmpRequest(request);
		empReq.validate(errors);
		
		RetirementRequest retirementReq = createReq.createRetirementRequest(request);
		
		EmpModify2Request modifyReq = new EmpModify2Request(empReq, retirementReq);
		empModify2Service.modifyEmp2(empId, modifyReq);
		
		Employee emp = empInfoSelectService.getEmp(empId);
		Retirement retirement = emp2InfoSelectService.getRetirement(empId);
		SeverancePay severancePay = emp2InfoSelectService.getSeverancePay(empId);
		
		request.setAttribute("emp", emp);
		request.setAttribute("retirement", retirement);
		request.setAttribute("ModifySuccess", true);
		request.setAttribute("severancePay", severancePay);
		
		return FORM_VIEW;
	}

}
