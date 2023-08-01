package workDao;

import java.util.List;
import java.util.Optional;

import workDto.Member;

public interface MemberDAO {
	
	Object selectBySearch(String sql, String search);
	List<?> selectByList(String sql);
	Optional<Member> selectByMember(String sql, Member member);
	Optional<Member> selectByName(String sql, Member member);
	Optional<Member> selectByIdName(String sql, Member member);
	int selectByCount(String sql, String memberid);
	
	int insert(String sql, Member member);
	int update(String sql, Member member);
	int delete(String sql, Member member);
	

}
