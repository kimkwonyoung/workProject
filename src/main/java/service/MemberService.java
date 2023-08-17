package service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import Utils.CommonProperty;
import Utils.QueryProperty;
import workDao.MemberDAOImpl;
import workDto.Member;
import workDto.SearchVO;

/** 회원 비즈니스 로직
 * @author kky
 *
 */
public class MemberService {
	private MemberDAOImpl _dao;
	
	//회원 전체 목록
	public List<Member> selectByList() {
		return _dao.selectByList(QueryProperty.getQuery("member.selectMemList"));
	}
	
	//아이디를 Key로 회원 조회
	public Member selectByMember(SearchVO search) {
		return _dao.selectBySearch(QueryProperty.getQuery("member.selectMemid"), search);
	}
	
	//로그인
	public JSONObject login(Member mem) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Member loginMember = _dao.checkIdPwd(QueryProperty.getQuery("member.selectMemid"), mem);
		if (loginMember != null) {
			jsonObject.put("member", loginMember);
			jsonObject.put("status", true);
			jsonObject.put("message", CommonProperty.getMessageLoginSuccess());
		} else {
			jsonObject.put("status", false);
			jsonObject.put("message", CommonProperty.getMessageMemMiss());
		}
		return jsonObject;
	}
	
	//아이디 비밀번호 찾기
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
	
	//존재 하는 회원 카운트
	public JSONObject existMember(String memberid) throws Exception {
		JSONObject jsonResult = new JSONObject();
		if (_dao.selectByCount(QueryProperty.getQuery("member.selectCount"), memberid)) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageExist());
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessagePossibleId());
		}
		
		return jsonResult;
	}
	
	//회원 가입
	public JSONObject insert(Member mem) {
		JSONObject jsonResult = new JSONObject();
		
		if (_dao.insert(QueryProperty.getQuery("member.insert"), mem) == 1) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageInsertSuccess());
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", CommonProperty.getMessageExist());
		}
		
		return jsonResult;
	}
	
	//회원 정보 수정
	public JSONObject update(Member mem) {
		JSONObject jsonResult = new JSONObject();
		SearchVO search = new SearchVO();
		search.setsMemid(mem.getMemberid());
		int row = _dao.update(QueryProperty.getQuery("member.update"), mem);
		if (row > 0) {
			jsonResult.put("status", true);
			jsonResult.put("message", CommonProperty.getMessageUpdate());
			jsonResult.put("member", _dao.selectBySearch(QueryProperty.getQuery("member.selectMemid"), search));
		}
		
		return jsonResult;
	}
	
	//회원 탈퇴 
	public JSONObject delete(Member mem) {
		JSONObject jsonResult = new JSONObject();
		Member member;
		try {
			member = _dao.checkIdPwd(QueryProperty.getQuery("member.selectMemid"), mem);
			if (member != null) {
				jsonResult.put("status", true);
				jsonResult.put("message", CommonProperty.getMessageWithdraw());
				_dao.delete(QueryProperty.getQuery("member.delete"), member);
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", CommonProperty.getMessageMemMiss());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
}
