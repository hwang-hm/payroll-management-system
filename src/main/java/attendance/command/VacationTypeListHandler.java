package attendance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.VacationType;
import attendance.service.VacationTypeService;
import mvc.command.CommandHandler;

public class VacationTypeListHandler implements CommandHandler {

	private final VacationTypeService vacationTypeService = new VacationTypeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		List<VacationType> vacationTypes = vacationTypeService.getVacationTypes();
		req.setAttribute("vacationTypes", vacationTypes);

		// 수정 또는 삭제할 휴가항목을 선택했을 때 상세 정보를 조회한다.
		// 修正または削除する休暇項目を選択した場合、詳細情報を照会する。
		String vacationTypeIdParam = req.getParameter("vacationTypeId");
		if (vacationTypeIdParam != null && !vacationTypeIdParam.trim().isEmpty()) {
			try {
				int vacationTypeId = Integer.parseInt(vacationTypeIdParam);
				VacationType selectedVacationType = vacationTypeService.getVacationType(vacationTypeId);
				req.setAttribute("selectedVacationType", selectedVacationType);
			} catch (NumberFormatException e) {
				req.setAttribute("errorMessage", "正しくない休暇項目番号です。");
			}
		}

		return "/view/config/vacationType.jsp";
	}

}
