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
	
	
	public List<Board> selectByMainNotice() {
		sql = "select * from board "
			+ "where rownum <= 4 and board_code=20 and fixed_yn='Y' "
			+ "order by board_num desc";
		return _boardDao.selectByBoardList(sql);
	}
	
	public List<Board> selectByMainNomal() {
		sql = "select * from board "
			+ "where rownum <= 4 and board_code=10 "
			+ "order by board_num desc";
		return _boardDao.selectByBoardList(sql);
	}
	
	public List<Board> selectByNoticeList() {
		sql = "select /*+INDEX_desc(board PK_BOARD) */ board_num, mem_id, title, content, reg_date, mod_date, view_count, board_code, fixed_yn "
				+ "from board where board_code = 20 and fixed_yn='Y' "
				+ "union all "
				+ "select /*+INDEX_desc(board PK_BOARD) */ "
				+ "board_num, mem_id, title, content, reg_date, mod_date, view_count, board_code, fixed_yn "
				+ "from board where board_code = 20 and fixed_yn='N' ";
		return _boardDao.selectByBoardList(sql);
	}

	
	public List<Board> selectByNomalList() {
		sql = "select /*+INDEX_desc(board PK_BOARD) */ board_num, mem_id, title, content, reg_date, mod_date, view_count, board_code, fixed_yn "
				+ "from board where board_code=20 and fixed_yn='Y' "
				+ "union all\r\n"
				+ "select /*+ index_desc(board PK_BOARD) */ "
				+ "board_num, mem_id, title, content, reg_date, mod_date, view_count, board_code, fixed_yn "
				+ "from board "
				+ "where board_code = 10";
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
		sql = "insert into board(board_num, mem_id, title, content, board_code, fixed_yn)"
				+ "values(?, ?, ?, ?, ?, ?)";
		_boardDao.insert(sql, board);
	}
	public void update(Board board) {
		sql = "update board set title=?, content=?, mod_date=?, board_code=?, fixed_yn=? where board_num = ?";
		_boardDao.update(sql, board);
	}
	public void delete(int num) {
		sql = "delete from board where board_num = ?";
		_boardDao.delete(sql, num);
	}
	
	public void updateViewCount(int num) {
		sql = "update board set view_count = view_count+1 where board_num =?";
		_boardDao.updateViewCount(sql, num);
	}
	
}
