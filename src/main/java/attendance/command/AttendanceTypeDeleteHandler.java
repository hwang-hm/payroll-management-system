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
 * 근태항목 삭제 요청을 처리하는 Handler
 * 勤怠項目削除リクエストを処理するHandler
 */
public class AttendanceTypeDeleteHandler implements CommandHandler {

    // 근태항목 Service / 勤怠項目Service
    private final AttendanceTypeService attendanceTypeService =
            new AttendanceTypeService();

    // 휴가항목 Service / 休暇項目Service
    private final VacationTypeService vacationTypeService =
            new VacationTypeService();

    /**
     * 근태항목 삭제 요청을 처리한다.
     * 勤怠項目削除リクエストを処理する。
     */
    @Override
    public String process(
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        // POST 요청만 삭제 처리
        // POSTリクエストのみ削除処理
        if (!"POST".equalsIgnoreCase(req.getMethod())) {
            return "redirect:/attendance/type/list.do";
        }

        try {
            int attendanceTypeId =
                    Integer.parseInt(
                            req.getParameter("attendanceTypeId"));

            if (!attendanceTypeService.removeAttendanceType(
                    attendanceTypeId)) {

                return showError(
                        req,
                        attendanceTypeId,
                        "削除する勤怠項目がありません。");
            }

            return "redirect:/attendance/type/list.do"
                    + "?result=deleted";

        } catch (NumberFormatException e) {
            return showError(
                    req,
                    0,
                    "削除する勤怠項目を選択してください。");

        } catch (RuntimeException e) {
            // 다른 테이블에서 참조하는 근태항목은 삭제하지 않는다.
            // 他のテーブルから参照されている勤怠項目は削除しない。
            return showError(
                    req,
                    toInt(req.getParameter("attendanceTypeId")),
                    "使用中の勤怠項目は削除できません。");
        }
    }

    /**
     * 오류 발생 시 목록과 선택값을 다시 화면에 표시한다.
     * エラー発生時、リストと選択値を画面に再表示する。
     */
    private String showError(
            HttpServletRequest req,
            int attendanceTypeId,
            String message) {

        List<AttendanceType> attendanceTypes =
                attendanceTypeService.getAttendanceTypes();

        List<VacationType> vacationTypes =
                vacationTypeService.getVacationTypes();

        req.setAttribute(
                "attendanceTypes", attendanceTypes);

        req.setAttribute(
                "vacationTypes", vacationTypes);

        if (attendanceTypeId > 0) {
            AttendanceType selectedAttendanceType =
                    attendanceTypeService.getAttendanceType(
                            attendanceTypeId);

            req.setAttribute(
                    "selectedAttendanceType",
                    selectedAttendanceType);
        }

        req.setAttribute(
                "errorMessage", message);

        return "/view/config/attendanceType.jsp";
    }

    /**
     * 문자열을 정수로 변환한다.
     * 文字列を整数に変換する。
     */
    private int toInt(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0;
            }

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {
            return 0;
        }
    }
}