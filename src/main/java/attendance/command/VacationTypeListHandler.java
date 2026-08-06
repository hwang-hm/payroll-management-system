package attendance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.VacationType;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

public class VacationTypeListHandler implements CommandHandler {

	private final VacationTypeService vacationTypeService = new VacationTypeService();
	
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

	List<VacationType> vacationTypes = vacationTypeService.getVacationTypes();
	
	req.setAttribute("vacationTypes", vacationTypes);
	
	return "/view/config/vacationType.jsp";
	}

}
