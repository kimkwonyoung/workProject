package pj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.StringUtil;
import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Board;
import workDto.Member;

/**
 * @author kky
 * 게시판 서블릿 컨트롤러
 *
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = -2850407651698537945L;
	BoardService _BoardService;
	MemberService _MemberService;
	///WEB-INF/jsp/board/
	
//	public BoardController() {
//        _BoardService = new BoardService(new BoardDB());
//        _MemberService = new MemberService(new MemberDB());
//    }
	public void init() {
    	_BoardService = new BoardService(new BoardDB());
        _MemberService = new MemberService(new MemberDB());
    }

	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//가져온 주소 체크 
		//requestURI = /workProject/board/설정주소
		//contextPath = /workProject
		//action = 설정주소
		String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String action = requestURI.substring(contextPath.length() + "/board/".length());
		
        System.out.println("requestURI = " + requestURI);
        System.out.println("contextPath = " + contextPath);
        System.out.println("action = " + action);
        
		try {
			switch(action) {
				case "boardList" -> boardList(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
//			switch(action) {
//				case "BoardInfo" -> BoardInfo(request, response);
//				case "Boardlist" -> Boardlist(request, response);
//				case "BoardUpdateInfo" -> BoardUpdateInfo(request, response);
//				case "BoardWrite" -> BoardWrite(request, response);
//				case "BoardDelete" -> boardDelete(request, response);
//			}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
//			switch(action) {
//			case "BoardInsert" -> boardInsert(request, response);
//			case "BoardUpdate" -> boardUpdate(request, response);
	}
	

	public void boardInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memid = request.getParameter("memberid");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String fixed_yn = request.getParameter("fixed_yn");
		String board_type = "nomal";
		Board board = new Board();
		List<Board> boardList = new ArrayList<>();
		if (type.equals("10")) {
			//일반게시판
			board.setBoard_code(10);
		} else if (type.equals("20")) {
			//공지사항
			board.setBoard_code(20);
			board_type = "notice";
		} else {
			//기타
			//board.setBoard_code(0);
		}
		
		if (StringUtil.isEmpty(fixed_yn)) {
			fixed_yn = "N";
		}
		
		
		board.setMem_id(memid);
		board.setContent(content);
		board.setTitle(title);
		board.setFixed_yn(fixed_yn);
		
		_BoardService.insert(board);
		
		if(board_type.equals("notice")) {
			boardList = _BoardService.selectByNoticeList();
		} else {
			boardList = _BoardService.selectByNomalList();
		}
		
		
		
		request.setAttribute("board_type", board_type);
		request.setAttribute("boardList", boardList);
		request.getRequestDispatcher("/board/board_list.jsp").forward(request, response);
	}
	
	public void boardUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String board_num = request.getParameter("board_num");
		String type = request.getParameter("type");
		String fixed_yn = request.getParameter("fixed_yn");
		String board_type = "";
		
		if (StringUtil.isEmpty(fixed_yn)) {
			fixed_yn = "N";
		}
		
		Board board = new Board();
		if (type.equals("10")) {
			//일반게시판
			board.setBoard_code(10);
			board_type = "nomal";
		} else if (type.equals("20")) {
			//공지사항
			board.setBoard_code(20);
			board_type = "notice";
		} else {
			//기타
			//board.setBoard_code(0);
		}
		board.setBoard_num(Integer.parseInt(board_num));
		board.setContent(content);
		board.setTitle(title);
		board.setFixed_yn(fixed_yn);
		
		_BoardService.update(board);
		
		Optional<Board> optionalBoard = _BoardService.selectByBoardNum(Integer.parseInt(board_num));
		
		if (optionalBoard.isPresent()) {
			board = optionalBoard.get();
			request.setAttribute("infoBoard", board);
			System.out.println("수정된 데이터 확인 = " + board);
		} else {
			System.out.println("보드 번호 없음 체크필요");
		}
		
		request.getRequestDispatcher("/board/board_info.jsp").forward(request, response);
		
	}
	
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String board_num = request.getParameter("board_num");
		System.out.println("보드 삭제 컨트롤로 도달 확인");
		_BoardService.delete(Integer.parseInt(board_num));
		
//		List<Board> boardList = _BoardService.selectByNomalList();
//		request.setAttribute("boardList", boardList);
		//리스트 포워딩은 수정 필요
		
		request.getRequestDispatcher("/board/board_list.jsp").forward(request, response);
	}
	
	public void boardList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String board_type = request.getParameter("board_type");
		System.out.println("타입 확인 = " + board_type);
		List<Board> boardList = new ArrayList<>();
		
		if(StringUtil.isEmpty(board_type)) {
			boardList = _BoardService.selectByNomalList();
			board_type = "nomal";
		} else {
			if(board_type.equals("notice")) {
				boardList = _BoardService.selectByNoticeList();
			} else if(board_type.equals("nomal")) {
				boardList = _BoardService.selectByNomalList();
			} else {
				System.out.println("존재 하지 않는 타입(jsp확인)");
			}
		}
		
		request.setAttribute("board_type", board_type);
		request.setAttribute("boardList", boardList);
		
		request.getRequestDispatcher("/WEB-INF/jsp/board/board_list.jsp").forward(request, response);
	}

	public void BoardInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("board_num"));
		
		Optional<Board> optionalBoard = _BoardService.selectByBoardNum(num);
		
		_BoardService.updateViewCount(num);
		
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			request.setAttribute("infoBoard", board);
			System.out.println("보드상세정보 데이터 확인 = " + board);
		} else {
			System.out.println("보드 번호 없음 체크필요");
		}
		
		
		request.getRequestDispatcher("/board/board_info.jsp").forward(request, response);
		
		
	}
	
	public void BoardUpdateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("board_num"));
		System.out.println("보드업데이트인포왔냐");
		Optional<Board> optionalBoard = _BoardService.selectByBoardNum(num);
		
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			request.setAttribute("infoBoard", board);
		} else {
			System.out.println("보드 번호 없음 체크필요");
		}
		request.setAttribute("chk", "update");
		
		request.getRequestDispatcher("/board/board_write.jsp").forward(request, response);
	}
	
	public void BoardWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("chk", "write");
		request.getRequestDispatcher("/board/board_write.jsp").forward(request, response);

	}
}
