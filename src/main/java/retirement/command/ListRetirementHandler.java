package retirement.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import retirement.service.RetirementEmpPage;
import retirement.service.RetirementListService;

public class ListRetirementHandler implements CommandHandler {

	private RetirementListService retirementListService = new RetirementListService();
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		RetirementEmpPage retirementEmpPage = retirementListService.getRetirementEmpPage(pageNo);
		
		System.out.println("total = " + retirementEmpPage.getTotal());
		System.out.println(
		    "list size = "
		    + retirementEmpPage.getRetirementEmpListData().size()
		);
		
		request.setAttribute("retirementEmpPage", retirementEmpPage);
		
		
		return "/view/retirement/listRetirement.jsp";
	}
}
