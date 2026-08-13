package attendance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.AttendanceType;
import attendance.model.VacationType;
import attendance.service.AttendanceTypeService;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

/**
 * 휴가항목 및 근태항목 설정 요청을 처리하는 Handler
 * 休暇項目および勤怠項目の設定リクエストを処理するHandler
 */
public class VacationAttendanceConfigHandler implements CommandHandler {

    // 근태항목 Service / 勤怠項目Service
	private final AttendanceTypeService attendanceTypeService = new AttendanceTypeService();
	
	// 휴가항목 Service / 休暇項目Service
	private final VacationTypeService vacationTypeService = new VacationTypeService();
	
	/**
	 * 휴가항목과 근태항목 목록 및 선택된 항목의 상세정보를 조회한다.
	 * 休暇項目と勤怠項目の一覧および選択された項目の詳細情報を照会する。
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 전체 근태항목 조회 / すべての勤怠項目を照会
		List<AttendanceType> attendanceTypes = attendanceTypeService.getAttendanceTypes();
		req.setAttribute("attendanceTypes", attendanceTypes);

		// 전체 휴가항목 조회
		// すべての休暇項目を照会
		List<VacationType> vacationTypes = vacationTypeService.getVacationTypes();
		req.setAttribute("vacationTypes", vacationTypes);

		String vacationTypeIdParam = req.getParameter("vacationTypeId");

		if (vacationTypeIdParam != null && !vacationTypeIdParam.trim().isEmpty()) {

			try {
				int vacationTypeId = Integer.parseInt(vacationTypeIdParam);

				VacationType selectedVacationType = vacationTypeService.getVacationType(vacationTypeId);

				req.setAttribute("selectedVacationType", selectedVacationType);

			} catch (NumberFormatException e) {
				req.setAttribute("errorMessage", "休暇項目番号が正しくありません。");
			}
		}

		// 수정 또는 삭제할 근태항목을 선택했을 때 상세정보 조회
		// 修正または削除する勤怠項目を選択した場合、詳細情報を照会
		String attendanceTypeIdParam = req.getParameter("attendanceTypeId");

		if (attendanceTypeIdParam != null && !attendanceTypeIdParam.trim().isEmpty()) {

			try {
				int attendanceTypeId = Integer.parseInt(attendanceTypeIdParam);

				AttendanceType selectedAttendanceType = attendanceTypeService.getAttendanceType(attendanceTypeId);

				req.setAttribute("selectedAttendanceType", selectedAttendanceType);

			} catch (NumberFormatException e) {
				req.setAttribute("errorMessage", "正しい勤怠項目番号を入力してください。");
			}
		}

		return "/WEB-INF/view/config/vacationAttendance.jsp";
	}
}