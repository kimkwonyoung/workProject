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

import Utils.CommonProperty;
import Utils.StringUtil;
import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Board;
import workDto.SearchVO;

/**
 * @author kky
 * 게시판 서블릿 컨트롤러
 *
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = -2850407651698537945L;
	private SearchVO search = new SearchVO();
	private BoardService _BoardService;
	private MemberService _MemberService;
	
	
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
		
		try {
			switch(action) {
				case "boardList" -> boardList(request, response);
				case "boardInfo" -> boardInfo(request, response);
				case "boardUpdateInfo" -> boardUpdateInfo(request, response);
				case "boardDelete" -> boardDelete(request, response);
				case "boardInsert" -> boardInsert(request, response);
				case "boardUpdate" -> boardUpdate(request, response);
				case "boardWrite" -> boardWrite(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	//게시판 글 작성
	public void boardInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_code(request.getParameter("board_code")); //String board_code를 int로 변환
		Board board = Board.builder()
						.mem_id(request.getParameter("memberid"))
						.title(request.getParameter("title"))
						.content(request.getParameter("content"))
						.board_code(search.getsBoard_code())
						.fixed_yn(request.getParameter("fixed_yn"))
						.build();
		
		_BoardService.insert(board);
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("boardList", _BoardService.selectByBoardList(search));
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_list.jsp").forward(request, response);
	}
	
	//게시판 글 업데이트
	public void boardUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_code(request.getParameter("board_code")); //String board_code를 int로 변환
		
		Board board = Board.builder()
						.board_num(search.getsBoard_num())
						.title(request.getParameter("title"))
						.content(request.getParameter("content"))
						.board_code(search.getsBoard_code())
						.fixed_yn(request.getParameter("fixed_yn"))
						.build();
						
		_BoardService.update(board);
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("infoBoard", _BoardService.selectKeyNum(search));

		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_info.jsp").forward(request, response);
		
	}
	
	//게시판 글 삭제
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		
		search.setsBoard_code(request.getParameter("board_code")); //String board_code를 int로 변환
		
		_BoardService.delete(search);
		
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("boardList", _BoardService.selectByBoardList(search));
		
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_list.jsp").forward(request, response);
	}
	
	//게시판 목록
	public void boardList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (StringUtil.isEmpty(request.getParameter("board_type"))) search.setsBoard_code("10");
		search.setsBoard_code(request.getParameter("board_type"));
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("boardList", _BoardService.selectByBoardList(search));
		
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_list.jsp").forward(request, response);
	}
	
	//게시판 글 정보
	public void boardInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		search.setsBoard_code(request.getParameter("board_type"));
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("infoBoard", _BoardService.selectByBoardNum(search));
		
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_info.jsp").forward(request, response);
	}
	
	//게시판 글 수정 페이지 이동(글쓰기 공통)
	public void boardUpdateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		
		request.setAttribute("chk", CommonProperty.getUpdate());
		request.setAttribute("infoBoard", _BoardService.selectKeyNum(search));
		
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_write.jsp").forward(request, response);
	}
	
	//게시판 글 쓰기 페이지 이동(글쓰기 공통)
	public void boardWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("chk", CommonProperty.getWrite());

		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_write.jsp").forward(request, response);
	}
}
