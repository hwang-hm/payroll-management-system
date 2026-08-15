package attendance.command;

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

	// 사원 목록 Service / 社員リストService
	private final EmpListService empListService =
			new EmpListService();

	/**
	 * 사원, 근태기록, 근태항목 목록을 조회한다.
	 * 社員、勤怠記録、勤怠項目のリストを照会する。
	 */
	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		// 사원 목록의 현재 페이지 번호
		// 社員リストの現在のページ番号
		int pageNo = parsePageNo(
				req.getParameter("pageNo"));

		// 사원 목록 조회
		// 社員リストを照会
		EmpListPage empListPage =
				empListService.getEmpPage(pageNo);

		// 전체 근태기록 조회
		// すべての勤怠記録を照会
		List<Attendance> attendances =
				attendanceService.getAttendances();

		// 전체 근태항목 조회
		// すべての勤怠項目を照会
		List<AttendanceType> attendanceTypes =
				attendanceTypeService.getAttendanceTypes();

		req.setAttribute("empListPage", empListPage);
		req.setAttribute("attendances", attendances);
		req.setAttribute("attendanceTypes", attendanceTypes);

		// 선택한 사원번호를 화면에 전달한다.
		// 選択した社員番号を画面に渡す。
		String empIdParam = req.getParameter("empId");

		if (empIdParam != null
				&& !empIdParam.trim().isEmpty()) {

			try {
				int selectedEmpId =
						Integer.parseInt(empIdParam.trim());

				req.setAttribute(
						"selectedEmpId",
						selectedEmpId);

				List<Attendance> employeeAttendances =
						attendanceService
								.getAttendancesByEmpId(
										selectedEmpId);

				req.setAttribute(
						"employeeAttendances",
						employeeAttendances);

			} catch (NumberFormatException e) {
				req.setAttribute(
						"errorMessage",
						"正しい社員番号を選択してください。");
			}
		}

		// 수정할 근태기록 번호를 확인한다.
		// 修正する勤怠記録番号を確認する。
		String attendanceIdParam =
				req.getParameter("attendanceId");

		if (attendanceIdParam != null
				&& !attendanceIdParam.trim().isEmpty()) {

			try {
				int attendanceId =
						Integer.parseInt(
								attendanceIdParam.trim());

				Attendance selectedAttendance =
						attendanceService
								.getAttendance(attendanceId);

				req.setAttribute(
						"selectedAttendance",
						selectedAttendance);

				// 수정 화면에서도 선택된 사원을 유지한다.
				// 修正画面でも選択した社員を維持する。
				if (selectedAttendance != null) {
					req.setAttribute(
							"selectedEmpId",
							selectedAttendance.getEmpId());
				}

			} catch (NumberFormatException e) {
				req.setAttribute(
						"errorMessage",
						"正しい勤怠記録番号を選択してください。");
			}
		}

		return "/WEB-INF/view/attendance/record.jsp";
	}

	/**
	 * 페이지 번호를 변환한다.
	 * ページ番号を変換する。
	 */
	private int parsePageNo(String value) {

		if (value == null || value.trim().isEmpty()) {
			return 1;
		}

		try {
			int pageNo = Integer.parseInt(value.trim());

			return pageNo > 0 ? pageNo : 1;

		} catch (NumberFormatException e) {
			return 1;
		}
	}
}