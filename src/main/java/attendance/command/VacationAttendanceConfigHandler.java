package attendance.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.service.AttendanceTypeService;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

public class VacationAttendanceConfigHandler implements CommandHandler {

	private final VacationTypeService vacationTypeService = new VacationTypeService();
	
	private final AttendanceTypeService attendanceTypeService = new AttendanceTypeService();
	
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpServletRequest req, HttpServletResponse res) throws Exception {
			
			List<VacationType> vacationTypes = vacationTypeService
		}
		
		return null;
	}

}
