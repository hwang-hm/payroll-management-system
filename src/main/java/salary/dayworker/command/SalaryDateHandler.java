package salary.dayworker.command;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.dayworker.service.GetSalaryDateService;
import salary.dayworker.service.SaveSalaryDateService;
import salary.model.SalaryDate;

/**
 * 급여 정산일자 조회 및 저장을 처리하는 핸들러 클래스 (AJAX JSON 직접 응답 방식)
 * 給与精算日時の照会および保存を処理するハンドラークラス (AJAX JSON直接レスポンス方式)
 */
public class SalaryDateHandler implements CommandHandler {

	private GetSalaryDateService getService = new GetSalaryDateService();
	private SaveSalaryDateService saveService = new SaveSalaryDateService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	/**
	 * 1. GET 요청 : 정산일자 조회 (AJAX) 1. GETリクエスト : 精算日時の照会 (AJAX)
	 */
	private String processForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			String yearStr = req.getParameter("payYear");
			String monthStr = req.getParameter("payMonth");
			String seqStr = req.getParameter("paySeq");

			if (isEmpty(yearStr) || isEmpty(monthStr) || isEmpty(seqStr)) {
				out.print("{\"status\":\"invalid\"}");
				return null;
			}

			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int seq = Integer.parseInt(seqStr);

			SalaryDate salaryDate = getService.getSalaryDate(year, month, seq);

			if (salaryDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = sdf.format(salaryDate.getStartDate());
				String endDate = sdf.format(salaryDate.getEndDate());
				String payDate = sdf.format(salaryDate.getPayDate());

				out.print(String.format(
						"{\"status\":\"found\", \"salaryDateId\":%d, \"startDate\":\"%s\", \"endDate\":\"%s\", \"payDate\":\"%s\"}",
						salaryDate.getSalaryDateId(), startDate, endDate, payDate));
			} else {
				out.print("{\"status\":\"notFound\"}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
		}

		return null;
	}

	/**
	 * 2. POST 요청 : 정산일자 저장 및 수정 (AJAX) 2. POSTリクエスト : 精算日時の保存および更新 (AJAX)
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			String salaryDateIdStr = req.getParameter("salaryDateId");
			String yearStr = req.getParameter("payYear");
			String monthStr = req.getParameter("payMonth");
			String seqStr = req.getParameter("paySeq");
			String startDateStr = req.getParameter("startDate");
			String endDateStr = req.getParameter("endDate");
			String payDateStr = req.getParameter("payDate");

			// 필수 입력값 검증
			// 必須入力項目の検証
			if (isEmpty(yearStr) || isEmpty(monthStr) || isEmpty(seqStr) || isEmpty(startDateStr) || isEmpty(endDateStr)
					|| isEmpty(payDateStr)) {
				out.print("{\"status\":\"error\", \"message\":\"すべての項目(帰属年月、回数、精算期間、支給日)を入力してください。\"}");
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int degree = Integer.parseInt(seqStr);
			Date startDate = sdf.parse(startDateStr);
			Date endDate = sdf.parse(endDateStr);
			Date payDate = sdf.parse(payDateStr);

			SalaryDate salaryDate = new SalaryDate();
			salaryDate.setPayYear(year);
			salaryDate.setPayMonth(month);
			salaryDate.setPaySeq(degree);
			salaryDate.setStartDate(startDate);
			salaryDate.setEndDate(endDate);
			salaryDate.setPayDate(payDate);

			// PK 값이 넘어왔다면 domain 객체에 세팅
			// PK値が渡された場合はdomainオブジェクトにセット
			if (!isEmpty(salaryDateIdStr)) {
				salaryDate.setSalaryDateId(Integer.parseInt(salaryDateIdStr));
			}

			boolean isSuccess = saveService.saveOrUpdate(salaryDate);

			if (isSuccess) {
				out.print("{\"status\":\"success\", \"message\":\"精算期間および給与支給日が正常に保存されました。\"}");
			} else {
				out.print("{\"status\":\"fail\", \"message\":\"保存に失敗しました。\"}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"status\":\"error\", \"message\":\"サーバーエラー: " + e.getMessage() + "\"}");
		}

		return null;
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}