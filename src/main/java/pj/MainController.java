package pj;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Board;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = -1625158307803301508L;
	BoardService _BoardService;
	MemberService _MemberService;
       
    public MainController() {
    	 _BoardService = new BoardService(new BoardDB());
         _MemberService = new MemberService(new MemberDB());
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	List<Board> boardList = _BoardService.selectByBoardList();
		//리스트 수정해야함 체크
    	System.out.println("세션 값 = " + request.getSession().getAttribute("loginMember"));
    	
		request.setAttribute("boardList", boardList);
		request.getRequestDispatcher("index.jsp").forward(request, response);
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
