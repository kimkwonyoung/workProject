package pj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.jasper.compiler.ELFunctionMapper;

import service.MemberService;
import workDao.MemberDB;
import workDto.Member;

@WebServlet("/main")
public class MemberController extends HttpServlet  {
	
	MemberService _memberService;
	
	
	public MemberController() {
        _memberService = new MemberService(new MemberDB()); 
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		try {
			switch(action) {
				case "memberInsert" -> memberInsert(request, response);
				case "memberLogin" -> memberLogin(request, response);
				case "membersearch" -> membersearch(request, response);
				case "memberupdate" -> memberupdate(request, response);
				case "memberwithdraw" -> memberwithdraw(request, response);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		try {
			switch(action) {
				case "logout" -> logOut(request, response);
				case "memberInfo" -> memberInfo(request, response);
				case "memberlist" -> memberlist(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void memberInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _memberService.selectByList();
		
		String memberid = request.getParameter("memberid");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		System.out.println("입력된 이름 값 = " + name);
		
		
		
		boolean result = memberList.stream().noneMatch(m -> m.getMemberid().equals(memberid));
		String message;
		
		if (result) {
			_memberService.insert(new Member(memberid, name, pwd, phone));
			message = "회원 가입 완료";
			request.setAttribute("alertmessage", message);
		} else {
			System.out.println("이미 존재 하는 아디 확인");
			message = "이미 존재 하는 아이디 입니다.";
			request.setAttribute("alertmessage", message);
		}
		
		request.getRequestDispatcher("/member/complete.jsp").forward(request, response);
	}
	
	public void memberLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _memberService.selectByList();
		
		String memberid = request.getParameter("memberid");
		String pwd = request.getParameter("pwd");
		System.out.println("입력된 아이디 = " + memberid);
		System.out.println("입력된 비밀번호 = " + pwd);
		String message;
		Optional<Member> optionalMember = memberList.stream().filter(m -> m.getMemberid().equals(memberid) && m.getPwd().equals(pwd)).findFirst();
		HttpSession session = request.getSession();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE a", new Locale("ko"));
		String dateFormat = sdf.format(date);
		
		System.out.println("현재시각 = " + dateFormat);
		
		if (optionalMember.isPresent()) {
			System.out.println("로그인 성공 확인");
			message = "로그인 성공";
			request.setAttribute("alertmessage", message);
			session.setAttribute("dateFormat", dateFormat);
			session.setAttribute("loginMember", optionalMember.get());
		} else {
			System.out.println("아이디 비번 실패 확인");
			message = "아이디 또는 비밀번호가 잘못되었습니다";
			request.setAttribute("alertmessage", message);
		}
	
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}
	
	public void memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Member member = _memberService.selectBySearch(request.getParameter("memberid"));
		
		request.setAttribute("findMember", member);
		request.getRequestDispatcher("/member/member_info.jsp").forward(request, response);
	}
	
	public void memberlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _memberService.selectByList();
		
		request.setAttribute("memberlist", memberList);
		request.getRequestDispatcher("/member/member_list.jsp").forward(request, response);
	}
	

	public void membersearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _memberService.selectByList();
		String dis = request.getParameter("dis");
		String message = "";
		
		
		String name = request.getParameter("name");
		
		if (dis.equals("searchId")) {
			Optional<Member> optionalMember = memberList.stream().filter(m -> m.getName().equals(name)).findFirst();
			if (optionalMember.isPresent()) {
				Member findId = optionalMember.get();
				message = "찾으시는 아이디 = " + findId.getMemberid();
				request.setAttribute("alertmessage", message);
				request.setAttribute("findId", findId);
				
			} else {
				message = "이름이 잘못되었습니다";
				request.setAttribute("alertmessage", message);
			}
		} else if(dis.equals("searchPwd")) {
			Optional<Member> optionalMember = memberList.stream().filter(m -> m.getMemberid().equals(request.getParameter("memberid")) && m.getName().equals(name)).findFirst();
			if (optionalMember.isPresent()) {
				Member findPwd = optionalMember.get();
				message = "찾으시는 비밀번호 = " + findPwd.getPwd();
				request.setAttribute("alertmessage", message);
				request.setAttribute("findPwd", findPwd);
			} else {
				message = "아이디와 이름이 잘못되었습니다";
				request.setAttribute("alertmessage", message);
			}
		}
		
//		if (dis.equals("searchId")) {
//			if (member != null) {
//				message = "찾으시는 아이디 = " + member.getMemberid();
//				request.setAttribute("alertmessage", message);
//				request.setAttribute("findId", member.getMemberid());
//			} else {
//				message = "이름이 잘못되었습니다";
//				request.setAttribute("alertmessage", message);
//			}
//			
//		} else if(dis.equals("searchPwd")) {
//			member = _memberService.selectBySearch(request.getParameter("memberid"));
//			if (member != null) {
//				System.out.println("멤버 = " + member);
//				message = "찾으시는 비밀번호 = " + member.getPwd();
//				request.setAttribute("alertmessage", message);
//				request.setAttribute("findPwd", member.getPwd());
//			} else {
//				message = "아이디와 이름이 잘못되었습니다";
//				request.setAttribute("alertmessage", message);
//			}
//		}
		request.setAttribute("dis", dis);
		request.getRequestDispatcher("/member/complete.jsp").forward(request, response);
	}
	
	

	public void memberupdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		List<Member> memberList = _memberService.selectByList();
		Member member = _memberService.selectBySearch(request.getParameter("searchId"));
		
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String message = "";
		HttpSession session = request.getSession();
		request.getSession().removeAttribute("loginMember");
		
		member.setName(name);
		member.setPhone(phone);
		member.setPwd(pwd);
		_memberService.update(member);
		
		message = "정보 수정 완료";
		
		request.setAttribute("alertmessage", message);
		session.setAttribute("loginMember", member);
		
		request.getRequestDispatcher("/member/member_info.jsp").forward(request, response);
		
	}
	
	public void memberwithdraw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _memberService.selectByList();
		
		String memberid = request.getParameter("memberid");
		String pwd = request.getParameter("pwd");
		String message = "";
		
		Optional<Member> optionalMember = memberList.stream().filter(m -> m.getMemberid().equals(memberid) && m.getPwd().equals(pwd)).findFirst();
		
		if (optionalMember.isPresent()) {
			Member mem = optionalMember.get();
			_memberService.delete(mem);
			
			message = "회원 탈퇴 완료";
			request.getSession().invalidate();
			
			request.setAttribute("alertmessage", message);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		} else {
			message = "아이디 또는 비밀번호가 잘못되었습니다";
			request.setAttribute("alertmessage", message);
			
			request.getRequestDispatcher("/member/member_withdraw.jsp").forward(request, response);
		}
		
		
		
	}

	
}
