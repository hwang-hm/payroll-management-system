package salary.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.model.SalaryItem;
import salary.service.GetSalaryItemService;
import salary.service.RegistSalaryItemService;
import salary.service.request.RegistItemRequest;

public class RegistSalaryItemHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/salary/salaryItemSetting.jsp";

	private GetSalaryItemService getService = new GetSalaryItemService();
	private RegistSalaryItemService registService = new RegistSalaryItemService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	// 화면 이동 및 기존 목록 데이터 조회
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		List<SalaryItem> payList = getService.getItemsByType("PAY");
		List<SalaryItem> deductList = getService.getItemsByType("DEDUCT");

		req.setAttribute("payList", payList);
		req.setAttribute("deductList", deductList);

		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		RegistItemRequest itemReq = createItemRequest(req);
		itemReq.validate(errors);

		if (!errors.isEmpty()) {
			return processForm(req, res);
		}

		// 저장
		registService.saveOrUpdate(itemReq);

		// GET 조회 핸들러 URL로 리다이렉트
		res.sendRedirect(req.getContextPath() + "/salary/item/setting.do");

		return null;

	}

	private RegistItemRequest createItemRequest(HttpServletRequest req) {
		String itemType = req.getParameter("itemType");

		// 공제항목(DEDUCT)인 경우 비과세로 설정
		String taxYn = "DEDUCT".equals(itemType) ? "N" : req.getParameter("taxYn");

		return new RegistItemRequest(parseInt(req.getParameter("salaryItemId")), // Integer
				parseInt(req.getParameter("attendanceTypeId")), // Integer
				itemType, // String
				req.getParameter("itemName"), // String
				taxYn, // String
				req.getParameter("taxFreeName"), // String
				parseLong(req.getParameter("taxFreeLimit")), // Long
				req.getParameter("calcMethod"), // String
				req.getParameter("roundUnit"), // String
				parseLong(req.getParameter("defaultAmount")), // Long (일괄지급액/기본금액)
				req.getParameter("remark"), // String
				req.getParameter("useYn") // String
		);
	}

	/**
	 * String -> Integer 안전 변환 null, 빈값(""), 공백(" "), 숫자가 아닌 문자 포함 시 null 반환
	 */
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			// "200,000" 처럼 쉼표가 들어올 경우 대비하여 숫자가 아닌 문자 제거
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;

			return Integer.parseInt(cleanVal);
		} catch (NumberFormatException e) {
			return null; // 파싱 실패 시 예외를 던지지 않고 안전하게 null 반환
		}
	}

	/**
	 * String -> Long 안전 변환 null, 빈값(""), 공백(" "), 숫자가 아닌 문자 포함 시 null 반환
	 */
	private Long parseLong(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			// "200,000" 처럼 쉼표가 들어올 경우 대비하여 숫자가 아닌 문자 제거
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;

			return Long.parseLong(cleanVal);
		} catch (NumberFormatException e) {
			return null; // 파싱 실패 시 예외를 던지지 않고 안전하게 null 반환
		}
	}

}
