package salary.util;

import javax.servlet.http.HttpServletRequest;

// 핸들러에서 request 파라미터 파싱할 때 공통으로 씀
// Handlerで request パラメータをパースするときに共通利用
public final class RequestParamParser {

	private RequestParamParser() {
	}

	public static int parseInt(String value, int defaultValue) {
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int parseInt(HttpServletRequest req, String name, int defaultValue) {
		return parseInt(req.getParameter(name), defaultValue);
	}

	public static Integer parseNullableInt(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer parseNullableInt(HttpServletRequest req, String name) {
		return parseNullableInt(req.getParameter(name));
	}

	// formId처럼 0이면 null로 볼 때
	// formIdのように0なら null とみなすとき
	public static Integer parseNullableIntOrZero(String value) {
		if (value == null || value.trim().isEmpty() || "0".equals(value.trim())) {
			return null;
		}
		return parseNullableInt(value);
	}

	public static Integer parseNullableIntOrZero(HttpServletRequest req, String name) {
		return parseNullableIntOrZero(req.getParameter(name));
	}
}
