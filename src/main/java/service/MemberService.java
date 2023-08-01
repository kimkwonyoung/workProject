package service;

import java.util.List;

import workDao.MemberDao;
import workDto.Member;


/**
 * @author kky
 * 비즈니스 로직 구현 불가능(쿼리문만 적용)
 *
 */
public class MemberService {
	String sql = "";
	
	private MemberDao _dao;
	
	public MemberService(MemberDao memberDao) {
		_dao = memberDao;
	}

	public List<Member> selectByList() {
		sql = "select memberid, name, pwd, phone from Member";
		return _dao.selectByList(sql);
	}
	
	public Member selectBySearch(String search) {
		sql = "select memberid, name, pwd, phone from Member where memberid=?";
		return (Member) _dao.selectBySearch(sql, search);
	}
	
	public void insert(Member member) {
		sql = "insert into Member(memberid, name, pwd, phone) values(?,?,?,?)";
		_dao.insert(sql, member);
	}
	
	public void update(Member member) {
		sql = "update Member set pwd=?, name=?, phone=? where memberid=?";
		_dao.update(sql, member);
	}
	
	public void delete(Member member) {
		sql = "delete from Member where memberid=?";
		_dao.delete(sql, member);
	}
	
}
