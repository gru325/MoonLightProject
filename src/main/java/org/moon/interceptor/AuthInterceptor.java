package org.moon.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.moon.domain.MemberVO;
import org.moon.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class AuthInterceptor extends HandlerInterceptorAdapter { // 특정 경로에 접근하는경우 현재 사용자가 로그인한 상태의 사용자인지를 체크하고 컨트롤러를 호출하게 할 것인지를 결정한다.
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Inject
	MemberService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();
		
		if(session.getAttribute("login") == null){ //현재 사용자가 HttpSession에 적당한 값이 없는 경우 loginCookie를 가지고 있는지 체크한다.
			
			logger.info("current member is not logined");
			
			saveDest(request);
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie"); // 현재 사용자의 세션에 login이 존재하지 않지만 쿠키 중에 loginCookie가 존재할 때 처리가 진행된다.
			
			if(loginCookie != null){ //과거에 보관한 쿠키가 있다면
				
				MemberVO memberVO = service.checkLoginBefore(loginCookie.getValue()); // MemberService 객체를 이용해서 사용자의 정보가 존재하는지를 확인한다.
				
				logger.info("MemberVO: " + memberVO);
				
				if(memberVO != null){ // 만일 사용자의 정보가 존재한다면
					
					session.setAttribute("login", memberVO); // HttpSession에 다시 사용자의 정보를 넣어준다.
					
					return true;
				}
			}
			
			response.sendRedirect("/member/login"); // 사용자가 로그인하지 않은 상태라면 로그인창으로 이동하게 한다.
			
			return false;
		}
		return true;
	}
	
	private void saveDest(HttpServletRequest req){ // 원래 사용자가 원하는 페이지의 정보를 HttpSession에 'dest'라는 이름으로 저장한다.
		
		String uri = req.getRequestURI();
		
		String query = req.getQueryString();
		
		if(query == null || query.equals("null")){
			query = "";
		}else{
			query = "?" + query;
		}
		
		if(req.getMethod().equals("GET")){ // GET방식인 경우 URI정보 뒤의 정보를 사용하도록 한다.
			logger.info("dest: " + (uri + query));
			req.getSession().setAttribute("dest", uri + query);
		}
	}
	
	
}
