package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Utils.CommonProperty;
import Utils.StringUtil;
import service.BoardService;
import workDto.Board;
import workDto.Board_comment;
import workDto.SearchVO;

/**게시판 컨트롤러
 * @author kky
 * 
 *
 */
public class BoardController {
	private SearchVO search = new SearchVO();
	private BoardService _boardService;
	
	//게시판 글 작성
	public String boardInsert(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		_boardService.insert(board);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		return "board/board_list.jsp";
	}
	
	//게시판 글 업데이트
	public String boardUpdate(Board board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
						
		_boardService.update(board);
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("infoBoard", _boardService.selectKeyNum(board.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(board.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(board.getBoard_num()));

		return "board/board_info.jsp";
		
	}
	
	//게시판 글 삭제
	public String boardDelete(Board board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject jsonResult = _boardService.delete(board);
		return jsonResult.toString();
	}
	
	//게시판 체크한 글 삭제
	public String boardDeleteChkbox(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		search.setsBNumStr(request.getParameter("bnumStr"));
		System.out.println("넘어온 글번호 값 : " + request.getParameter("bnumStr"));
		
		_boardService.deleteChkbox(search);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		
		return "board/board_list.jsp";
	}
	
	//게시판 목록
	public String boardList(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtil.isEmpty(request.getParameter("board_code"))) board.setBoard_code(10);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		return "board/board_list.jsp";
	}
	
	
	public String boardList2(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtil.isEmpty(request.getParameter("board_code"))) board.setBoard_code(10);
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("result", _boardService.selectByBoardList(board));
		return "board/board_list2.jsp";
	}
	
	public String ajaxList2(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteAjax(board);
		
		return jsonResult.toString();
	}
	
	public String ajaxCheckDelete(Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = _boardService.deleteCheckBox(board);
		
		return jsonResult.toString();
		
	}
	
	//게시판 글 정보
	public String boardInfo(Board board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("board_code", board.getBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(board.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(board.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(board.getBoard_num()));
		
		return "board/board_info.jsp";
	}
	
	//게시판 글 수정 페이지 이동(글쓰기 공통)
	public String boardUpdateInfo(Board board, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("chk", CommonProperty.getUpdate());
		request.setAttribute("infoBoard", _boardService.selectKeyNum(board.getBoard_num()));
		
		return "board/board_write.jsp";
	}
	
	//게시판 글 쓰기 페이지 이동(글쓰기 공통)
	public String boardWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("chk", CommonProperty.getWrite());

		return "board/board_write.jsp";
	}
	
	//댓글 작성
	public String commentInsert(Board_comment comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_num(request.getParameter("board_num"));
		search.setsBoard_code(request.getParameter("board_code"));
		
		_boardService.insert(comment);
		
		request.setAttribute("board_code", search.getsBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(comment.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(comment.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(comment.getBoard_num()));
		
		return "board/board_info.jsp";
	}
//	public String commentInsert(Board_comment comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("댓글 = " + comment);
//		JSONObject jsonResult = _boardService.insert(comment);
//		System.out.println(jsonResult.get("board_comment"));
//		return jsonResult.toString();
//	}
	
	//댓글 삭제 
	public String commentDelete(Board_comment comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search.setsBoard_code(request.getParameter("board_code"));
		
		_boardService.deleteComment(comment.getComment_num());

		request.setAttribute("board_code", search.getsBoard_code());
		request.setAttribute("infoBoard", _boardService.selectByBoardNum(comment.getBoard_num()));
		request.setAttribute("board_comment", _boardService.selectCommentList(comment.getBoard_num()));
		request.setAttribute("comment_count", _boardService.selectCommentCount(comment.getBoard_num()));

		return "board/board_info.jsp";
	}
	
	//게시판에 댓글 수정하기
	public String boardUpdateComment(Board_comment board_comment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonResult = _boardService.update(board_comment);
		return jsonResult.toString();
	}
}
