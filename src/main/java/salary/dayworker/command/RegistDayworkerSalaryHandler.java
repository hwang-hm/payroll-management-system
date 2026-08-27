package salary.dayworker.command;

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
import salary.dayworker.service.RegistDayworkerSalaryService;
import salary.dayworker.service.request.RegistSalaryRequest;
import salary.dayworker.service.request.RegistSalaryRequest.SalaryDetailRequest;

/**
 * 일용직 급여 등록/수정 요청 처리 핸들러 (GET: 폼 뷰 이동 / POST: AJAX 저장)
 * 日雇い給与登録/修正リクエスト処理ハンドラー (GET: フォームビュー移動 / POST: AJAX保存)
 */
public class RegistDayworkerSalaryHandler implements CommandHandler {

	// 급여 등록 폼 JSP 경로 / 給与登録フォームJSPのパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/registDayworkerSalaryForm.jsp";

	// 급여 등록 비즈니스 서비스 / 給与登録ビジネスサービス
	private RegistDayworkerSalaryService registService = new RegistDayworkerSalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. 요청 방식(HTTP Method)에 따른 분기 처리
		// 1. リクエスト方式(HTTP Method)に応じた分岐処理
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			// 허용되지 않은 HTTP 메서드 처리 / 許可されていないHTTPメソッドの処理
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	// GET 요청: 등록 화면 포워딩 / GETリクエスト: 登録画面フォワーディング
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	// POST 요청: 급여 데이터 저장 처리 (JSON 응답)
	// POSTリクエスト: 給与データ保存処理 (JSONレスポンス)
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		try {
			// 1. HTTP 요청 파라미터를 Request DTO 객체로 수집
			// 1. HTTPリクエストパラメータをRequest DTOオブジェクトへ収集
			RegistSalaryRequest registReq = createRegistRequest(req);

			// 2. 유효성 검증 실행 / 2. バリデーション検証の実行
			registReq.validate(errors);

			// 검증 실패 시 오류 JSON 반환 / 検証失敗時にエラーJSONを返却
			if (!errors.isEmpty()) {
				out.print("{\"status\":\"fail\", \"message\":\"必須入力項目が漏れているか無効です。\"}");
				return null;
			}

			// 3. 급여 등록 서비스 호출 / 3. 給与登録サービスの呼び出し
			registService.regist(registReq);
			out.print("{\"status\":\"success\", \"message\":\"日雇い給与明細が正常に保存されました。\"}");

		} catch (Exception e) {
			e.printStackTrace(); // 콘솔 에러 로깅 / コンソールエラーログ出力

			// 예외 발생 시 서버 에러 JSON 반환 / 例外発生時にサーバーエラーJSONを返却
			String errMsg = e.getMessage() != null ? e.getMessage().replace("\"", "'") : "알 수 없는 서버 오류 / 不明なサーバーエラー";
			out.print("{\"status\":\"error\", \"message\":\"保存失敗: " + errMsg + "\"}");
		}

		return null;
	}

	// HTTP 파라미터를 RegistSalaryRequest 객체로 매핑하는 유틸 메서드
	// HTTPパラメータをRegistSalaryRequestオブジェクトへマッピングするユーティリティメソッド
	private RegistSalaryRequest createRegistRequest(HttpServletRequest req) {
		RegistSalaryRequest registReq = new RegistSalaryRequest();

		// 1. 정산 및 사원 기본 정보 파싱
		// 1. 精算および社員基本情報のパース
		Integer empId = parseIntNull(req.getParameter("empId"));
		registReq.setEmpId(empId != null ? empId : 0);
		registReq.setPayYear(parseInt(req.getParameter("payYear")));
		registReq.setPayMonth(parseInt(req.getParameter("payMonth")));
		registReq.setPaySeq(parseInt(req.getParameter("paySeq")));

		registReq.setStartDate(parseDate(req.getParameter("startDate")));
		registReq.setEndDate(parseDate(req.getParameter("endDate")));
		registReq.setPayDate(parseDate(req.getParameter("payDate")));

		// 2. 급여 총액 정보 파싱 (천단위 콤마 제거 포함)
		// 2. 給与総額情報のパース (千位のカンマ除去を含む)
		registReq.setTotalPay(parseLongAmount(req.getParameter("totalPay")));
		registReq.setTotalDeduct(parseLongAmount(req.getParameter("totalDeduct")));
		registReq.setNetPay(parseLongAmount(req.getParameter("netPay")));

		// 3. 동적 급여/공제 상세 항목 배열 파싱
		// 3. 動的給与/控除詳細項目配列のパース
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
				// IDは空文字の際、0LではなくnullをセットしてDB FKエラーを防止
				if (itemIds != null && i < itemIds.length) {
					detail.setSalaryItemId(parseIntNull(itemIds[i]));
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

	// 정수 파싱 유틸 (빈 값일 경우 null 반환) / 정수パースユーティリティ (空文字の場合null返却)
	private Integer parseIntNull(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			return Integer.parseInt(val.replaceAll(",", "").trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// Long 파싱 유틸 (빈 값일 경우 null 반환) / Longパースユーティリティ (空文字の場合null返却)
	private Long parseLongNull(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			return Long.parseLong(val.replaceAll(",", "").trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// 금액 파싱 유틸 (빈 값일 경우 0L 반환) / 金額パースユーティリティ (空文字の場合0L返却)
	private long parseLongAmount(String val) {
		Long result = parseLongNull(val);
		return result != null ? result : 0L;
	}

	// 기본 정수 파싱 / 基本整数パース
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return 0;
		try {
			return Integer.parseInt(val.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	// 날짜 문자열(yyyy-MM-dd) Date 객체 파싱 유틸 / 日付文字列(yyyy-MM-dd) Dateオブジェクトパースユーティリティ
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