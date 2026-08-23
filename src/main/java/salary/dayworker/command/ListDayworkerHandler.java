package salary.dayworker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.model.Dayworker;
import salary.dayworker.service.ListDayworkerService;

/**
 * 일용직 사원 목록 조회 핸들러 (모달 / HTML 조각 용)
 * 日雇い社員リスト照会ハンドラー (モーダル / HTMLスニペット用)
 */
public class ListDayworkerHandler implements CommandHandler {

	// 응답할 JSP 조각 뷰 경로 / レスポンスするJSPスニペットビューのパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/components/_modalWorkerList.jsp";

	// 일용직 목록 조회 서비스 객체 / 日雇いリスト照会サービスオブジェクト
	private ListDayworkerService listService = new ListDayworkerService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. HTTP Method 검증 (GET 요청만 허용)
		// 1. HTTP Method 検証 (GETリクエストのみ許可)
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		try {
			// 2. 서비스 호출: DB에서 일용직 사원 목록 조회
			// 2. サービス呼び出し: DBから日雇い社員リスト照会
			List<Dayworker> workerList = listService.getDayworkerList();

			// 3. Request 영역에 데이터 저장
			// 3. Request 領域にデータ格納
			req.setAttribute("dayWorkerList", workerList);

		} catch (Exception e) {
			System.err.println("[ListDayworkerHandler] 日雇いリスト照会中にエラー発生");
			e.printStackTrace();
		}

		// 4. HTML 조각 JSP로 포워딩 / 4. HTMLスニペットJSPへフォワーディング
		return FORM_VIEW;
	}

}