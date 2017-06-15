package org.moon.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter { // "/loginPost"로 접근하도록 설정하는 것을 목적으로 한다. 로그인한 사용자에 대해서 postHandle()을 이용해서 HttpSession에 보관하는 처리를 담당한다.
	
	private static final String LOGIN = "login";
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, // MemberController에서 memberVO라는 이름으로 객체를 담아둔 상태이므로, 이 상태를 체크해서 HttpSession에 저장한다.
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		ModelMap modelMap = modelAndView.getModelMap();
		
		Object memberVO = modelMap.get("memberVO");
		
		if(memberVO != null){
			
			logger.info("new login success");
			session.setAttribute(LOGIN,  memberVO);
			
			if(request.getParameter("useCookie") != null){
				
				logger.info("remember me........................");
				
				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(60*60*24*7);
				
				response.addCookie(loginCookie);
			}
			
//			response.sendRedirect("/");
			
			Object dest = session.getAttribute("dest"); // 로그인 성공 후의 response.sendRedirect() 작업에 'dest'정보를 사용하도록 한다.
			
			response.sendRedirect(dest != null ? (String)dest:"/");			
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // 기존 HttpSession에 정보가 남아있는 경우에 정보를 삭제하도록 한다.
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null){
			
			logger.info("clear login data before");
			
			session.removeAttribute(LOGIN);
		}
		return true;
	}
}
