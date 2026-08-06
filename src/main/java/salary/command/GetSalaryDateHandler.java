package salary.command;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.model.SalaryDate;
import salary.service.GetSalaryDateService;

public class GetSalaryDateHandler implements CommandHandler {

	private GetSalaryDateService getService = new GetSalaryDateService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			String yearStr = req.getParameter("belongYear");
			String monthStr = req.getParameter("belongMonth");
			String degreeStr = req.getParameter("payDegree");

			if (isEmpty(yearStr) || isEmpty(monthStr) || isEmpty(degreeStr)) {
				out.print("{\"status\":\"invalid\"}");
				return null;
			}

			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int seq = Integer.parseInt(degreeStr);

			SalaryDate salaryDate = getService.getSalaryDate(year, month, seq);

			if (salaryDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = sdf.format(salaryDate.getStartDate());
				String endDate = sdf.format(salaryDate.getEndDate());
				String payDate = sdf.format(salaryDate.getPayDate());

				out.print(String.format(
						"{\"status\":\"found\", \"startDate\":\"%s\", \"endDate\":\"%s\", \"payDate\":\"%s\"}",
						startDate, endDate, payDate));
			} else {
				// DB에 해당 조건의 데이터가 없는 경우
				out.print("{\"status\":\"notFound\"}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
		}

		return null;
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}