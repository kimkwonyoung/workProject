package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public List<Board> selectByBoardList(SearchVO search) {
		List<Board> boardList = new ArrayList<>();
		
		if (search.getsBoard_code() == 10) {
			boardList = _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectNomal"));
		} else if (search.getsBoard_code() == 20) {
			boardList = _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectNotice"));
		} else {
			System.out.println("타입 오류 체크");
		}
		
		return boardList;
	}
	
	//아이디를 Key로 게시판 글 가져오기
	public List<Board> selectByIdBoardList(SearchVO search) {
		return _boardDao.selectByIdBoardList(QueryProperty.getQuery("board.selectId"), search.getsMemid());
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기 + 조회수 1증가 업데이트
	public Board selectByBoardNum(SearchVO search) {
		Optional<Board> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), search.getsBoard_num());
		
		int row = _boardDao.updateViewCount(QueryProperty.getQuery("board.updateView"), search.getsBoard_num());
		
		if (row > 0) {
			System.out.println("조회수 반영된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		return optionalBoard.orElse(null);
	}
	
	//게시판 번호를 Key로 게시판 글 가져오기
	public Board selectKeyNum(SearchVO search) {
		Optional<Board> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), search.getsBoard_num());
		return optionalBoard.orElse(null);
	}
	
	//댓글 전체 리스트 가져오기(글 한개에 대한)
	public List<Board_comment> selectCommentList(SearchVO search) {
		return _boardDao.selectByCommentList(QueryProperty.getQuery("board.selectCommentList"), search.getsBoard_num());
	}
	
	//댓글 카운트
	public int selectCommentCount(SearchVO search) {
		return _boardDao.selectByCommentCount(QueryProperty.getQuery("board.selectCommentCount"), search.getsBoard_num());
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
	public void update(Board_comment comment) {
		int row = _boardDao.updateComment(QueryProperty.getQuery("board.updateComment"), comment);
		if (row > 0) {
			System.out.println("반영된 댓글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 글 삭제
	public void delete(SearchVO search) {
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), search.getsBoard_num());
		if (row > 0) {
			System.out.println("삭제된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	//게시판 글 체크한것 삭제
	public void deleteChkbox(SearchVO search) {
		_boardDao.delete(QueryProperty.getQuery("board.deleteChk"), search.getsBNumStr());
	}
	
	//게시판 댓글 삭제
	public void deleteComment(SearchVO search) {
		int row = _boardDao.delete(QueryProperty.getQuery("board.deleteComment"), search.getsComment_num());
		if (row > 0) {
			System.out.println("삭제된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		
	}
	
}
