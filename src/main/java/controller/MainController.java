package controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardService;

/**메인 컨트롤러
 * @author kky
 *
 */
public class MainController {
	private BoardService _boardService;
	
	public String mainIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("세션 값 = " + request.getSession().getAttribute("loginMember"));
    	
    	
		request.setAttribute("noticeList", _boardService.selectByMainList().get("noticeList"));
		request.setAttribute("nomalList", _boardService.selectByMainList().get("nomalList"));
		
		return "index.jsp";
	}
}
