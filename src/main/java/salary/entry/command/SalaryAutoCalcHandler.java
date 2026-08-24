package salary.entry.command;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.entry.service.SalaryAutoCalcService;

// 급여입력 상세에서 공제항목 자동계산 (JSON, 화면 새로고침 없음)
// 給与入力詳細で控除項目を自動計算 (JSON、画面再読込なし)
public class SalaryAutoCalcHandler implements CommandHandler {

	private final SalaryAutoCalcService autoCalcService = new SalaryAutoCalcService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			int salaryId = Integer.parseInt(req.getParameter("salaryId"));
			Map<Integer, Long> updated = autoCalcService.autoCalc(salaryId);

			StringBuilder json = new StringBuilder();
			json.append("{\"ok\":true,\"details\":[");
			boolean first = true;
			for (Map.Entry<Integer, Long> entry : updated.entrySet()) {
				if (!first) {
					json.append(',');
				}
				first = false;
				json.append("{\"detailId\":").append(entry.getKey())
						.append(",\"amount\":").append(entry.getValue()).append('}');
			}
			json.append("]}");
			out.print(json.toString());
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"ok\":false}");
		}
		out.flush();
		return null;
	}
}
