package attendance.command;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.Attendance;
import attendance.model.AttendanceType;
import attendance.service.AttendanceService;
import attendance.service.AttendanceTypeService;
import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;

/**
 * 근태조회 화면 요청을 처리하는 Handler
 * 勤怠照会画面のリクエストを処理するHandler
 */
public class AttendanceInquiryHandler implements CommandHandler {

	private final AttendanceService attendanceService =
			new AttendanceService();
	private final AttendanceTypeService attendanceTypeService =
			new AttendanceTypeService();
	private final EmpListService empListService =
			new EmpListService();

	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		int pageNo = parsePositiveInt(req.getParameter("pageNo"), 1);
		EmpListPage empListPage = empListService.getEmpPage(pageNo);
		List<AttendanceType> attendanceTypes =
				attendanceTypeService.getAttendanceTypes();

		LocalDate today = LocalDate.now();
		int selectedYear = parsePositiveInt(
				req.getParameter("year"), today.getYear());
		int selectedMonth = parseMonth(
				req.getParameter("month"), today.getMonthValue());
		Integer selectedEmpId = parseOptionalId(
				req.getParameter("empId"));

		List<Attendance> monthlyAttendances = Collections.emptyList();
		if (selectedEmpId != null) {
			monthlyAttendances = attendanceService.getMonthlyAttendances(
					selectedEmpId, selectedYear, selectedMonth);
		}

		req.setAttribute("empListPage", empListPage);
		req.setAttribute("attendanceTypes", attendanceTypes);
		req.setAttribute("selectedEmpId", selectedEmpId);
		req.setAttribute("selectedYear", selectedYear);
		req.setAttribute("selectedMonth", selectedMonth);
		req.setAttribute("monthlyAttendances", monthlyAttendances);

		return "/WEB-INF/view/attendance/attendanceList.jsp";
	}

	private int parsePositiveInt(String value, int defaultValue) {
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			int parsedValue = Integer.parseInt(value.trim());
			return parsedValue > 0 ? parsedValue : defaultValue;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private int parseMonth(String value, int defaultValue) {
		int month = parsePositiveInt(value, defaultValue);
		return month >= 1 && month <= 12 ? month : defaultValue;
	}

	private Integer parseOptionalId(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			int parsedValue = Integer.parseInt(value.trim());
			return parsedValue > 0 ? parsedValue : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
