package attendance.command;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.Attendance;
import attendance.model.AttendanceType;
import attendance.service.AttendanceService;
import attendance.service.AttendanceTypeService;
import employee.list.service.EmpListData;
import employee.list.service.EmpListPage;
import employee.list.service.EmpListService;
import mvc.command.CommandHandler;

public class AttendanceInquiryHandler implements CommandHandler {

    private final AttendanceService attendanceService = new AttendanceService();
    private final AttendanceTypeService attendanceTypeService = new AttendanceTypeService();
    private final EmpListService empListService = new EmpListService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int pageNo = parsePositiveInt(req.getParameter("pageNo"), 1);
        EmpListPage empListPage = empListService.getEmpPage(pageNo);
        List<AttendanceType> attendanceTypes = attendanceTypeService.getAttendanceTypes();

        LocalDate today = LocalDate.now();
        int selectedYear = parsePositiveInt(req.getParameter("year"), today.getYear());
        int selectedMonth = parseMonth(req.getParameter("month"), today.getMonthValue());

        Calendar cal = Calendar.getInstance();
        cal.set(selectedYear, selectedMonth - 1, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 1일부터 31일까지 각 일자의 요일 정보 계산 (1: 일요일, 7: 토요일)
        Map<Integer, Integer> dayOfWeekMap = new HashMap<>();
        for (int day = 1; day <= 31; day++) {
            if (day <= daysInMonth) {
                cal.set(selectedYear, selectedMonth - 1, day);
                dayOfWeekMap.put(day, cal.get(Calendar.DAY_OF_WEEK));
            } else {
                dayOfWeekMap.put(day, 0);
            }
        }

        Map<Integer, Set<Integer>> empAttendanceDaysMap = new HashMap<>();

        if (empListPage != null && empListPage.getEmpListData() != null) {
            for (EmpListData emp : empListPage.getEmpListData()) {
                int empId = emp.getEmpId();

                List<Attendance> attendances = attendanceService.getMonthlyAttendances(empId, selectedYear, selectedMonth);
                Set<Integer> activeDays = new HashSet<>();

                if (attendances != null) {
                    for (Attendance att : attendances) {
                        LocalDate startDate = convertToLocalDate(att.getAttendanceStartDate());
                        LocalDate endDate = convertToLocalDate(att.getAttendanceEndDate());

                        if (startDate != null) {
                            if (endDate == null) {
                                endDate = startDate;
                            }

                            for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
                                if (d.getYear() == selectedYear && d.getMonthValue() == selectedMonth) {
                                    activeDays.add(d.getDayOfMonth());
                                }
                            }
                        }
                    }
                }
                empAttendanceDaysMap.put(empId, activeDays);
            }
        }

        req.setAttribute("empListPage", empListPage);
        req.setAttribute("attendanceTypes", attendanceTypes);
        req.setAttribute("selectedYear", selectedYear);
        req.setAttribute("selectedMonth", selectedMonth);
        req.setAttribute("daysInMonth", daysInMonth);
        req.setAttribute("dayOfWeekMap", dayOfWeekMap);
        req.setAttribute("empAttendanceDaysMap", empAttendanceDaysMap);

        return "/WEB-INF/view/attendance/attendanceList.jsp";
    }

    private LocalDate convertToLocalDate(Object dateObject) {
        if (dateObject == null) return null;

        if (dateObject instanceof java.sql.Date) {
            return ((java.sql.Date) dateObject).toLocalDate();
        } else if (dateObject instanceof java.util.Date) {
            return ((java.util.Date) dateObject).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
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
}
