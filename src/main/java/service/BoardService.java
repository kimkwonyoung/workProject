package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import Utils.CommonProperty;
import Utils.QueryProperty;
import Utils.StringUtil;
import workDao.BoardDAOImpl;
import workDto.Board;
import workDto.Board_comment;
import workDto.SearchVO;

/** 게시판 비즈니스 로직
 * @author kky
 *
 */
public class BoardService {
	private BoardDAOImpl _boardDao;
	
//	public BoardService(BoardDAOImpl boardDB) {
//		_boardDao = boardDB;
//	}
	
	//메인페이지 공지사항, 일반 글 4개
	public Map<String, List<Board>> selectByMainList() {
		Map<String, List<Board>> map = new HashMap<>();
		
		map.put("noticeList", _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectMainNotice")));
		map.put("nomalList", _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectMainNomal")));
		return map;
	}
	
	//게시판 전체 목록
	public Map<String, Object> selectByBoardList(Board board) throws Exception {
		
		board.setTotalCount(_boardDao.selectPageTotalCount(board));
		System.out.println("총 게시글 개수 = " + board.getTotalCount());
		Map<String, Object> map = new HashMap<>();
		
		map.put("pageBoard", board);
		map.put("list", _boardDao.selectByPageList(board));
		map.put("notice", _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectMainNotice")));
		
		return map;
	}
	
	//아이디를 Key로 게시판 글 가져오기
	public List<Board> selectByIdBoardList(SearchVO search) {
		return _boardDao.selectByIdBoardList(QueryProperty.getQuery("board.selectId"), search.getsMemid());
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기 + 조회수 1증가 업데이트
	public Board selectByBoardNum(int board_num) {
		Optional<Board> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), board_num);
		
		int row = _boardDao.updateViewCount(QueryProperty.getQuery("board.updateView"), board_num);
		
		if (row > 0) {
			System.out.println("조회수 반영된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		return optionalBoard.orElse(null);
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기
	public Board selectKeyNum(int board_num) {
		Optional<Board> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), board_num);
		return optionalBoard.orElse(null);
	}
	
	//댓글 전체 리스트 가져오기(글 한개에 대한)
	public List<Board_comment> selectCommentList(int board_num) {
		return _boardDao.selectByCommentList(QueryProperty.getQuery("board.selectCommentList"), board_num);
	}
	
	//댓글 카운트
	public int selectCommentCount(int board_num) {
		return _boardDao.selectByCommentCount(QueryProperty.getQuery("board.selectCommentCount"), board_num);
	}
	
	//게시판 글 작성
	public void insert(Board board) {
		if (StringUtil.isEmpty(board.getFixed_yn())) board.setFixed_yn("N");
		
		int row = _boardDao.insert(QueryProperty.getQuery("board.insert"), board);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 댓글 작성
//	public JSONObject insert(Board_comment comment) {
//		JSONObject jsonResult = new JSONObject();
//		int row = _boardDao.insertComment(QueryProperty.getQuery("board.insertComment"), comment);
//		if (row > 0) {
//			jsonResult.put("status", true);
//			System.out.println("반영된 글 갯수 : " + row);
//			System.out.println("작성한 댓글 = " + comment);
//			jsonResult.put("board_comment", _boardDao.selectByComment(QueryProperty.getQuery("board.selectCommentRecent"), comment));
//		} else {
//			jsonResult.put("status", false);
//			System.out.println("반영 X");
//		}
//		return jsonResult;
//	}
	public void insert(Board_comment comment) {
		int row = _boardDao.insertComment(QueryProperty.getQuery("board.insertComment"), comment);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 글 수정
	public void update(Board board) {
		if (StringUtil.isEmpty(board.getFixed_yn())) board.setFixed_yn("N");
		int row = _boardDao.update(QueryProperty.getQuery("board.update"), board);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 댓글 수정
	public JSONObject update(Board_comment comment) {
		JSONObject jsonResult = new JSONObject();
		String nowtime = StringUtil.getDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		comment.setReg_date(nowtime);
		int row = _boardDao.updateComment(QueryProperty.getQuery("board.updateComment"), comment);
		if (row > 0) {
			
			jsonResult.put("status", true);
			jsonResult.put("comment_num", comment.getComment_num());
			jsonResult.put("detail", comment.getDetail());
			jsonResult.put("reg_date", nowtime);
			System.out.println("반영된 댓글 갯수 : " + row);
		} else {
			jsonResult.put("status", false);
			System.out.println("반영 X");
		}
		return jsonResult;
	}
	
	//게시판 글 삭제
	public JSONObject delete(Board board) {
		JSONObject jsonResult = new JSONObject();
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), board.getBoard_num());
		if (row > 0) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageBoardDelete());
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessageBoardFail());
		}
		return jsonResult;
	}
	
	//게시판 글 체크한것 삭제
	public void deleteChkbox(SearchVO search) {
		_boardDao.delete(QueryProperty.getQuery("board.deleteChk"), search.getsBNumStr());
	}
	
	//게시판 댓글 삭제
	public void deleteComment(int comment_num) {
		int row = _boardDao.delete(QueryProperty.getQuery("board.deleteComment"), comment_num);
		if (row > 0) {
			System.out.println("삭제된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		
	}
	
}
