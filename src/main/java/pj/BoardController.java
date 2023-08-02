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

import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Board;
import workDto.Member;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = -2850407651698537945L;
	BoardService _BoardService;
	MemberService _MemberService;

	public BoardController() {
        _BoardService = new BoardService(new BoardDB());
        _MemberService = new MemberService(new MemberDB());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		try {
			switch(action) {
				case "BoardInfo" -> BoardInfo(request, response);
				case "Boardlist" -> Boardlist(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		try {
			switch(action) {
			case "boardInsert" -> boardInsert(request, response);
			case "boardUpdate" -> boardUpdate(request, response);
			case "boardDelete" -> boardDelete(request, response);
		}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void boardInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memid = request.getParameter("memberid");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Board board = new Board();
		List<Board> boardList = new ArrayList<>();
		Member member = _MemberService.selectBySearch(memid);
		if (type.equals("10")) {
			//일반게시판
			board.setBoard_code(10);
		} else if (type.equals("20")) {
			//공지사항
			board.setBoard_code(20);
		} else {
			//기타
			//board.setBoard_code(0);
		}
		board.setMem_id(memid);
		board.setMem_name(member.getName());
		board.setContent(content);
		board.setTitle(title);
		
		_BoardService.insert(board);
		
		boardList = _BoardService.selectByBoardList();
		
		request.setAttribute("boardList", boardList);
		request.getRequestDispatcher("/board/board_list.jsp").forward(request, response);
	}
	
	public void boardUpdate(HttpServletRequest request, HttpServletResponse response) {
		
		
		
	}
	
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) {
		
		
		
	}
	
	public void Boardlist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Board> boardList = _BoardService.selectByBoardList();
		request.setAttribute("boardList", boardList);
		request.getRequestDispatcher("/board/board_list.jsp").forward(request, response);
	}

	public void BoardInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("board_num"));
		
		Optional<Board> optionalBoard = _BoardService.selectByBoardNum(num);
		
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			request.setAttribute("infoBoard", board);
		} else {
			System.out.println("보드 번호 없음 체크필요");
		}
		
		request.getRequestDispatcher("/board/board_info.jsp").forward(request, response);
		
		
	}
}
