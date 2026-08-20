package employee.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.register.service.CreateRequest;
import employee.register.service.DependentRequest;
import employee.register.service.EmpInfoSalaryRequest;
import employee.register.service.EmpRegisterRequest;
import employee.register.service.EmpRegisterService;
import employee.register.service.EmpRequest;
import mvc.command.CommandHandler;

public class RegisterEmpHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/config/empRegister.jsp";
	private EmpRegisterService empRegisterService = new EmpRegisterService();
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
	
	private String processForm(HttpServletRequest request, HttpServletResponse response){
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		
		EmpRequest empReq = createReq.createEmpRequest(request);
		empReq.validate(errors);
		
		EmpInfoSalaryRequest empInfoSalaryReq = createReq.createEmpInfoSalaryRequest(request);
		empInfoSalaryReq.validate(errors);
		
		List<DependentRequest> dependentReqs = createReq.createDependentRequests(request);
		
		if (dependentReqs == null || dependentReqs.isEmpty()) {
			errors.put("dependent", Boolean.TRUE);
		} 
		 else {
	           // 입력된 부양가족 각각 검증
	           for (DependentRequest dependentReq : dependentReqs) {
	               dependentReq.validate(errors);
	           }
	    }
		 
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		EmpRegisterRequest registerReq =
		        new EmpRegisterRequest(
		                empReq,
		                empInfoSalaryReq,
		                dependentReqs
		        );

		int newEmpNo = empRegisterService.registerEmp(registerReq);

		request.setAttribute("newEmpNo", newEmpNo);

		request.setAttribute("empReq", empReq);
		request.setAttribute("empInfoSalaryReq", empInfoSalaryReq);
		request.setAttribute("dependentReqs", dependentReqs);

		request.setAttribute("registerSuccess", true);

		return FORM_VIEW;
	}

}
