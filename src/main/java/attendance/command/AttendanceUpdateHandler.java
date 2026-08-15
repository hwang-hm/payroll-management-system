package attendance.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.Attendance;
import attendance.service.AttendanceService;
import mvc.command.CommandHandler;

/**
 * 근태기록 수정 요청을 처리하는 Handler
 * 勤怠記録の修正リクエストを処理するHandler
 */
public class AttendanceUpdateHandler implements CommandHandler {

	// 근태기록 Service / 勤怠記録Service
	private final AttendanceService attendanceService =
			new AttendanceService();

	/**
	 * 근태기록을 수정한다.
	 * 勤怠記録を修正する。
	 */
	@Override
	public String process(
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		// POST 요청만 수정 처리
		// POSTリクエストのみ修正処理
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/attendance/record/list.do";
		}

		try {
			// 수정할 근태기록 번호
			// 修正する勤怠記録番号
			int attendanceId = parseRequiredInt(
					req.getParameter("attendanceId"));

			int empId = parseRequiredInt(
					req.getParameter("empId"));

			int attendanceTypeId = parseRequiredInt(
					req.getParameter("attendanceTypeId"));

			Date attendanceStartDate = parseRequiredDate(
					req.getParameter("attendanceStartDate"));

			Date attendanceEndDate = parseOptionalDate(
					req.getParameter("attendanceEndDate"));

			double attendanceDays = parseRequiredDouble(
					req.getParameter("attendanceDays"));

			int allowanceAmount = parseOptionalInt(
					req.getParameter("allowanceAmount"));

			String remarks = trim(
					req.getParameter("remarks"));

			// 종료일을 입력하지 않으면 시작일과 같은 날짜로 처리한다.
			// 終了日が未入力の場合は開始日と同じ日付にする。
			if (attendanceEndDate == null) {
				attendanceEndDate = attendanceStartDate;
			}

			// 종료일은 시작일보다 빠를 수 없다.
			// 終了日は開始日より前に設定できない。
			if (attendanceEndDate.before(attendanceStartDate)) {
				return "redirect:/attendance/record/list.do"
						+ "?attendanceId=" + attendanceId
						+ "&error=invalidDate";
			}

			// 근태일수는 0보다 커야 한다.
			// 勤怠日数は0より大きくなければならない。
			if (attendanceDays <= 0) {
				return "redirect:/attendance/record/list.do"
						+ "?attendanceId=" + attendanceId
						+ "&error=invalidDays";
			}

			// 수정 대상이 실제로 존재하는지 확인한다.
			// 修正対象が実際に存在するか確認する。
			Attendance savedAttendance =
					attendanceService.getAttendance(attendanceId);

			if (savedAttendance == null) {
				return "redirect:/attendance/record/list.do"
						+ "?error=notFound";
			}

			Attendance attendance = new Attendance();

			attendance.setAttendanceId(attendanceId);
			attendance.setEmpId(empId);
			attendance.setAttendanceTypeId(attendanceTypeId);
			attendance.setAttendanceStartDate(
					attendanceStartDate);
			attendance.setAttendanceEndDate(
					attendanceEndDate);
			attendance.setAttendanceDays(attendanceDays);
			attendance.setAllowanceAmount(allowanceAmount);
			attendance.setRemarks(remarks);

			boolean updated =
					attendanceService.modifyAttendance(attendance);

			if (!updated) {
				return "redirect:/attendance/record/list.do"
						+ "?attendanceId=" + attendanceId
						+ "&error=updateFailed";
			}

			return "redirect:/attendance/record/list.do"
					+ "?result=updated";

		} catch (IllegalArgumentException e) {
			return "redirect:/attendance/record/list.do"
					+ "?error=invalidInput";
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

	/**
	 * 선택 정수 입력값을 변환한다.
	 * 任意の整数入力値を変換する。
	 */
	private int parseOptionalInt(String value) {

		if (value == null || value.trim().isEmpty()) {
			return 0;
		}

		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 필수 실수 입력값을 변환한다.
	 * 必須の小数入力値を変換する。
	 */
	private double parseRequiredDouble(String value) {

		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		try {
			return Double.parseDouble(value.trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 필수 날짜 입력값을 변환한다.
	 * 必須の日付入力値を変換する。
	 */
	private Date parseRequiredDate(String value) {

		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		try {
			return Date.valueOf(value.trim());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 선택 날짜 입력값을 변환한다.
	 * 任意の日付入力値を変換する。
	 */
	private Date parseOptionalDate(String value) {

		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {
			return Date.valueOf(value.trim());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 문자열의 앞뒤 공백을 제거한다.
	 * 文字列の前後の空白を削除する。
	 */
	private String trim(String value) {
		return value == null ? "" : value.trim();
	}
}