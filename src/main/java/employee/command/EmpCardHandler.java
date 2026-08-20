package employee.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.dto.EmployeeDetailData;
import employee.empCard.service.EmpCardData;
import employee.empCard.service.EmpCardService;
import mvc.command.CommandHandler;

public class EmpCardHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/emp/empCard.jsp";
	
	private EmpCardService empCardService = new EmpCardService();
	
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
		List<EmployeeDetailData> empList = empCardService.getEmpList();
	    request.setAttribute("empList", empList);

	    String empIdParam = request.getParameter("empId");
	    if (empIdParam == null || empIdParam.trim().isEmpty()) {
	        return FORM_VIEW;
	    }

	    int empId = Integer.parseInt(empIdParam);
	    EmpCardData empCardData = empCardService.getEmpCard(empId);

	    request.setAttribute("empCardData", empCardData);
	    request.setAttribute("EmpCardSuccess", true);

	    return FORM_VIEW;

	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		List<EmployeeDetailData> empList = empCardService.getEmpList();
	    request.setAttribute("empList", empList);
	    
		String empIdParam = request.getParameter("empId");
		int empId = Integer.parseInt(empIdParam);

		EmpCardData empCardData = empCardService.getEmpCard(empId);

		request.setAttribute("empCardData", empCardData);
		request.setAttribute("EmpCardSuccess", true);

		return FORM_VIEW;
	}

}
