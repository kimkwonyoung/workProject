package service;

import java.util.List;
import java.util.Optional;

import workDao.MemberDB;
import workDto.Member;

public class MemberService {
	String sql = "";
	
	private MemberDB _dao;
	
	public MemberService(MemberDB memberdb) {
		_dao = memberdb;
	}

	public List<Member> selectByList() {
		sql = "select memberid, name, pwd, phone from Member";
		return _dao.selectByList(sql);
	}
	
	public Member selectBySearch(String search) {
		sql = "select memberid, name, pwd, phone from Member where memberid=?";
		return (Member) _dao.selectBySearch(sql, search);
	}
	
	public Optional<Member> selectByMember(Member mem) {
		sql = "select memberid, name, pwd, phone from Member where memberid=? and pwd=?";
		return _dao.selectByMember(sql, mem);
	}
	
	public Optional<Member> selectByName(Member mem) {
		sql = "select memberid, name, pwd, phone from Member where name=? and phone=?";
		return _dao.selectByName(sql, mem);
	}
	
	public Optional<Member> selectByIdName(Member mem) {
		sql = "select memberid, name, pwd, phone from Member where memberid=? and name=?";
		return _dao.selectByIdName(sql, mem);
	}
	
	
	public int selectByCount(String memberid) {
		sql = "select count(*) from member where memberid=?";
		return _dao.selectByCount(sql, memberid);
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
