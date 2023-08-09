package pj;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Utils.CommonProperty;
import Utils.StringUtil;
import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Board;
import workDto.Board_comment;
import workDto.SearchVO;

/**게시판 서블릿
 * @author kky
 * 
 *
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = -2850407651698537945L;
	private SearchVO search = new SearchVO();
	private BoardService _BoardService;
	
	public void init() {
    	_BoardService = new BoardService(new BoardDB());
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
				case "boardDeleteChkbox" -> boardDeleteChkbox(request, response);
				case "boardInsert" -> boardInsert(request, response);
				case "boardUpdate" -> boardUpdate(request, response);
				case "boardWrite" -> boardWrite(request, response);
				case "boardUpdateComment" -> boardUpdateComment(request, response);
				case "commentInsert" -> commentInsert(request, response);
				case "commentDelete" -> commentDelete(request, response);
				
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
	
	//게시판 체크한 글 삭제
	public void boardDeleteChkbox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBNumStr(request.getParameter("bnumStr"));
		System.out.println("넘어온 글번호 값 : " + request.getParameter("bnumStr"));
		
		_BoardService.deleteChkbox(search);
		
		request.setAttribute("board_type", request.getParameter("board_type"));
		request.setAttribute("boardList", _BoardService.selectByBoardList(search));
		
//		request.setAttribute(CommonProperty.getAlertmessage(), "게시글 삭제 완료");
//		request.setAttribute(CommonProperty.getAlerthref(), CommonProperty.getBoardPath() + "board_list.jsp);
//		request.getRequestDispatcher(CommonProperty.getJspPath() + "/alertpage.jsp").forward(request, response);
		
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
		request.setAttribute("board_comment", _BoardService.selectCommentList(search));
		request.setAttribute("comment_count", _BoardService.selectCommentCount(search));
		
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
	
	//댓글 작성 (비동기 ajax로 나중에 바꿔야함)
	public void commentInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		search.setsBoard_code(request.getParameter("board_type"));
		
		Board_comment comment = Board_comment.builder()
								.board_num(search.getsBoard_num())
								.detail(request.getParameter("detail"))
								.mem_id(request.getParameter("mem_id"))
								.build();
		
		_BoardService.insert(comment);
		
		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("infoBoard", _BoardService.selectByBoardNum(search));
		request.setAttribute("board_comment", _BoardService.selectCommentList(search));
		request.setAttribute("comment_count", _BoardService.selectCommentCount(search));
		
		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_info.jsp").forward(request, response);
	}
	
	//댓글 삭제 (비동기 ajax로 나중에 바꿔야함)
	public void commentDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		search.setsBoard_code(request.getParameter("board_type"));
		search.setsComment_num(Integer.parseInt(request.getParameter("comment_num")));
		
		_BoardService.deleteComment(search);

		request.setAttribute("board_type", search.getsBoard_code());
		request.setAttribute("infoBoard", _BoardService.selectByBoardNum(search));
		request.setAttribute("board_comment", _BoardService.selectCommentList(search));
		request.setAttribute("comment_count", _BoardService.selectCommentCount(search));

		request.getRequestDispatcher(CommonProperty.getBoardPath() + "board_info.jsp").forward(request, response);
	}
	
	//게시판에 댓글 수정하기 (최초 구현 ajax 수업 진행 후 새로 설계 필요)
	public void boardUpdateComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BufferedInputStream in = new BufferedInputStream(request.getInputStream());
		int available = request.getContentLength();
		
		byte [] data = new byte[available];
		
		in.read(data);
		String str = new String(data, "utf-8");
		
		
		JSONObject jsonObject = new JSONObject(str);
		
		//넘어온 수정된 데이터
		int cnum = jsonObject.getInt("comment_num");
		String detail = jsonObject.getString("detail");
		String nowtime = StringUtil.getDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		Board_comment comment = Board_comment.builder()
								.comment_num(cnum)
								.detail(detail)
								.reg_date(nowtime)
								.build();
		
		_BoardService.update(comment);
		
		JSONObject jsonResult = new JSONObject();
		
		jsonResult.put("comment_num", cnum);
		jsonResult.put("detail", detail);
		jsonResult.put("reg_date", nowtime);
		
		response.reset();
		response.setContentType("Content-Type: application/json; charset=utf-8");
		ServletOutputStream out = response.getOutputStream();
		out.println(new String(jsonResult.toString().getBytes("utf-8"), "8859_1"));
		out.flush();
	}
}
