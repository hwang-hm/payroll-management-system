package salary.util;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

// 귀속연월·급여차수 request 파라미터
// 帰属年月・給与回数の request パラメータ
public class PayPeriodParams {

	private final int payYear;
	private final int payMonth;
	private final int paySeq;

	public PayPeriodParams(int payYear, int payMonth, int paySeq) {
		this.payYear = payYear;
		this.payMonth = payMonth;
		this.paySeq = paySeq;
	}

	public int getPayYear() {
		return payYear;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public int getPaySeq() {
		return paySeq;
	}

	// 없으면 올해·이번달·1차
	// なければ今年・今月・1回
	public static PayPeriodParams fromRequest(HttpServletRequest req) {
		Calendar now = Calendar.getInstance();
		return fromRequest(req, now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, 1);
	}

	public static PayPeriodParams fromRequest(HttpServletRequest req, int defaultYear, int defaultMonth,
			int defaultSeq) {
		return new PayPeriodParams(
				RequestParamParser.parseInt(req, "payYear", defaultYear),
				RequestParamParser.parseInt(req, "payMonth", defaultMonth),
				RequestParamParser.parseInt(req, "paySeq", defaultSeq));
	}

	// hidden 필드로 항상 넘어오는 POST용. 없으면 NumberFormatException
	// hidden フィールドで常に渡る POST用。なければ NumberFormatException
	public static PayPeriodParams requiredFromRequest(HttpServletRequest req) {
		return new PayPeriodParams(
				Integer.parseInt(req.getParameter("payYear")),
				Integer.parseInt(req.getParameter("payMonth")),
				Integer.parseInt(req.getParameter("paySeq")));
	}

	public String toQueryString() {
		return "payYear=" + payYear + "&payMonth=" + payMonth + "&paySeq=" + paySeq;
	}
}
