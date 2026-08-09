package salary.command;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		try {
			RegistSalaryRequest registReq = createRegistRequest(req);
			registReq.validate(errors);

			if (!errors.isEmpty()) {
				out.print("{\"status\":\"fail\", \"message\":\"필수 입력값이 누락되었거나 유효하지 않습니다.\"}");
				return null;
			}

			System.out.println("============== [DEBUG 4] Service.regist() 호출 직전 ==============");
			registService.regist(registReq);
			System.out.println("============== [DEBUG 5] Service.regist() 정상 완료! ==============");
			out.print("{\"status\":\"success\", \"message\":\"일용직 급여 내역이 성공적으로 저장되었습니다.\"}");

		} catch (Exception e) {
			System.out.println("❌❌❌ [DEBUG ERROR] Handler에서 예외 터짐! ❌❌❌");
			e.printStackTrace(); // 콘솔에 상세 에러 출력
			// 클라이언트에 상세 예외 메시지 반환
			String errMsg = e.getMessage() != null ? e.getMessage().replace("\"", "'") : "알 수 없는 서버 오류";
			out.print("{\"status\":\"error\", \"message\":\"저장 실패: " + errMsg + "\"}");
		}

		return null;
	}

	private RegistSalaryRequest createRegistRequest(HttpServletRequest req) {
		RegistSalaryRequest registReq = new RegistSalaryRequest();

		// 1. 헤더 기본 정보 (parseLongNull: ID용, parseLongAmount: 금액용)
		Long empId = parseLongNull(req.getParameter("empId"));
		registReq.setEmpId(empId != null ? empId : 0L);
		registReq.setPayYear(parseInt(req.getParameter("belongYear")));
		registReq.setPayMonth(parseInt(req.getParameter("belongMonth")));
		registReq.setPaySeq(parseInt(req.getParameter("payDegree")));

		registReq.setStartDate(parseDate(req.getParameter("startDate")));
		registReq.setEndDate(parseDate(req.getParameter("endDate")));
		registReq.setPayDate(parseDate(req.getParameter("payDate")));

		// 2. 총액 정보
		registReq.setTotalPay(parseLongAmount(req.getParameter("totalPay")));
		registReq.setTotalDeduct(parseLongAmount(req.getParameter("totalDeduct")));
		registReq.setNetPay(parseLongAmount(req.getParameter("netPay")));

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

				// ID는 빈 값일 때 0L이 아닌 null이 들어가야 DB FK 오류가 나지 않음
				if (itemIds != null && i < itemIds.length) {
					detail.setSalaryItemId(parseLongNull(itemIds[i]));
				}

				detail.setItemType(itemTypes != null && i < itemTypes.length ? itemTypes[i] : "PAY");
				detail.setItemName(itemNames[i]);
				detail.setTaxYn(taxYns != null && i < taxYns.length ? taxYns[i] : "Y");
				detail.setAmount(amounts != null && i < amounts.length ? parseLongAmount(amounts[i]) : 0L);
				detail.setMemo(memos != null && i < memos.length ? memos[i] : "");

				details.add(detail);
			}
			registReq.setDetails(details);
		}

		return registReq;
	}

	// ID 파싱용 (빈 값 시 null 반환)
	private Long parseLongNull(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			return Long.parseLong(val.replaceAll(",", "").trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// 금액 파싱용 (빈 값 시 0L 반환)
	private long parseLongAmount(String val) {
		Long result = parseLongNull(val);
		return result != null ? result : 0L;
	}

	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return 0;
		try {
			return Integer.parseInt(val.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private Date parseDate(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(val.trim());
		} catch (Exception e) {
			return null;
		}
	}
}