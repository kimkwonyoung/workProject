package workDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import workDto.Board;
import workDto.Board_comment;

public interface BoardDAO {
	
	List<?> selectByBoardList(String sql);
	List<?> selectByIdBoardList(String sql, String id);
	Optional<?> selectByBoardNum(String sql, int num);
	
	List<?> selectByCommentList(String sql, int num);
	int selectByCommentCount(String sql, int num);
	
	int insert(String sql, Board board);
	int update(String sql, Board board);
	int updateViewCount(String sql, int num);
	int delete(String sql, int num);
	
	int updateComment(String sql, Board_comment comment);
	int insertComment(String sql, Board_comment comment);

}
