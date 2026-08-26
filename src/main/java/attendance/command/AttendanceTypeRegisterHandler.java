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
 * 근태항목 등록 요청을 처리하는 Handler
 * 勤怠項目登録リクエストを処理するHandler
 */
public class AttendanceTypeRegisterHandler implements CommandHandler {

    // 근태항목 Service / 勤怠項目Service
    private final AttendanceTypeService attendanceTypeService =
            new AttendanceTypeService();

    // 휴가항목 Service / 休暇項目Service
    private final VacationTypeService vacationTypeService =
            new VacationTypeService();

    /**
     * 근태항목 등록 요청을 처리한다.
     * 勤怠項目登録リクエストを処理する。
     */
    @Override
    public String process(
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        // POST 요청만 등록 처리
        // POSTリクエストのみ登録処理
        if (!"POST".equalsIgnoreCase(req.getMethod())) {
            return "redirect:/config/vacation-attendance.do#attendance";
        }
        AttendanceType attendanceType =
                createAttendanceType(req);

        String validationMessage =
                validate(attendanceType);

        if (validationMessage != null) {
            return showForm(
                    req, attendanceType, validationMessage);
        }

        if (!attendanceTypeService.addAttendanceType(
                attendanceType)) {

            return showForm(
                    req,
                    attendanceType,
                    "勤怠項目を登録できませんでした。");
        }

        return "redirect:/config/vacation-attendance.do?result=attendanceRegistered#attendance";
    }

    /**
     * 요청값으로 근태항목 객체를 생성한다.
     * リクエスト値から勤怠項目オブジェクトを生成する。
     */
    private AttendanceType createAttendanceType(
            HttpServletRequest req) {

        AttendanceType attendanceType =
                new AttendanceType();

        attendanceType.setAttendanceType(
                trim(req.getParameter("attendanceType")));

        attendanceType.setUnit(
                trim(req.getParameter("unit")));

        attendanceType.setGroupName(
                trim(req.getParameter("groupName")));

        attendanceType.setUseYn(
                "N".equals(req.getParameter("useYn"))
                        ? "N" : "Y");

        attendanceType.setVacationTypeId(
                toInt(req.getParameter("vacationTypeId")));

        return attendanceType;
    }

    /**
     * 입력값을 검사한다.
     * 入力値を検証する。
     */
    private String validate(
            AttendanceType attendanceType) {

        if (attendanceType.getAttendanceType().isEmpty()) {
            return "勤怠項目名を入力してください。";
        }

        if (attendanceType.getUnit().isEmpty()) {
            return "単位を選択してください。";
        }

        if (attendanceType.getGroupName().isEmpty()) {
            return "勤怠グループを選択してください。";
        }

        return null;
    }

    /**
     * 오류 발생 시 목록과 입력값을 다시 화면에 표시한다.
     * エラー発生時、リストと入力値を画面に再表示する。
     */
    private String showForm(
            HttpServletRequest req,
            AttendanceType attendanceType,
            String message) {

        List<AttendanceType> attendanceTypes =
                attendanceTypeService.getAttendanceTypes();

        List<VacationType> vacationTypes =
                vacationTypeService.getVacationTypes();

        req.setAttribute(
                "attendanceTypes", attendanceTypes);

        req.setAttribute(
                "vacationTypes", vacationTypes);

        req.setAttribute(
                "formAttendanceType", attendanceType);

        req.setAttribute(
                "errorMessage", message);

        return "/WEB-INF/view/config/attendanceType.jsp";
    }

    /**
     * 문자열 앞뒤 공백을 제거한다.
     * 文字列の前後の空白を削除する。
     */
    private String trim(String value) {
        return value == null ? "" : value.trim();
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
