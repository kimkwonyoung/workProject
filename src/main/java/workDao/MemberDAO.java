package workDao;

import java.util.List;
import java.util.Optional;

import workDto.Member;
import workDto.SearchVO;

public interface MemberDAO {
	
	List<?> selectByList(Member member);
	List<?> selectList(String sql);
	Object selectBySearch(String sql, SearchVO search);
	Optional<?> selectByMember(String sql, Member member);
	Optional<?> selectByName(String sql, Member member);
	Optional<?> selectByIdName(String sql, Member member);
	boolean selectByCount(String sql, String memberid) throws Exception;
	Member checkIdPwd(String sql, Member member) throws Exception;
	int insert(String sql, Member member);
	int update(String sql, Member member);
	int delete(String sql, Member member);
	
	boolean insert_ProcedureCall(Member member) throws Exception;
	boolean update_ProcedureCall(Member member) throws Exception;
	boolean delete_ProcedureCall(Member member) throws Exception;
	
	

}
