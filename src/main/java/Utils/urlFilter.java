package Utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class urlFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = -3998687148897102541L;

	public urlFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

    	String requestURI = httpRequest.getRequestURI();
    	String contextPath = httpRequest.getContextPath();
    	String action = requestURI.substring(contextPath.length());
    	HttpSession session = httpRequest.getSession();
    	
//    	System.out.println("requestURI = " + requestURI);
//    	System.out.println("contextPath = " + contextPath);
//    	System.out.println("action = " + action);
        
        if (session.getAttribute("loginMember") == null) {
            if (action.equals("/board/boardWrite.do")) {
                httpResponse.sendRedirect(contextPath + "/board/boardList.do");
                return;
            } else if (action.equals("/board/boardUpdateInfo.do")) {
            	httpResponse.sendRedirect(contextPath + "/board/boardList.do");
            	return;
            } else if (action.equals("/board/boardUpdateInfo.do")) {
           	 	httpResponse.sendRedirect(contextPath + "/board/boardList.do");
           	 	return;
            } else if (action.equals("/member/memberList.do")) {
            	httpResponse.sendRedirect(contextPath + "/main/mainIndex.do");
            	return;
            } else if (action.equals("/member/memberUpdateMove.do")) {
            	httpResponse.sendRedirect(contextPath + "/main/mainIndex.do");
            	return;
            } else if (action.equals("/member/memberWithdrawMove.do")) {
            	httpResponse.sendRedirect(contextPath + "/main/mainIndex.do");
        	 	return;
            }
        }
		
		chain.doFilter(request, response);
	}


}
