package salary.entry.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import salary.entry.service.SalaryDateService;
import salary.entry.service.SalaryDeleteService;
import salary.model.SalaryDate;
import salary.util.PayPeriodParams;

// 급여입력에서 선택한 사원 급여 삭제
public class SalaryDeleteHandler implements CommandHandler {

	private final SalaryDateService salaryDateService = new SalaryDateService();
	private final SalaryDeleteService deleteService = new SalaryDeleteService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		PayPeriodParams period = PayPeriodParams.requiredFromRequest(req);
		String[] salaryIdArr = req.getParameterValues("salaryIds");
		List<Integer> salaryIds = new ArrayList<>();
		if (salaryIdArr != null) {
			for (String salaryId : salaryIdArr) {
				salaryIds.add(Integer.parseInt(salaryId));
			}
		}

		int salaryDateId = resolveSalaryDateId(period);
		deleteService.deleteSelected(salaryDateId, salaryIds);

		return "redirect:/salary/entry.do?" + period.toQueryString();
	}

	private int resolveSalaryDateId(PayPeriodParams period) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			SalaryDate salaryDate = salaryDateService.ensureByPeriod(conn, period.getPayYear(), period.getPayMonth(),
					period.getPaySeq());
			return salaryDate.getSalaryDateId();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
