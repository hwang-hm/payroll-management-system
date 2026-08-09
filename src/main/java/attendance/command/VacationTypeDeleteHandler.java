package attendance.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.VacationType;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

/**
 * 휴가항목 삭제 요청을 처리하는 Handler
 * 休暇項目の削除リクエストを処理するHandler
 */
public class VacationTypeDeleteHandler implements CommandHandler {

	private final VacationTypeService vacationTypeService = new VacationTypeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/vacation/type/list.do";
		}

		try {
			int vacationTypeId = Integer.parseInt(req.getParameter("vacationTypeId"));
			if (!vacationTypeService.removeVacationType(vacationTypeId)) {
				return showError(req, "削除する休暇項目が見つかりません。");
			}
			return "redirect:/vacation/type/list.do?result=deleted";
		} catch (NumberFormatException e) {
			return showError(req, "削除する休暇項目を選択してください。");
		} catch (RuntimeException e) {
			// 다른 테이블에서 참조 중인 항목은 삭제하지 않고 사용자에게 안내한다.
			// 他のテーブルから参照されている項目は削除せず、利用者に案内する。
			if (isForeignKeyViolation(e)) {
				return showError(req, "使用中の休暇項目は削除できません。");
			}
			throw e;
		}
	}

	private boolean isForeignKeyViolation(Throwable throwable) {
		Throwable cause = throwable;
		while (cause != null) {
			if (cause instanceof SQLException && ((SQLException) cause).getErrorCode() == 2292) {
				return true;
			}
			cause = cause.getCause();
		}
		return false;
	}

	private String showError(HttpServletRequest req, String message) {
		List<VacationType> vacationTypes = vacationTypeService.getVacationTypes();
		req.setAttribute("vacationTypes", vacationTypes);
		req.setAttribute("errorMessage", message);
		return "/view/config/vacationType.jsp";
	}
}
