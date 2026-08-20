package employee.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.model.Dependent;
import employee.model.EmpInfoSalary;
import employee.model.Employee;
import employee.modify.service.CreateRequest;
import employee.modify.service.DependentRequest;
import employee.modify.service.EmpInfoSalaryRequest;
import employee.modify.service.EmpInfoSelectService;
import employee.modify.service.EmpModifyRequest;
import employee.modify.service.EmpModifyService;
import employee.modify.service.EmpRequest;
import mvc.command.CommandHandler;

public class ModifyEmpHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/modify.jsp";

	private EmpModifyService empModifyService = new EmpModifyService();
	private EmpInfoSelectService empInfoSelectService = new EmpInfoSelectService();
	private CreateRequest createReq = new CreateRequest();

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
		EmpInfoSalary empInfoSalary = empInfoSelectService.getEmpInfoSalary(empId);
		List<Dependent> dependent = empInfoSelectService.getDependent(empId);

		request.setAttribute("emp", emp);
		request.setAttribute("empInfoSalary", empInfoSalary);
		request.setAttribute("dependent", dependent);

		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);

		String empIdParam = request.getParameter("empId");
		int empId = Integer.parseInt(empIdParam);

		EmpRequest empReq = createReq.createEmpRequest(request);
		empReq.validate(errors);

		EmpInfoSalaryRequest empInfoSalaryReq = createReq.createEmpInfoSalaryRequest(request);
		empInfoSalaryReq.validate(errors);

		List<DependentRequest> dependentReqs = createReq.createDependentRequests(request);

		if (dependentReqs == null || dependentReqs.isEmpty()) {
			errors.put("dependent", Boolean.TRUE);
		} else {
			// 입력된 부양가족 각각 검증
			for (DependentRequest dependentReq : dependentReqs) {
				dependentReq.validate(errors);
			}
		}

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		EmpModifyRequest modifyReq = new EmpModifyRequest(empReq, empInfoSalaryReq, dependentReqs);

		empModifyService.modifyEmp(empId, modifyReq);

		Employee emp = empInfoSelectService.getEmp(empId);
		EmpInfoSalary empInfoSalary = empInfoSelectService.getEmpInfoSalary(empId);
		List<Dependent> dependent = empInfoSelectService.getDependent(empId);

		request.setAttribute("emp", emp);
		request.setAttribute("empInfoSalary", empInfoSalary);
		request.setAttribute("dependent", dependent);

		request.setAttribute("ModifySuccess", true);

		return FORM_VIEW;
	}

}
