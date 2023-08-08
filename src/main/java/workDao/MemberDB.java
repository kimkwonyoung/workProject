package workDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Utils.SingletonConnectionHelper;
import workDto.Member;
import workDto.SearchVO;

public class MemberDB implements MemberDAO {
	private Connection conn = null;
	
	public MemberDB() {
		try {
			conn = SingletonConnectionHelper.getConnection("oracle");
			System.out.println("DB연결 확인 = " + conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Member> selectByList(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList();
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Member mem = Member.builder()
							.memberid(rs.getString(1))
							.name(rs.getString(2))
							.pwd(rs.getString(3))
							.phone(rs.getString(4))
							.build();
					memberList.add(mem);
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		return memberList;
	}
	
	@Override
	public Member selectBySearch(String sql, SearchVO search) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member mem = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, search.getsMemid());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mem = Member.builder()
						.memberid(rs.getString(1))
						.name(rs.getString(2))
						.pwd(rs.getString(3))
						.phone(rs.getString(4))
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return mem;
	}
	
	@Override
	public Optional<Member> selectByMember(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberid());
			pstmt.setString(2, member.getPwd());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member mem = Member.builder()
						.memberid(rs.getString(1))
						.name(rs.getString(2))
						.pwd(rs.getString(3))
						.phone(rs.getString(4))
						.build();
				optionMem = Optional.of(mem);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return optionMem;
	}

	@Override
	public Optional<Member> selectByName(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member mem = Member.builder()
								.memberid(rs.getString(1))
								.name(rs.getString(2))
								.pwd(rs.getString(3))
								.phone(rs.getString(4))
								.build();
				optionMem = Optional.of(mem);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return optionMem;
	}
	
	@Override
	public Optional<Member> selectByIdName(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberid());
			pstmt.setString(2, member.getName());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member mem = Member.builder()
						.memberid(rs.getString(1))
						.name(rs.getString(2))
						.pwd(rs.getString(3))
						.phone(rs.getString(4))
						.build();
				optionMem = Optional.of(mem);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return optionMem;
	}
	
	@Override
	public int selectByCount(String sql, String memberid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return count;
	}

	@Override
	public int insert(String sql, Member member) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberid());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPwd());
			pstmt.setString(4, member.getPhone());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}

	@Override
	public int update(String sql, Member member) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPwd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getMemberid());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}

	@Override
	public int delete(String sql, Member member) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberid());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}

	

	

	



}
