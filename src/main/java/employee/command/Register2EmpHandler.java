package employee.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.model.Employee;
import employee.model.Retirement;
import employee.modify2.service.Emp2InfoSelectService;
import employee.register2.service.CreateRequest2;
import employee.register2.service.EmpRegister2Request;
import employee.register2.service.EmpRegister2Service;
import employee.register2.service.RetirementRequest;
import mvc.command.CommandHandler;
import retirement.model.SeverancePay;

public class Register2EmpHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/config/empRegister2.jsp";

	private EmpRegister2Service empRegister2Service =new EmpRegister2Service();
	private Emp2InfoSelectService emp2InfoSelectService = new Emp2InfoSelectService();
	private CreateRequest2 createReq2 = new CreateRequest2();

	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);

		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);

		} else {
			response.setStatus(
					HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}


	// 사원등록2 화면
	private String processForm(HttpServletRequest request,
			HttpServletResponse response) {

		String empIdParam = request.getParameter("empId");

	    if (empIdParam == null || empIdParam.trim().isEmpty()) {
	        request.setAttribute("register2AccessDenied", true);
	        return FORM_VIEW;
	    }

	    int empId = Integer.parseInt(empIdParam);
	    Employee emp = empRegister2Service.getEmp(empId);
	    Retirement retirement = empRegister2Service.getRetirement(empId);
	    SeverancePay severancePay = emp2InfoSelectService.getSeverancePay(empId);
	    
	    request.setAttribute("emp", emp);
	    request.setAttribute("retirement", retirement);
	    request.setAttribute("severancePay", severancePay);

	    return FORM_VIEW;
	}


	// 사원정보2 저장
	private String processSubmit(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Boolean> errors = new HashMap<>();
        request.setAttribute("errors", errors);

        String empIdParam = request.getParameter("empId");

        if (empIdParam == null || empIdParam.trim().isEmpty()) {
            request.setAttribute("register2AccessDenied", true);
            return FORM_VIEW;
        }

        int empId = Integer.parseInt(empIdParam);

        // 사원 조회
        Employee emp =  empRegister2Service.getEmp(empId);


        // request → RetirementRequest
        RetirementRequest retirementReq = createReq2.createRetirementRequest(request);


        // RetirementRequest → EmpRegister2Request
        EmpRegister2Request registerReq2 = new EmpRegister2Request(
                        retirementReq
                );


        // 퇴직정보 저장
        empRegister2Service.registereEmp2(registerReq2);

        // 저장된 DB 값을 다시 조회
        Retirement retirement = empRegister2Service.getRetirement(empId);
        SeverancePay severancePay = emp2InfoSelectService.getSeverancePay(empId);

        // JSP 전달
        request.setAttribute("emp", emp);
        request.setAttribute("retirement", retirement);
        request.setAttribute("register2Success",true);
        request.setAttribute("severancePay", severancePay);

        return FORM_VIEW;
	}

}