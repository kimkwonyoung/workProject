package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Utils.QueryProperty;
import Utils.StringUtil;
import workDao.BoardDB;
import workDto.Board;
import workDto.SearchVO;

/** 게시판 비즈니스 로직
 * @author kky
 *
 */
public class BoardService {
	final private BoardDB _boardDao;
	
	public BoardService(BoardDB boardDB) {
		_boardDao = boardDB;
	}
	
	public Map<String, List<Board>> selectByMainList() {
		Map<String, List<Board>> map = new HashMap<>();
		
		map.put("noticeList", _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectMainNotice")));
		map.put("nomalList", _boardDao.selectByBoardList(QueryProperty.getQuery("board.selectMainNomal")));
		return map;
	}
	
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
	
	
	public List<Board> selectByIdBoardList(SearchVO search) {
		return _boardDao.selectByIdBoardList(QueryProperty.getQuery("board.selectId"), search.getsMemid());
	}
	
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
	
	public Board selectKeyNum(SearchVO search) {
		Optional<Board> optionalBoard =  _boardDao.selectByBoardNum(QueryProperty.getQuery("board.selectNum"), search.getsBoard_num());
		return optionalBoard.orElse(null);
	}
	
	
	public void insert(Board board) {
		if (StringUtil.isEmpty(board.getFixed_yn())) board.setFixed_yn("N");
		
		int row = _boardDao.insert(QueryProperty.getQuery("board.insert"), board);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	public void update(Board board) {
		if (StringUtil.isEmpty(board.getFixed_yn())) board.setFixed_yn("N");
		
		int row = _boardDao.update(QueryProperty.getQuery("board.update"), board);
		if (row > 0) {
			System.out.println("반영된 글 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	public void delete(SearchVO search) {
		int row = _boardDao.delete(QueryProperty.getQuery("board.delete"), search.getsBoard_num());
		if (row > 0) {
			System.out.println("삭제된 갯수 : " + row);
		} else {
			System.out.println("반영 X");
		}
	}
	
	public void deleteChkbox(SearchVO search) {
		_boardDao.delete(QueryProperty.getQuery("board.deleteChk"), search.getsBNumStr());
	}
	
	
}
