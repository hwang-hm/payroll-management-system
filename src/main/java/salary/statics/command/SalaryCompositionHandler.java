package salary.statics.command;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.SalaryCompositionService;
import salary.statics.service.request.SalaryCompositionDTO;

/**
 * 급여 구성 항목별 통계 조회 핸들러
 * 給与構成項目別統計照会ハンドラー
 */
public class SalaryCompositionHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/salaryCompositionStatics.jsp";

	// 서비스 계층 의존성 객체 생성
	// サービス層依存オブジェクトの生成
	private SalaryCompositionService service = new SalaryCompositionService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 현재 날짜 기준 지난달(1달 전) 계산 (기본 조회 기간 설정용)
		// 現在の日付基準の前月（1ヶ月前）計算（デフォルト照会期間設定用）
		LocalDate lastMonth = LocalDate.now().minusMonths(1);

		// 요청 파라미터 수신 (귀속연도, 귀속월, 사원 ID, 사원명)
		// リクエストパラメーター受信（帰属年度、帰属月、社員ID、社員名）
		String yearParam = req.getParameter("year");
		String monthParam = req.getParameter("month");
		String empIdParam = req.getParameter("empId");
		String empNameParam = req.getParameter("empName");

		// 파라미터 검증 및 기본값 설정 (파라미터가 없으면 지난달 연도/월 및 기본 사원 ID: 1로 설정)
		// パラメーター検証およびデフォルト値設定（パラメーターがない場合は前月の年度・月およびデフォルト社員ID: 1に設定）
		int selectedYear = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : lastMonth.getYear();
		int selectedMonth = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam)
				: lastMonth.getMonthValue();
		int empId = (empIdParam != null && !empIdParam.trim().isEmpty()) ? Integer.parseInt(empIdParam) : 1;

		// 뷰(JSP) 전달을 위한 검색 조건 Request 속성 저장
		// ビュー(JSP)へ渡すための検索条件Request属性保存
		req.setAttribute("selectedYear", selectedYear);
		req.setAttribute("selectedMonth", selectedMonth);
		req.setAttribute("selectedEmpId", empId);
		req.setAttribute("selectedEmpName", empNameParam);

		// 해당 사원, 연도, 월의 급여 구성 항목 통계 데이터 조회 서비스 호출
		// 該当社員、年度、月の給与構成項目統計データ照会サービス呼び出し
		SalaryCompositionDTO statData = service.getSalaryComposition(empId, selectedYear, selectedMonth);
		req.setAttribute("statData", statData);

		return FORM_VIEW;
	}

}