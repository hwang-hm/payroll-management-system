package salary.dayworker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.model.Dayworker;
import salary.dayworker.service.ListEmployeeService;

/**
 * 사원 검색 모달용 목록 조회 핸들러 (키워드 검색 지원)
 * 社員検索モーダル用リスト照会ハンドラー (キーワード検索対応)
 */
public class ListEmployeeHandler implements CommandHandler {

	// 사원 검색 모달 JSP 조각 뷰 경로 / 社員検索モーダルJSPスニペットのパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/components/empSearchModal.jsp";

	// 사원 목록 조회 서비스 객체 / 社員リスト照会サービスオブジェクト
	private ListEmployeeService listService = new ListEmployeeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. HTTP Method 검증 (GET 요청만 허용)
		// 1. HTTP Method 検証 (GETリクエストのみ許可)
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		try {
			// 2. 검색 키워드 파라미터 수신 (사원명 또는 사번)
			// 2. 検索キーワードパラメータの取得 (社員名または社員番号)
			String keyword = req.getParameter("keyword");

			// 3. 서비스 호출: DB에서 전체 또는 키워드 조건 사원 목록 조회
			// 3. サービス呼び出し: DBから全体またはキーワード条件の社員リスト照会
			List<Dayworker> workerList = listService.getEmpList(keyword);

			// 4. View(JSP) 전달을 위해 Request 영역에 데이터 저장
			// 4. View(JSP)へ伝達するためRequest領域にデータ格納
			req.setAttribute("empList", workerList);

		} catch (Exception e) {
			System.err.println("[ListEmployeeHandler] 사원 목록 조회 중 오류 발생 / 社員リスト照会中にエラー発生");
			e.printStackTrace();
		}

		// 5. 모달 HTML 조각 JSP로 포워딩 / 5. モーダルHTMLスニペットJSPへフォワーディング
		return FORM_VIEW;
	}
}