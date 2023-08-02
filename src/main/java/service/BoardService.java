package service;

import java.util.List;
import java.util.Optional;

import workDao.BoardDB;
import workDto.Board;

public class BoardService {
	String sql = "";

	private BoardDB _boardDao;
	
	public BoardService(BoardDB boardDB) {
		_boardDao = boardDB;
	}
	
	//수정 필요
	public List<Board> selectByMain() {
		sql = "select * from (select rownum as num, b.board_num, b.title from ("
				+ "select board_num, title from board where board_code = 20 order by board_num desc) b"
				+ ") where num <= 4";
		return _boardDao.selectByBoardList(sql);
	}

	public List<Board> selectByBoardList() {
		sql = "select * from board order by board_num desc";
		return _boardDao.selectByBoardList(sql);
	}
	
	public List<Board> selectByIdBoardList(String id) {
		sql = "select * from board where mem_id = ?";
		return _boardDao.selectByIdBoardList(sql, id);
	}
	
	public List<Board> selectByBoardCode(int code) {
		sql = "select * from board where board_code = ?";
		return _boardDao.selectByBoardCode(sql, code);
	}
	
	public Optional<Board> selectByBoardNum(int num) {
		sql = "select * from board where board_num = ?";
		return _boardDao.selectByBoardNum(sql, num);
	}
	
	
	public void insert(Board board) {
		sql = "insert into board(board_num, mem_id, mem_name, title, content, board_code)"
				+ "values(?, ?, ?, ?, ?, ?)";
		_boardDao.insert(sql, board);
	}
	public void update(Board board) {
		sql = "update board set title=?, content=?, mod_date=? where board_num = ?";
		_boardDao.update(sql, board);
	}
	public void delete(int num) {
		sql = "delete from board where board_num = ?";
		_boardDao.delete(sql, num);
	}
	
	public void updateViewCount(int num) {
		sql = "";
		_boardDao.updateViewCount(sql, num);
	}
	
}
