package salary.util;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

// 사원별 급여내역처럼 시작~종료 년월 범위 파라미터
// 社員別給与明細と同様の開始〜終了年月範囲パラメータ
public class PeriodRangeParams {

	private final int startYear;
	private final int startMonth;
	private final int endYear;
	private final int endMonth;

	public PeriodRangeParams(int startYear, int startMonth, int endYear, int endMonth) {
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.endYear = endYear;
		this.endMonth = endMonth;
	}

	public int getStartYear() {
		return startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public int getEndYear() {
		return endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public static PeriodRangeParams fromRequest(HttpServletRequest req) {
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH) + 1;
		return new PeriodRangeParams(
				RequestParamParser.parseInt(req, "startYear", currentYear),
				RequestParamParser.parseInt(req, "startMonth", 1),
				RequestParamParser.parseInt(req, "endYear", currentYear),
				RequestParamParser.parseInt(req, "endMonth", currentMonth));
	}
}
