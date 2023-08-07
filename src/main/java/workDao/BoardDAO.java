package workDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import workDto.Board;

public interface BoardDAO {
	
	List<?> selectByBoardList(String sql);
	List<?> selectByIdBoardList(String sql, String id);
	Optional<?> selectByBoardNum(String sql, int num);
	
	int insert(String sql, Board board);
	int update(String sql, Board board);
	int delete(String sql, int num);
	
	int updateViewCount(String sql, int num);

}
