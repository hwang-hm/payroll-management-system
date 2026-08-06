package salary.command;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.model.SalaryDate;
import salary.service.SaveSalaryDateService;

public class SaveSalaryDateHandler implements CommandHandler{
	
	private SaveSalaryDateService saveService = new SaveSalaryDateService();

	@Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!req.getMethod().equalsIgnoreCase("POST")) {
            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return null;
        }

        res.setContentType("application/json; charset=UTF-8");
        PrintWriter out = res.getWriter();

        try {
            // 1. 파라미터 수집
            String yearStr = req.getParameter("belongYear");
            String monthStr = req.getParameter("belongMonth");
            String degreeStr = req.getParameter("payDegree");
            String startDateStr = req.getParameter("startDate");
            String endDateStr = req.getParameter("endDate");
            String payDateStr = req.getParameter("payDate");

            // 2. 유효성 검증
            if (isEmpty(yearStr) || isEmpty(monthStr) || isEmpty(degreeStr) 
                    || isEmpty(startDateStr) || isEmpty(endDateStr) || isEmpty(payDateStr)) {
                out.print("{\"status\":\"error\", \"message\":\"모든 필드(귀속연월, 차수, 정산기간, 지급일)를 입력해주세요.\"}");
                return null;
            }

            // 3. 데이터 변환
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int degree = Integer.parseInt(degreeStr);
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            Date payDate = sdf.parse(payDateStr);

            SalaryDate salaryDate = new SalaryDate(year, month, degree, startDate, endDate, payDate);

            // 4. 서비스 호출
            boolean isSuccess = saveService.saveOrUpdate(salaryDate);

            if (isSuccess) {
                out.print("{\"status\":\"success\", \"message\":\"정산기간 및 급여지급일이 정상적으로 저장되었습니다.\"}");
            } else {
                out.print("{\"status\":\"fail\", \"message\":\"저장에 실패했습니다.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\", \"message\":\"서버 오류: " + e.getMessage() + "\"}");
        }

        return null; // AJAX 응답이므로 View 경로를 리턴하지 않음
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
