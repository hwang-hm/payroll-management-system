package salary.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.service.DeleteSalaryItemService;

/**
 * 급여 항목 삭제 요청 처리 커맨드 핸들러
 * 給与項目削除リクエスト処理コマンドハンドラー
 */
public class DeleteSalaryItemHandler implements CommandHandler {

	// 급여 항목 삭제 서비스 객체 생성
	// 給与項目削除サービスオブジェクトの生成
	private DeleteSalaryItemService deleteService = new DeleteSalaryItemService();

	/**
	 * HTTP POST 요청을 수신하여 급여 항목 삭제 실행 후 리다이렉트
	 * HTTP POSTリクエストを受信して給与項目削除を実行後リダイレクト
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// POST 요청이 아닌 경우 405 Method Not Allowed 응답
		// POSTリクエストでない場合は405 Method Not Allowedレスポンス
		if (!req.getMethod().equalsIgnoreCase("POST")) {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		// 삭제할 급여 항목 ID 파라미터 수집
		// 削除対象の給与項目IDパラメータの取得
		String salaryItemIdStr = req.getParameter("salaryItemId");

		// 파라미터가 존재하고 빈 값이 아닌 경우 삭제 처리 진행
		// パラメータが存在し、空文字でない場合のみ削除処理を実行
		if (salaryItemIdStr != null && !salaryItemIdStr.trim().isEmpty()) {
			try {
				int salaryItemId = Integer.parseInt(salaryItemIdStr);
				// 급여 항목 삭제 서비스 호출
				// 給与項目削除サービスの呼び出し
				deleteService.deleteItem(salaryItemId);
			} catch (NumberFormatException e) {
				// ID 파싱 실패 시 예외 처리 (로그 출력)
				// IDパース失敗時の例外処理（ログ出力）
				e.printStackTrace();
			}
		}

		// 삭제 처리 후 최신 목록 조회를 위해 GET URL로 리다이렉트
		// 削除処理後、最新リスト照会のためGET URLへリダイレクト
		res.sendRedirect(req.getContextPath() + "/salary/item/setting.do");
		return null;
	}
}