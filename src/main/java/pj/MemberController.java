package pj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.jasper.compiler.ELFunctionMapper;

import Utils.CommonProperty;
import service.BoardService;
import service.MemberService;
import workDao.BoardDB;
import workDao.MemberDB;
import workDto.Member;
import workDto.SearchVO;

/**회원 서블릿
 * @author kky
 *
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet  {
	private static final long serialVersionUID = -8738546228574989741L;
	private MemberService _MemberService;
	private SearchVO search = new SearchVO();
	
	public void init() {
		_MemberService = new MemberService(new MemberDB());
    }
	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String action = requestURI.substring(contextPath.length() + "/member/".length());
        
        try {
			switch(action) {
				case "memberWrite" -> memberWrite(request, response);
				case "memberSearch" -> memberSearch(request, response);
				case "memberSearchMove" -> memberSearchMove(request, response);
				case "memberFavorite" -> memberFavorite(request, response);
				case "memberLogin" -> memberLogin(request, response);
				case "memberLogout" -> memberLogout(request, response);
				case "memberInfo" -> memberInfo(request, response);
				case "memberUpdateMove" -> memberUpdateMove(request, response);
				case "memberUpdate" -> memberUpdate(request, response);
				case "memberList" -> memberList(request, response);
				case "memberWithdrawMove" -> memberWithdrawMove(request, response);
				case "memberWithdraw" -> memberWithdraw(request, response);
				case "memberInsert" -> memberInsert(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	//회원 가입 수행
	public void memberInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Member member = Member.builder()
						.memberid(request.getParameter("memberid"))
						.pwd(request.getParameter("pwd"))
						.name(request.getParameter("name"))
						.phone(request.getParameter("phone"))
						.build();
		
		request.setAttribute(CommonProperty.getAlertmessage(), _MemberService.insert(member));
		
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "complete.jsp").forward(request, response);
	}
	
	//회원 로그인
	public void memberLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Member member = Member.builder()
						.memberid(request.getParameter("memberid"))
						.pwd(request.getParameter("pwd"))
						.build();
		
		Map<String, Object> map = new HashMap<>();
		
		//로그인 결과 값 = 성공시 Member값 + message, 실패시 message
		map = _MemberService.selectByMember(member);
		search = (SearchVO) map.get("message");
		HttpSession session = request.getSession();
		
		request.setAttribute(CommonProperty.getAlertmessage(), search.getMessage());
		request.setAttribute(CommonProperty.getAlerthref(), request.getContextPath() + "/main");
		session.setAttribute("loginMember", (Member) map.get("member"));
		
		request.getRequestDispatcher(CommonProperty.getJspPath() + "alertpage.jsp").forward(request, response);
	}
	
	//회원 로그아웃
	public void memberLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		
		request.setAttribute(CommonProperty.getAlertmessage(), CommonProperty.getMessageLogout());
		request.setAttribute(CommonProperty.getAlerthref(), request.getContextPath() + "/main");
		
		request.getRequestDispatcher(CommonProperty.getJspPath() + "alertpage.jsp").forward(request, response);
	}
	
	//회원 상세 정보
	public void memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		search.setsMemid(request.getParameter("memberid"));
//		Member member = _MemberService.selectByMember(search);
//		
//		request.setAttribute("findMember", member);
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_info.jsp").forward(request, response);
	}
	
	//회원 전체 목록
	public void memberList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Member> memberList = _MemberService.selectByList();
		
		request.setAttribute("memberlist", memberList);
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_list.jsp").forward(request, response);
	}
	
	
	//회원 아이디 찾기, 비밀번호 찾기
	public void memberSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		search.setChkMem(request.getParameter("chkMem")); //아이디 찾기, 비밀번호 찾기 구분자 ex) value= findid, findpwd
		search.setsMemid(request.getParameter("memberid")); //파라미터 아이디
		Member member = Member.builder()
						.memberid(request.getParameter("memberid"))
						.name(request.getParameter("name"))
						.phone(request.getParameter("phone"))
						.build();
		
		String message = _MemberService.selectBySearch(member, search);
		
		request.setAttribute("chkMem", search.getChkMem());
		request.setAttribute(CommonProperty.getAlertmessage(), message);
		
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "complete.jsp").forward(request, response);
	}
	
	//회원 정보 수정 수행
	public void memberUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Member member = Member.builder()
						.memberid(request.getParameter("memberid"))
						.pwd(request.getParameter("pwd"))
						.name(request.getParameter("name"))
						.phone(request.getParameter("phone"))
						.build();
		
		HttpSession session = request.getSession();
		request.getSession().removeAttribute("loginMember");
		
		request.setAttribute(CommonProperty.getAlertmessage(), CommonProperty.getMessageUpdate());
		session.setAttribute("loginMember", _MemberService.update(member));
		
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_info.jsp").forward(request, response);
	}
	
	//회원 탈퇴 수행
	public void memberWithdraw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Member member = Member.builder()
				.memberid(request.getParameter("memberid"))
				.pwd(request.getParameter("pwd"))
				.build();
		
		map = _MemberService.delete(member);
		
		Boolean status = (Boolean) map.get("status");
		
		if (status) {
			//회원 탈퇴 성공
			request.getSession().invalidate();
			request.setAttribute(CommonProperty.getAlertmessage(), (String) map.get("message"));
			request.setAttribute(CommonProperty.getAlerthref(), request.getContextPath() + "/main");
			request.getRequestDispatcher(CommonProperty.getJspPath() + "alertpage.jsp").forward(request, response);
		} else {
			//회원 탈퇴 실패
			request.setAttribute(CommonProperty.getAlertmessage(), (String) map.get("message"));
			request.setAttribute(CommonProperty.getAlerthref(), CommonProperty.getMemberPath() + "member_withdraw.jsp");
			request.getRequestDispatcher(CommonProperty.getJspPath() + "alertpage.jsp").forward(request, response);
		}
	}
	
	//회원가입 폼 이동
	public void memberWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_write.jsp").forward(request, response);
	}
	
	//아이디 찾기, 비밀번호 찾기 폼 이동
	public void memberSearchMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("chkMem", request.getParameter("chkMem"));
		
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_search.jsp").forward(request, response);
	}
	
	//사진 보기 이동 (로직 존재X)
	public void memberFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "favorite.jsp").forward(request, response);
	}
	
	//정보 수정 폼 이동
	public void memberUpdateMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		search.setsMemid(request.getParameter("memberid"));
		
		request.setAttribute("memid", search.getsMemid());
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_update.jsp").forward(request, response);
	}
	
	//회원 탈퇴 폼 이동
	public void memberWithdrawMove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher(CommonProperty.getMemberPath() + "member_withdraw.jsp").forward(request, response);
	}
	

	
	
	
}
