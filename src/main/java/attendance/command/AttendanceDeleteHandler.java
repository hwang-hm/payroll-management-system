package attendance.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.Attendance;
import attendance.service.AttendanceService;
import mvc.command.CommandHandler;

/**
 * 근태기록 삭제 요청을 처리하는 Handler
 * 勤怠記録の削除リクエストを処理するHandler
 */
public class AttendanceDeleteHandler implements CommandHandler {

	// 근태기록 Service / 勤怠記録Service
	private final AttendanceService attendanceService =
			new AttendanceService();

	/**
	 * 근태기록을 삭제한다.
	 * 勤怠記録を削除する。
	 */
	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		// POST 요청만 삭제 처리
		// POSTリクエストのみ削除処理
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/attendance/record/list.do";
		}

		try {
			// 삭제할 근태기록 번호를 변환한다.
			// 削除する勤怠記録番号を変換する。
			int attendanceId = parseRequiredInt(
					req.getParameter("attendanceId"));

			// 삭제 대상이 실제로 존재하는지 확인한다.
			// 削除対象が実際に存在するか確認する。
			Attendance savedAttendance =
					attendanceService.getAttendance(attendanceId);

			if (savedAttendance == null) {
				return "redirect:/attendance/record/list.do"
						+ "?error=notFound";
			}

			boolean deleted =
					attendanceService.removeAttendance(attendanceId);

			if (!deleted) {
				return "redirect:/attendance/record/list.do"
						+ "?error=deleteFailed";
			}

			return "redirect:/attendance/record/list.do"
					+ "?result=deleted";

		} catch (IllegalArgumentException e) {
			return "redirect:/attendance/record/list.do"
					+ "?error=invalidInput";

		} catch (RuntimeException e) {
			/*
			 * 다른 테이블에서 사용 중이거나 DB 오류가 발생하면
			 * 사용자가 확인할 수 있도록 목록 화면으로 안내한다.
			 *
			 * 他のテーブルで使用中、またはDBエラーが発生した場合、
			 * ユーザーが確認できるように一覧画面へ案内する。
			 */
			return "redirect:/attendance/record/list.do"
					+ "?error=deleteFailed";
		}
	}

	/**
	 * 필수 정수 입력값을 변환한다.
	 * 必須の整数入力値を変換する。
	 */
	private int parseRequiredInt(String value) {

		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
}