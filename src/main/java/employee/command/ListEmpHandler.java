package employee.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;

public class ListEmpHandler implements CommandHandler {
	
	private EmpListService empListService = new EmpListService(); 
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		EmpListPage empListPage = empListService.getEmpPage(pageNo);
		
		request.setAttribute("empListPage", empListPage);
		
		return "/WEB-INF/view/emp/list.jsp";
	}


}
