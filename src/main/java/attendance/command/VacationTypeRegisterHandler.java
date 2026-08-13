package attendance.command;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.VacationType;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

/**
 * 휴가항목 등록 요청을 처리하는 Handler
 * 休暇項目の登録リクエストを処理するHandler
 */
public class VacationTypeRegisterHandler implements CommandHandler {

	private final VacationTypeService vacationTypeService = new VacationTypeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!"POST".equalsIgnoreCase(req.getMethod())) {
			return "redirect:/config/vacation-attendance.do#vacation";
		}

		VacationType vacationType = createVacationType(req);
		String validationMessage = validate(vacationType);

		if (validationMessage != null) {
			return showForm(req, vacationType, validationMessage);
		}

		if (!vacationTypeService.addVacationType(vacationType)) {
			return showForm(req, vacationType, "休暇項目を登録できませんでした。");
		}
		return "redirect:/config/vacation-attendance.do?result=vacationRegistered#vacation";
	}

	private VacationType createVacationType(HttpServletRequest req) {
		VacationType vacationType = new VacationType();
		vacationType.setVacationName(trim(req.getParameter("vacationName")));
		vacationType.setApplyStartDate(toDate(req.getParameter("applyStartDate")));
		vacationType.setApplyEndDate(toDate(req.getParameter("applyEndDate")));
		vacationType.setVacationDays(trim(req.getParameter("vacationDays")));
		vacationType.setUseYn("N".equals(req.getParameter("useYn")) ? "N" : "Y");
		return vacationType;
	}

	private String validate(VacationType vacationType) {
		if (vacationType.getVacationName().isEmpty()) {
			return "休暇項目名を入力してください。";
		}
		if (vacationType.getApplyStartDate() == null || vacationType.getApplyEndDate() == null) {
			return "適用期間を入力してください。";
		}
		if (vacationType.getApplyStartDate().after(vacationType.getApplyEndDate())) {
			return "適用開始日は適用終了日以前の日付を入力してください。";
		}
		return null;
	}

	private String showForm(HttpServletRequest req, VacationType vacationType, String message) {
		List<VacationType> vacationTypes = vacationTypeService.getVacationTypes();
		req.setAttribute("vacationTypes", vacationTypes);
		req.setAttribute("formVacationType", vacationType);
		req.setAttribute("errorMessage", message);
		return "/view/config/vacationType.jsp";
	}

	private Date toDate(String value) {
		try {
			return value == null || value.trim().isEmpty() ? null : Date.valueOf(value);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private String trim(String value) {
		return value == null ? "" : value.trim();
	}
}
