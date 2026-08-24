package salary.statics.command;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import salary.statics.service.GetEmpYearlySalaryService;
import salary.statics.service.request.EmpYearlySalaryDTO;

/**
 * 개인별 연도별 급여 통계 조회 핸들러
 * 個人別年度別給与統計照会ハンドラー
 */
public class GetEmpYearlyStaticsHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/salary/statics/empYearlyStatics.jsp";
	private GetEmpYearlySalaryService service = new GetEmpYearlySalaryService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 조회 대상 연도 파라미터 수신 (전달값이 없으면 현재 연도를 기본값으로 설정)
		// 照会対象年度パラメーター受信（値がない場合は今年度をデフォルト値として設定）
		Integer targetYear = parseInt(req.getParameter("year"));
		if (targetYear == null) {
			targetYear = LocalDate.now().getYear();
		}

		// 조회 대상 사원 ID 및 사원명 파라미터 수신
		// 照会対象の社員IDおよび社員名パラメーター受信
		Integer empId = parseInt(req.getParameter("empId"));
		String empName = req.getParameter("empName");

		// 페이지 최초 진입 시 사원이 지정되지 않은 경우 기본 사원(ID: 1) 자동 지정
		// ページ初期アクセス時に社員が指定されていない場合、デフォルト社員(ID: 1)を自動指定
		if (empId == null) {
			empId = 1;
		}

		List<EmpYearlySalaryDTO> statList = null;

		// 사원 ID가 존재하는 경우 해당 사원의 10개년 급여 통계 데이터 조회
		// 社員IDが存在する場合、該当社員の10年間の給与統計データを照会
		if (empId != null) {
			statList = service.get10YearPersonalStats(empId, targetYear);
		}

		// 뷰(JSP) 전달을 위한 Request 속성 저장
		// ビュー(JSP)へ渡すためのRequest属性保存
		req.setAttribute("selectedYear", targetYear);
		req.setAttribute("selectedEmpId", empId);
		req.setAttribute("selectedEmpName", empName);
		req.setAttribute("statList", statList);

		return FORM_VIEW;
	}

	/**
	 * 문자열을 Integer로 안전하게 변환 (null, 빈값, 숫자가 아닌 문자 포함 시 null 반환)
	 * 文字列をIntegerへ安全に変換（null、空文字、数値以外の文字を含む場合はnullを返却）
	 */
	private Integer parseInt(String val) {
		if (val == null || val.trim().isEmpty())
			return null;
		try {
			// 숫자와 음수 기호(-)를 제외한 모든 문자 제거 후 변환
			// 数字とマイナス記号(-)以外のすべての文字を削除して変換
			return Integer.parseInt(val.replaceAll("[^0-9-]", ""));
		} catch (Exception e) {
			return null;
		}
	}

}