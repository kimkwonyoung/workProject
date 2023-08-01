package workDao;

import java.util.List;

import workDto.Member;

public interface GeneralDAO {
	
	Object selectBySearch(String sql, String search);
	List<?> selectByList(String sql);
	
	int insert(String sql, Member member);
	int update(String sql, Member member);
	int delete(String sql, Member member);
	

}
