package attendance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.Attendance;
import attendance.model.AttendanceType;
import attendance.service.AttendanceService;
import attendance.service.AttendanceTypeService;
import mvc.command.CommandHandler;

/**
 * 근태기록 목록 조회 요청을 처리하는 Handler
 * 勤怠記録リスト照会リクエストを処理するHandler
 */
public class AttendanceListHandler implements CommandHandler {

	// 근태기록 Service / 勤怠記録Service
	private final AttendanceService attendanceService =
			new AttendanceService();

	// 근태항목 Service / 勤怠項目Service
	private final AttendanceTypeService attendanceTypeService =
			new AttendanceTypeService();

	/**
	 * 근태기록과 근태항목 목록을 조회한다.
	 * 勤怠記録と勤怠項目のリストを照会する。
	 */
	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		// 전체 근태기록 조회
		// すべての勤怠記録を照会
		List<Attendance> attendances =
				attendanceService.getAttendances();

		// 전체 근태항목 조회
		// すべての勤怠項目を照会
		List<AttendanceType> attendanceTypes =
				attendanceTypeService.getAttendanceTypes();

		req.setAttribute("attendances", attendances);
		req.setAttribute("attendanceTypes", attendanceTypes);

		// 선택한 근태기록 번호 확인
		// 選択した勤怠記録番号を確認
		String attendanceIdParam =
				req.getParameter("attendanceId");

		if (attendanceIdParam != null
				&& !attendanceIdParam.trim().isEmpty()) {

			try {
				int attendanceId =
						Integer.parseInt(attendanceIdParam);

				Attendance selectedAttendance =
						attendanceService.getAttendance(attendanceId);

				req.setAttribute(
						"selectedAttendance",
						selectedAttendance);

			} catch (NumberFormatException e) {
				req.setAttribute(
						"errorMessage",
						"正しい勤怠記録番号を入力してください。");
			}
		}

		return "/WEB-INF/view/attendance/record.jsp";
	}
}