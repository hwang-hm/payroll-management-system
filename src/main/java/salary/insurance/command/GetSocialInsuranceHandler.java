package salary.insurance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.insurance.service.GetSocialInsuranceService;
import salary.insurance.service.request.GetSocialInsuranceDTO;

/**
 * 4대보험 공제 내역 조회 요청 처리 커맨드 핸들러
 * 4大保険控除明細照会リクエスト処理コマンドハンドラー
 */
public class GetSocialInsuranceHandler implements CommandHandler {
	// Ajax 요청 시 반환할 부분 뷰(JSP) 경로 / Ajaxリクエスト時に返却する部分ビュー(JSP)パス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/insurance/_socialInsuranceRows.jsp";
	private GetSocialInsuranceService service = new GetSocialInsuranceService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// GET 방식 이외의 요청 거부 (405 Method Not Allowed)
		// GET方式以外のリクエストを拒否 (405 Method Not Allowed)
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		// 급여 정산 ID 파라미터 추출
		// 給与計算IDパラメータの抽出
		String salaryDateIdVal = req.getParameter("salaryDateId");

		// 파라미터가 존재할 경우 목록 조회 후 부분 뷰 반환 (Ajax 요청)
		// パラメータが存在する場合、一覧照会後に部分ビューを返却 (Ajaxリクエスト)
		if (salaryDateIdVal != null && !salaryDateIdVal.trim().isEmpty()) {
			int salaryDateId = Integer.parseInt(salaryDateIdVal);
			
			// 4대보험 공제 내역 목록 조회
			// 4大保険控除明細一覧の照会
			List<GetSocialInsuranceDTO> list = service.getSocialInsuranceList(salaryDateId);
			
			// 요청 객체에 결과 데이터 바인딩
			// リクエストオブジェクトに結果データをバインディング
			req.setAttribute("insuranceList", list);
			req.setAttribute("salaryDateId", salaryDateId);

			return FORM_VIEW;
		}

		// 파라미터가 없는 첫 화면 진입 시 메인 JSP 뷰 반환
		// パラメータがない初回画面アクセス時、メインJSPビューを返却
		return "/WEB-INF/view/salary/insurance/socialInsurance.jsp";
	}

}