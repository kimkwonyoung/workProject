package workDao;

import java.util.List;
import java.util.Optional;

import workDto.Member;
import workDto.SearchVO;

public interface MemberDAO {
	
	List<?> selectByList(String sql);
	Object selectBySearch(String sql, SearchVO search);
	Optional<?> selectByMember(String sql, Member member);
	Optional<?> selectByName(String sql, Member member);
	Optional<?> selectByIdName(String sql, Member member);
	int selectByCount(String sql, String memberid);
	
	int insert(String sql, Member member);
	int update(String sql, Member member);
	int delete(String sql, Member member);
	
	

}
