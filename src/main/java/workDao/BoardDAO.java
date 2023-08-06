package workDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import workDto.Board;

public interface BoardDAO {
	
	List<Board> selectByBoardList(String sql);
	List<Board> selectByIdBoardList(String sql, String id);
	Optional<Board> selectByBoardNum(String sql, int num);
	
	int insert(String sql, Board board);
	int update(String sql, Board board);
	int delete(String sql, int num);
	
	int updateViewCount(String sql, int num);

}
