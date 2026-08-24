package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetAllMonthlySalaryService;
import salary.statics.service.request.MonthlySalaryDTO;

/**
 *  월별 급여 통계 조회 핸들러
 *  月別給与統計照会ハンドラー
 */
public class GetAllMonthlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/monthlyStaticsAll.jsp";

	private GetAllMonthlySalaryService monthlySalaryService = new GetAllMonthlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//  HTTP 요청 메서드 검증 (GET/POST 허용, 그 외 405 Method Not Allowed 반환)
		//  HTTPリクエストメソッド検証 (GET/POST許可、それ以外は405 Method Not Allowed返却)
		if (req.getMethod().equalsIgnoreCase("GET") || req.getMethod().equalsIgnoreCase("POST")) {
			return processForm(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		//  요청 파라미터에서 검색 연도 추출 (누락 시 현재 연도를 기본값으로 설정)
		//  リクエストパラメータから検索年度を抽出 (未指定時は今年度をデフォルト値に設定)
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		//  해당 연도의 12개월 급여 통계 데이터 조회
		//  該当年度の12ヶ月給与統計データを取得
		List<MonthlySalaryDTO> statList = monthlySalaryService.get12MonthSalaryStats(targetYear);

		//  연간 총 지급액 및 월평균 인원수 집계 변수
		//  年間総支給額および月平均人数集計変数
		long totalSalarySum = 0;
		double totalEmpSum = 0;
		int validMonthCount = 0;

		//  12개월 데이터를 순회하며 총 급여액 합산 및 급여 지급 월 기준 평균 인원 계산
		//  12ヶ月 データをループ処理し、総給与額の合算および給与支給月の平均人数を計算
		for (MonthlySalaryDTO dto : statList) {
			totalSalarySum += dto.getTotalSalaryThousand();
			if (dto.getEmpCount() > 0) {
				totalEmpSum += dto.getEmpCount();
				validMonthCount++; //  실제 급여가 지급된 월 수 /  実際に給与が支給された月数
			}
		}

		//  연평균 인원수 계산 (소수점 첫째 자리 반올림)
		//  年平均人数の計算 (少数点第一位四捨五入)
		double avgEmpCount = validMonthCount > 0 ? (totalEmpSum / validMonthCount) : 0.0;
		avgEmpCount = Math.round(avgEmpCount * 10.0) / 10.0;

		//  뷰(JSP)로 전달할 데이터 Request 속성에 저장
		//  ビュー(JSP)へ渡すデータをRequest属性に保存
		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("statList", statList);
		req.setAttribute("totalSalarySum", totalSalarySum);
		req.setAttribute("avgEmpCount", avgEmpCount);

		return FORM_VIEW;
	}

	/**
	 *  문자열을 안전하게 Integer로 변환 (숫자 및 음수 기호 외 제거)
	 *  文字列を安全にIntegerへ変換 (数字およびマイナス記号以外を削除)
	 */
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty()) {
			return null;
		}
		try {
			String cleanVal = val.replaceAll("[^0-9-]", "");
			if (cleanVal.isEmpty())
				return null;
			return Integer.parseInt(cleanVal);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}