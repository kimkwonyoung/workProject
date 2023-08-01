package service;

import java.util.List;

import workDao.MemberDB;
import workDto.Member;

public class MemberService {
	String sql = "";
	
	private MemberDB _dao;
	
	public MemberService(MemberDB memberDao) {
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
