package salary.dayworker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attendance.model.DailyWork;
import attendance.service.DailyWorkService;
import mvc.command.CommandHandler;

/**
 * 사원별 월 근무 내역 조회 핸들러 (AJAX / Include용)
 * 社員別月間勤務履歴照会ハンドラー (AJAX / Include用)
 */
public class GetWorkLogHandler implements CommandHandler {

	// 비즈니스 로직 처리를 위한 서비스 객체 생성 / ビジネスロジック処理のためのサービスオブジェクト生成
	private DailyWorkService dailyWorkService = new DailyWorkService();

	// 응답할 JSP 조각 뷰 경로 / レスポンスするJSPスニペットビューのパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/components/_workLogList.jsp";

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		try {
			String empIdStr = req.getParameter("empId");
			String yearStr = req.getParameter("payYear");
			String monthStr = req.getParameter("payMonth");

			// 1. 필수 파라미터 누락 및 빈 값 검증 / 必須パラメータの漏れおよび空文字の検証
			if (isEmpty(empIdStr) || isEmpty(yearStr) || isEmpty(monthStr)) {
				return FORM_VIEW;
			}

			int empId = Integer.parseInt(empIdStr);
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);

			// 2. 서비스 호출: 해당 사원의 월 근무 내역 조회
			// 2. サービス呼び出し: 該当社員の月間勤務履歴の照会
			List<DailyWork> workLogList = dailyWorkService.getMonthlyWorks(empId, year, month);

			// 3. Request 영역에 근무 내역 및 근무 일수 저장
			// 3. Request 領域に勤務履歴および勤務日数データの格納
			req.setAttribute("workLogList", workLogList);
			req.setAttribute("workDaysCount", workLogList != null ? workLogList.size() : 0);

		} catch (NumberFormatException e) {
			System.err.println("[GetWorkLogHandler] パラメータ変換エラー: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("[GetWorkLogHandler] 勤務履歴照会中にエラー発生");
			e.printStackTrace();
		}

		return FORM_VIEW;
	}

	// 파라미터 빈값 검증 유틸 메서드 / パラメータ空文字検証ユーティリティメソッド
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}