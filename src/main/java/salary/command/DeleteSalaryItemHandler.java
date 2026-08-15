package salary.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.service.DeleteSalaryItemService;

public class DeleteSalaryItemHandler implements CommandHandler {

	private DeleteSalaryItemService deleteService = new DeleteSalaryItemService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!req.getMethod().equalsIgnoreCase("POST")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		String salaryItemIdStr = req.getParameter("salaryItemId");

		if (salaryItemIdStr != null && !salaryItemIdStr.trim().isEmpty()) {
			try {
				int salaryItemId = Integer.parseInt(salaryItemIdStr);
				deleteService.deleteItem(salaryItemId);
			} catch (NumberFormatException e) {
				// ID 파싱 실패 시 예외 처리
				e.printStackTrace();
			}
		}

		// 삭제 성공 후 최신 목록 조회를 위해 GET URL로 리다이렉트
		res.sendRedirect(req.getContextPath() + "/salary/item/setting.do");
		return null;
	}
}