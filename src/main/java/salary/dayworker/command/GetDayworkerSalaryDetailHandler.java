package salary.dayworker.command;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import salary.dayworker.dao.SalaryDao;
import salary.dayworker.dao.SalaryDateDao;
import salary.dayworker.dao.SalaryDetailDao;
import salary.dayworker.dao.SalaryItemDao;
import salary.model.Salary;
import salary.model.SalaryDate;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;

public class GetDayworkerSalaryDetailHandler implements CommandHandler {

	private SalaryDateDao salaryDateDao = new SalaryDateDao();
	private SalaryDao salaryDao = new SalaryDao();
	private SalaryDetailDao salaryDetailDao = new SalaryDetailDao();
	private SalaryItemDao salaryItemDao = new SalaryItemDao();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();

			long empId = Long.parseLong(req.getParameter("empId"));
			int year = Integer.parseInt(req.getParameter("payYear"));
			int month = Integer.parseInt(req.getParameter("payMonth"));
			int seq = Integer.parseInt(req.getParameter("paySeq"));

			SalaryDate salaryDate = salaryDateDao.selectByYearMonthSeq(conn, year, month, seq);
			Salary salary = null;
			if (salaryDate != null) {
				salary = salaryDao.selectByDateAndEmp(conn, salaryDate.getSalaryDateId(), empId);
			}

			StringBuilder json = new StringBuilder();
			json.append("{");

			if (salary != null) {
				// 1. 기존에 저장된 salary_detail 데이터가 있는 경우
				List<SalaryDetail> details = salaryDetailDao.selectBySalaryId(conn, salary.getSalaryId());
				json.append("\"isSaved\": true,");
				json.append("\"totalPay\": ").append(salary.getTotalPay()).append(",");
				json.append("\"totalDeduct\": ").append(salary.getTotalDeduct()).append(",");
				json.append("\"netPay\": ").append(salary.getNetPay()).append(",");
				json.append("\"items\": [");
				for (int i = 0; i < details.size(); i++) {
					SalaryDetail d = details.get(i);
					json.append("{").append("\"salaryItemId\": ").append(d.getSalaryItemId()).append(",")
							.append("\"itemType\": \"").append(d.getItemType()).append("\",").append("\"itemName\": \"")
							.append(d.getItemName()).append("\",").append("\"taxYn\": \"").append(d.getTaxYn())
							.append("\",").append("\"amount\": ").append(d.getAmount()).append(",")
							.append("\"memo\": \"").append(d.getMemo() != null ? d.getMemo() : "").append("\"")
							.append("}").append(i < details.size() - 1 ? "," : "");
				}
				json.append("]");
			} else {
				// 2. 저장된 데이터가 없는 경우 -> salary_item의 active 공제 항목 반환
				List<SalaryItem> activeItems = salaryItemDao.selectActiveDeductionItems(conn);
				json.append("\"isSaved\": false,");
				json.append("\"items\": [");
				for (int i = 0; i < activeItems.size(); i++) {
					SalaryItem item = activeItems.get(i);
					json.append("{").append("\"salaryItemId\": ").append(item.getSalaryItemId()).append(",")
							.append("\"itemType\": \"DEDUCT\",").append("\"itemName\": \"").append(item.getItemName())
							.append("\",").append("\"taxYn\": \"").append(item.getTaxYn()).append("\",")
							.append("\"amount\": 0,").append("\"memo\": \"\"").append("}")
							.append(i < activeItems.size() - 1 ? "," : "");
				}
				json.append("]");
			}

			json.append("}");
			out.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"error\": \"" + e.getMessage() + "\"}");
		} finally {
			JdbcUtil.close(conn);
		}

		return null;
	}
}