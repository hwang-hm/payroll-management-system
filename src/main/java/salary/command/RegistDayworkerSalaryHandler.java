package salary.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.service.RegistDayworkerSalaryService;
import salary.service.request.RegistSalaryRequest;
import salary.service.request.RegistSalaryRequest.SalaryDetailRequest;

public class RegistDayworkerSalaryHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/salary/registDayworkerSalaryForm.jsp";
	private RegistDayworkerSalaryService registService = new RegistDayworkerSalaryService();

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

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		RegistSalaryRequest registReq = createRegistRequest(req);
		registReq.validate(errors);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		int empNo = registService.regist(registReq);
		req.setAttribute("empNo", empNo);

		// 일용직 급여 등록 화면으로 Redirect 실행
		String redirectUrl = req.getContextPath() + "/salary/dayworker/register.do";
		res.sendRedirect(redirectUrl);

		return null;
	}

	private RegistSalaryRequest createRegistRequest(HttpServletRequest req) {
		RegistSalaryRequest registReq = new RegistSalaryRequest();

		// 1. 헤더 기본 정보
		registReq.setEmpId(parseLong(req.getParameter("empId")));
		registReq.setPayYear(parseInt(req.getParameter("belongYear")));
		registReq.setPayMonth(parseInt(req.getParameter("belongMonth")));
		registReq.setPaySeq(parseInt(req.getParameter("payDegree")));
		registReq.setStartDate(req.getParameter("startDate"));
		registReq.setEndDate(req.getParameter("endDate"));
		registReq.setPayDate(req.getParameter("payDate"));

		// 2. 총액 정보
		registReq.setTotalPay(parseLong(req.getParameter("totalPay")));
		registReq.setTotalDeduct(parseLong(req.getParameter("totalDeduct")));
		registReq.setNetPay(parseLong(req.getParameter("netPay")));

		// 3. 동적 상세 항목 (배열 파라미터)
		String[] itemIds = req.getParameterValues("salaryItemId");
		String[] itemTypes = req.getParameterValues("itemType");
		String[] itemNames = req.getParameterValues("itemName");
		String[] taxYns = req.getParameterValues("taxYn");
		String[] amounts = req.getParameterValues("amount");
		String[] memos = req.getParameterValues("memo");

		if (itemNames != null) {
			List<SalaryDetailRequest> details = new ArrayList<>();
			for (int i = 0; i < itemNames.length; i++) {
				SalaryDetailRequest detail = new SalaryDetailRequest();
				if (itemIds != null && i < itemIds.length && !itemIds[i].isEmpty()) {
					detail.setSalaryItemId(parseLong(itemIds[i]));
				}
				detail.setItemType(itemTypes != null ? itemTypes[i] : "PAY");
				detail.setItemName(itemNames[i]);
				detail.setTaxYn(taxYns != null ? taxYns[i] : "Y");
				detail.setAmount(amounts != null ? parseLong(amounts[i]) : 0L);
				detail.setMemo(memos != null && i < memos.length ? memos[i] : "");
				details.add(detail);
			}
			registReq.setDetails(details);
		}

		return registReq;
	}

	private Long parseLong(String val) {
		if (val == null || val.trim().isEmpty())
			return 0L;
		try {
			return Long.parseLong(val.replaceAll(",", ""));
		} catch (NumberFormatException e) {
			return 0L;
		}
	}

	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return 0;
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
