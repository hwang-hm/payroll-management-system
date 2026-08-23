package salary.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.AttendanceType;
import attendance.service.AttendanceTypeService;
import mvc.command.CommandHandler;
import salary.model.SalaryItem;
import salary.service.GetSalaryItemService;
import salary.service.RegistSalaryItemService;
import salary.service.request.RegistItemRequest;

/**
 * 급여 항목 등록 및 수정 요청 처리 커맨드 핸들러
 * 給与項目登録および修正リクエスト処理コマンドハンドラー
 */
public class RegistSalaryItemHandler implements CommandHandler {
	// 급여 항목 설정 화면 JSP 경로
	// 給与項目設定画面JSPパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/salaryItemSetting.jsp";

	// 서비스 객체 생성
	// サービスオブジェクトの生成
	private GetSalaryItemService getService = new GetSalaryItemService();
	private RegistSalaryItemService registService = new RegistSalaryItemService();
	private AttendanceTypeService attendanceService = new AttendanceTypeService();

	/**
	 * HTTP 요청 방식(GET/POST)에 따른 분기 처리
	 * HTTPリクエスト方式(GET/POST)に応じた分岐処理
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			// GET/POST 이외의 요청 방식은 405 Method Not Allowed 응답
			// GET/POST以外のリクエスト方式は405 Method Not Allowedレスポンス
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	/**
	 * GET 요청 처리: 급여 항목 설정 화면 이동 및 기존 목록 데이터 조회
	 * GETリクエスト処理：給与項目設定画面への移動および既存リストデータの照会
	 */
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		// 지급 항목, 공제 항목, 근태 유형 목록 조회
		// 支給項目、控除項目、勤怠タイプ一覧の照会
		List<SalaryItem> payList = getService.getItemsByType("PAY");
		List<SalaryItem> deductList = getService.getItemsByType("DEDUCT");
		List<AttendanceType> attendanceList = attendanceService.getAttendanceTypes();

		// Request 속성에 조회 데이터 저장
		// Request属性に照会データを設定
		req.setAttribute("payList", payList);
		req.setAttribute("deductList", deductList);
		req.setAttribute("attendanceList", attendanceList);

		return FORM_VIEW;
	}

	/**
	 * POST 요청 처리: 급여 항목 등록/수정 폼 제출 처리 (유효성 검증 및 저장)
	 * POSTリクエスト処理：給与項目登録/修正フォーム送信処理（バリデーションおよび保存）
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		// 폼 파라미터 기반으로 Request DTO 생성 및 유효성 검증
		// フォームパラメータを基にRequest DTO生成およびバリデーション
		RegistItemRequest itemReq = createItemRequest(req);
		itemReq.validate(errors);

		// 유효성 검증 에러 존재 시 입력 폼 화면으로 다시 이동
		// バリデーションエラー存在時、入力フォーム画面へ戻る
		if (!errors.isEmpty()) {
			return processForm(req, res);
		}

		// 급여 항목 정보 저장/수정
		// 給与項目情報の保存/更新
		registService.saveOrUpdate(itemReq);

		// PRG(Post-Redirect-Get) 패턴 적용: 저장 완료 후 GET 조회 URL로 리다이렉트
		// PRG(Post-Redirect-Get)パターンの適用：保存完了後、GET照会URLへリダイレクト
		res.sendRedirect(req.getContextPath() + "/config/salaryItem.do");

		return null;
	}

	/**
	 * HttpServletRequest에서 폼 파라미터를 수집하여 RegistItemRequest DTO 객체 생성
	 * HttpServletRequestからフォームパラメータを収集してRegistItemRequest DTOオブジェクトを生成
	 */
	private RegistItemRequest createItemRequest(HttpServletRequest req) {
		String itemType = req.getParameter("itemType");

		// 공제항목(DEDUCT)인 경우 비과세 여부를 'N'으로 고정, 지급항목인 경우 파라미터 값 사용
		// 控除項目(DEDUCT)の場合は非課税有無を'N'に固定、支給項目の場合はパラメータ値を使用
		String taxYn = "DEDUCT".equals(itemType) ? "N" : req.getParameter("taxYn");

		return new RegistItemRequest(
				parseInt(req.getParameter("salaryItemId")),   // 급여항목 ID / 給与項目ID
				parseInt(req.getParameter("attendanceTypeId")), // 연동 근태유형 ID / 連動勤怠タイプID
				itemType,                                       // 항목 유형(PAY/DEDUCT) / 項目タイプ
				req.getParameter("itemName"),                   // 항목명 / 項目名
				taxYn,                                          // 과세 여부(Y/N) / 課税有無
				req.getParameter("taxFreeName"),                // 비과세 감면명 / 非課税減免名
				parseLong(req.getParameter("taxFreeLimit")),    // 비과세 한도액 / 非課税限度額
				req.getParameter("calcMethod"),                 // 계산 방식 / 計算方式
				req.getParameter("roundUnit"),                  // 절사/반올림 단위 / 端数処理単位
				parseLong(req.getParameter("defaultAmount")),   // 기본 금액 / 基本金額
				req.getParameter("remark"),                     // 비고 / 備考
				req.getParameter("useYn")                       // 사용 여부(Y/N) / 使用有無
		);
	}

	/**
	 * String -> Integer 안전 변환
	 * null, 빈값(""), 공백(" "), 숫자가 아닌 문자(천단위 쉼표 등) 포함 시 안전하게 null 반환
	 * 
	 * String -> Integer 安全変換
	 * null、空文字("")、空白(" ")、数値以外の文字(カンマ等)が含まれる場合は安全にnullを返却
	 */
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			// "200,000" 처럼 쉼표가 들어올 경우를 대비해 숫자와 음수 기호(-) 외 정제
			// "200,000"のようなカンマ入力に対応するため数値と負号(-)以外を شه去
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;

			return Integer.parseInt(cleanVal);
		} catch (NumberFormatException e) {
			return null; // 파싱 실패 시 예외를 던지지 않고 안전하게 null 반환 / パース失敗時は例外をスローせず安全にnullを返却
		}
	}

	/**
	 * String -> Long 안전 변환
	 * null, 빈값(""), 공백(" "), 숫자가 아닌 문자(천단위 쉼표 등) 포함 시 안전하게 null 반환
	 * 
	 * String -> Long 安全変換
	 * null、空文字("")、空白(" ")、数値以外の文字(カンマ等)が含まれる場合は安全にnullを返却
	 */
	private Long parseLong(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			// "200,000" 처럼 쉼표가 들어올 경우를 대비해 숫자와 음수 기호(-) 외 정제
			// "200,000"のようなカンマ入力に対応するため数値と負号(-)以外を شه去
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;

			return Long.parseLong(cleanVal);
		} catch (NumberFormatException e) {
			return null; // 파싱 실패 시 예외를 던지지 않고 안전하게 null 반환 / パース失敗時は例外をスローせず安全にnull를返却
		}
	}

}