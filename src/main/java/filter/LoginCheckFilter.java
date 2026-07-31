package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {
//테스트66
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException { // 17행: 여는 중괄호 { 추가

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);

		// 세션이 없거나 세션에 "authUser"(로그인 정보)가 없다면 로그인하지 않은 상태
		if (session == null || session.getAttribute("authUser") == null) {
			HttpServletResponse response = (HttpServletResponse) res;
			// 로그인 페이지로 강제 리다이렉트 (쉼표 오타 수정)
			response.sendRedirect(request.getContextPath() + "/login.do");
		} else {
			// 로그인한 상태라면 막지 않고 원래 요청했던 다음 단계(핸들러/JSP)로 진행시킴ddd
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 필터 초기화 시 필요한 코드가 있다면 작성 (현재는 생략 가능)
	}

	@Override
	public void destroy() {
		// 필터 소멸 시 처리할 코드가 있다면 작성 (현재는 생략 가능)
	}
}