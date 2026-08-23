package salary.dayworker.command;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.service.GetSalaryListService;
import salary.dayworker.service.request.GetSalaryListDTO;

/**
 * 일용직 사원 급여 목록 조회 요청을 처리하는 Command Handler 클래스입니다.
 * 日雇い社員の給与リスト照会リクエストを処理するCommand Handlerクラスです。
 */
public class GetSalaryListHandler implements CommandHandler {

	// 응답으로 포워딩할 비동기(AJAX)용 JSP 조각 파일 경로
	// レスポンスとしてフォワーディングする非同期(AJAX)用JSPスニペットファイルのパス
	private static final String FORM_VIEW = "/WEB-INF/view/salary/dayworker/components/_employeeListRows.jsp";

	// 급여 목록 조회 비즈니스 로직을 수행하는 서비스 객체
	// 給与リスト照会のビジネスロジックを実行するサービスオブジェクト
	private GetSalaryListService listService = new GetSalaryListService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. HTTP 메서드 검증 (GET 요청만 허용)
		// 1. HTTPメソッドの検証 (GETリクエストのみ許可)
		if (!req.getMethod().equalsIgnoreCase("GET")) {
			// GET 방식이 아닌 경우 405 Method Not Allowed 상태 코드 반환
			// GET方式でない場合は 405 Method Not Allowed ステータスコードを返却
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

		// 2. 요청 파라미터 수신 (급여 지급일 ID)
		// 2. リクエストパラメータの取得 (給与支給日ID)
		String salaryDateIdParam = req.getParameter("salary_date_id");

		List<GetSalaryListDTO> salaryList;

		// 3. 파라미터 유효성 검증 및 비즈니스 로직(DB 조회) 수행
		// 3. パラメータの妥当性検証およびビジネスロジック(DB照会)の実行
		if (salaryDateIdParam != null && !salaryDateIdParam.trim().isEmpty()) {
			try {
				Integer salaryDateId = Integer.parseInt(salaryDateIdParam);

				// 파라미터가 정상적인 정수 형태인 경우 DB에서 급여 목록 조회
				// パラメータが正常な整数形式の場合、DBから給与リストを照会
				salaryList = listService.getSalaryList(salaryDateId);
			} catch (NumberFormatException e) {
				// 숫자로 변환할 수 없는 예외 발생 시 빈 리스트 처리하여 시스템 오류 방지
				// 数値に変換できない例外が発生した場合、空のリストとして処理しシステムエラーを防止
				salaryList = Collections.emptyList();
			}
		} else {
			// salary_date_id 파라미터가 미전달된 경우 빈 리스트 설정
			// salary_date_id パラメータが渡されなかった場合は空のリストを設定
			salaryList = Collections.emptyList();
		}

		// 4. JSP에서 참조할 수 있도록 Request scope에 조회 데이터 저장
		// 4. JSPで参照できるようにRequest scopeに照会データを保存
		req.setAttribute("salaryList", salaryList);

		// 5. 뷰(JSP) 경로 반환 및 포워딩 수행
		// 5. ビュー(JSP)パスの返却およびフォワーディングの実行
		return FORM_VIEW;
	}
}