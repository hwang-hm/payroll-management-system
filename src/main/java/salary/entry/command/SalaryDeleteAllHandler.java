package salary.entry.command;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import salary.entry.service.SalaryDateService;
import salary.entry.service.SalaryDeleteService;
import salary.model.SalaryDate;
import salary.util.PayPeriodParams;

// 급여입력에서 이번 귀속년월 급여 전체 삭제
public class SalaryDeleteAllHandler implements CommandHandler {

	private final SalaryDateService salaryDateService = new SalaryDateService();
	private final SalaryDeleteService deleteService = new SalaryDeleteService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			SalaryDate salaryDate = salaryDateService.ensureByPeriod(conn, period.getPayYear(), period.getPayMonth(),
					period.getPaySeq());
			deleteService.deleteAll(salaryDate.getSalaryDateId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}

		return "redirect:/salary/entry.do?" + period.toQueryString();
	}
}
