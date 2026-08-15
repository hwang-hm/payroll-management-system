package retirement.command;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.dao.EmpDao;
import mvc.command.CommandHandler;
import retirement.retire.service.EmpNotFoundExcpetion;
import retirement.retire.service.EmpRetireRequest;
import retirement.retire.service.EmpRetireService;

public class RetireEmpHandler implements CommandHandler {
	
	private EmpRetireService empRetireService = new EmpRetireService();
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return null;
            
		}else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	

	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EmpRetireRequest empRetireReq = new EmpRetireRequest(
				Integer.parseInt(request.getParameter("emp_id")), request.getParameter("retirement_type"), 
				Date.valueOf(request.getParameter("retirement_date")), request.getParameter("retirement_reason"),
				request.getParameter("contact_after_retirement"));
		
		try {
			empRetireService.retireEmp(empRetireReq);;
			return "redirect:/retirement/list.do";
		}catch (EmpNotFoundExcpetion e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
