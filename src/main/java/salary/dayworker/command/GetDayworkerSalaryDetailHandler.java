package salary.dayworker.command;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.service.GetDayworkerSalaryDetailService;
import salary.dayworker.service.request.SalaryDetailResult;
import salary.model.Salary;
import salary.model.SalaryDetail;
import salary.model.SalaryItem;

/**
 * 일용직 급여 상세 내역 조회 핸들러 (AJAX/JSON 응답)
 * 日雇い給与詳細履歴照会ハンドラー (AJAX/JSONレスポンス)
 */
public class GetDayworkerSalaryDetailHandler implements CommandHandler {

	// 비즈니스 서비스 객체 생성 / ビジネスサービスオブジェクトの生成
	private GetDayworkerSalaryDetailService detailService = new GetDayworkerSalaryDetailService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			// 1. 요청 파라미터 파싱 / リクエストパラメータのパース
			int empId = Integer.parseInt(req.getParameter("empId"));
			int year = Integer.parseInt(req.getParameter("payYear"));
			int month = Integer.parseInt(req.getParameter("payMonth"));
			int seq = Integer.parseInt(req.getParameter("paySeq"));

			// 2. 서비스 호출 (비즈니스 로직 및 DB 처리 위임)
			// 2. サービス呼び出し (ビジネスロジックおよびDB処理の委任)
			SalaryDetailResult result = detailService.getSalaryDetail(empId, year, month, seq);

			// 3. JSON 응답 생성 / JSONレスポンスの生成
			String jsonResponse = buildJsonResponse(result);
			out.print(jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생 시 에러 JSON 응답 / 例外発生時エラーJSONレスポンス
			String errMsg = e.getMessage() != null ? escapeJson(e.getMessage()) : "서버 오류 발생 / サーバーエラー発生";
			out.print("{\"error\": \"" + errMsg + "\"}");
		}

		return null;
	}

	// Service 결과 DTO를 JSON 문자열로 변환하는 유틸 메서드
	// Service結果DTOをJSON文字列に変換するユーティリティメソッド
	private String buildJsonResponse(SalaryDetailResult result) {
		StringBuilder json = new StringBuilder();
		json.append("{");

		if (result.isSaved()) {
			// 1. 기존 데이터가 존재하는 경우 / 既存データが存在する場合
			Salary salary = result.getSalary();
			List<SalaryDetail> details = result.getDetails();

			json.append("\"isSaved\": true,");
			json.append("\"totalPay\": ").append(salary.getTotalPay()).append(",");
			json.append("\"totalDeduct\": ").append(salary.getTotalDeduct()).append(",");
			json.append("\"netPay\": ").append(salary.getNetPay()).append(",");
			json.append("\"items\": [");
			for (int i = 0; i < details.size(); i++) {
				SalaryDetail d = details.get(i);
				json.append("{")
						.append("\"salaryItemId\": ").append(d.getSalaryItemId()).append(",")
						.append("\"itemType\": \"").append(d.getItemType()).append("\",")
						.append("\"itemName\": \"").append(escapeJson(d.getItemName())).append("\",")
						.append("\"taxYn\": \"").append(d.getTaxYn()).append("\",")
						.append("\"amount\": ").append(d.getAmount()).append(",")
						.append("\"memo\": \"").append(d.getMemo() != null ? escapeJson(d.getMemo()) : "").append("\"")
						.append("}").append(i < details.size() - 1 ? "," : "");
			}
			json.append("]");
		} else {
			// 2. 기존 데이터가 없어 기본 공제 항목을 반환하는 경우
			// 2. 既存データがなく基本控除項目を返却する場合
			List<SalaryItem> activeItems = result.getActiveDeductionItems();

			json.append("\"isSaved\": false,");
			json.append("\"items\": [");
			for (int i = 0; i < activeItems.size(); i++) {
				SalaryItem item = activeItems.get(i);
				json.append("{")
						.append("\"salaryItemId\": ").append(item.getSalaryItemId()).append(",")
						.append("\"itemType\": \"DEDUCT\",")
						.append("\"itemName\": \"").append(escapeJson(item.getItemName())).append("\",")
						.append("\"taxYn\": \"").append(item.getTaxYn()).append("\",")
						.append("\"amount\": 0,")
						.append("\"memo\": \"\"")
						.append("}").append(i < activeItems.size() - 1 ? "," : "");
			}
			json.append("]");
		}

		json.append("}");
		return json.toString();
	}

	// JSON 특수문자 이스케이프 유틸 / JSON特殊文字エスケープユーティリティ
	private String escapeJson(String input) {
		if (input == null) return "";
		return input.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}
}