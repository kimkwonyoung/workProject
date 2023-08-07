package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Utils.CommonProperty;
import Utils.QueryProperty;
import workDao.MemberDB;
import workDto.Member;
import workDto.SearchVO;

public class MemberService {
	private MemberDB _dao;
	
	public MemberService(MemberDB memberdb) {
		_dao = memberdb;
	}

	public List<Member> selectByList() {
		return _dao.selectByList(QueryProperty.getQuery("member.selectMemList"));
	}
	
	public Member selectByMember(SearchVO search) {
		return _dao.selectBySearch(QueryProperty.getQuery("member.selectMemid"), search);
	}
	
	public Map<String, Object> selectByMember(Member mem) {
		Member member = new Member();
		SearchVO search = new SearchVO();
		Optional<Member> optionalMember = _dao.selectByMember(QueryProperty.getQuery("member.selectMember"), mem);
		Map<String, Object> map = new HashMap<>();
		
		if (optionalMember.isPresent()) {
			member = optionalMember.get();
			search.setMessage(CommonProperty.getMessageLoginSuccess());
			map.put("member", member);
			map.put("message", search);
		} else {
			search.setMessage(CommonProperty.getMessageMemMiss());
			map.put("member", member);
			map.put("message", search);
		}
		return map;
	}
	
	public String selectBySearch(Member mem, SearchVO search) {
		Member member = new Member();
		String message = "";
		if (search.getChkMem().equals(CommonProperty.getFindid())) {
			Optional<Member> optionalMember = _dao.selectByName(QueryProperty.getQuery("member.selectName"), mem);
			if (optionalMember.isPresent()) {
				member = optionalMember.get();
				message = CommonProperty.getMessageFid() + member.getMemberid();
			} else {
				message = CommonProperty.getMessageMissid();
			}
		} else if (search.getChkMem().equals(CommonProperty.getFindpwd())) {
			Optional<Member> optionalMember = _dao.selectByIdName(QueryProperty.getQuery("member.selectIdName"), mem);
			if (optionalMember.isPresent()) {
				member = optionalMember.get();
				message = CommonProperty.getMessageFpwd() + member.getPwd();
			} else {
				message = CommonProperty.getMessageMisspwd();
			}
		}
		return message;
	}
	
	
	public int selectByCount(String memberid) {
		return _dao.selectByCount(QueryProperty.getQuery("member.selectCount"), memberid);
	}
	
	public String insert(Member mem) {
		String message = "";
		int count = _dao.selectByCount(QueryProperty.getQuery("member.selectCount"), mem.getMemberid());
		
		if (count > 0) {
			message = CommonProperty.getMessageExist();
		} else {
			int row = _dao.insert(QueryProperty.getQuery("member.insert"), mem);
			if (row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
			message = CommonProperty.getMessageInsertSuccess();
		}
		return message;
	}
	
	public Member update(Member mem) {
		int row = _dao.update(QueryProperty.getQuery("member.update"), mem);
		if (row > 0) {
			System.out.println("반영된 행의 수 : " + row);
		} else {
			System.out.println("반영 X");
		}
		SearchVO search = new SearchVO();
		search.setsMemid(mem.getMemberid());
		
		return _dao.selectBySearch(QueryProperty.getQuery("member.selectMemid"), search);
	}
	
	public Map<String, Object> delete(Member mem) {
		String message = "";
		Map<String, Object> map = new HashMap<>();
		
		Optional<Member> optionalMamber = _dao.selectByMember(QueryProperty.getQuery("member.selectMember"), mem);
		if (optionalMamber.isPresent()) {
			Member member = optionalMamber.get();
			message = CommonProperty.getMessageWithdraw();
			map.put("status", true);
			int row = _dao.delete(QueryProperty.getQuery("member.delete"), member);
			if (row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
		} else {
			message = CommonProperty.getMessageMemMiss();
			map.put("status", false);
		}
		map.put("message", message);
		return map;
	}
	
}
