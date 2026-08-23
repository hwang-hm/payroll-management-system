package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetAllYearlySalaryService;
import salary.statics.service.request.YearlySalaryDTO;

/**
 * 연도별 급여 통계 전체 조회 핸들러
 * 年度別給与統計全体照会ハンドラー
 */
public class GetAllYearlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/yearlyStaticsAll.jsp";

	private GetAllYearlySalaryService yearlySalaryService = new GetAllYearlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// HTTP GET 요청만 허용 (그 외 요청 방식은 405 Method Not Allowed 반환)
		// HTTP GETリクエストのみ許可（その他のリクエスト方式は405 Method Not Allowedを返却）
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	/**
	 * 화면 이동 및 연도별 통계 데이터 조회
	 * 画面遷移および年度別統計データ照会
	 */
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		// 선택된 귀속연도 파라미터 수신 (전달값이 없으면 현재 연도를 기본값으로 설정)
		// 選択された帰属年度パラメーター受信（値がない場合は今年度をデフォルト値として設定）
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		// 기준 연도 포함 10개년 보정 통계 데이터 조회
		// 基準年度を含む10年間の補正統計データ照会
		List<YearlySalaryDTO> statList = yearlySalaryService.get10YearSalaryStats(targetYear);

		// 뷰(JSP) 전달을 위한 Request 속성 저장
		// ビュー(JSP)へ渡すためのRequest属性保存
		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("statList", statList);

		return FORM_VIEW;
	}

	/**
	 * 문자열을 Integer로 안전하게 변환 (null, 빈값, 숫자가 아닌 문자 포함 시 null 반환)
	 * 文字列をIntegerへ安全に変換（null、空文字、数値以外の文字を含む場合はnullを返却）
	 */
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			// 숫자와 음수 기호(-)를 제외한 모든 문자 제거
			// 数字とマイナス記号(-)以外のすべての文字を削除
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;

			return Integer.parseInt(cleanVal);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}