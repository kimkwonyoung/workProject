package workDao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Utils.ConnectionUtil;
import workDto.Member;
import workDto.SearchVO;

public class MemberDAOImpl implements MemberDAO {
	
	
	@Override
	public List<Member> selectByList(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList();
		try {
			conn = ConnectionUtil.getConnection();
			
			String sql = "select * from ( "
					+  "    select * from member "
					+  "    where membernum != 0 ";
			if (member.getMembernum() != 0) {		
				sql += "    and membernum < ? ";
			}
				sql	+= "    order by membernum desc "
					+  " ) where rownum <= 10 ";
			
			
			
			pstmt = conn.prepareStatement(sql);
			
			if (member.getMembernum() != 0) {
				pstmt.setInt(1, member.getMembernum());
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Member mem = Member.builder()
							.memberid(rs.getString("memberid"))
							.name(rs.getString("name"))
							.pwd(rs.getString("pwd"))
							.phone(rs.getString("phone"))
							.membernum(rs.getInt("membernum"))
							.build();
					memberList.add(mem);
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return memberList;
	}
	
	@Override
	public Member selectBySearch(String sql, SearchVO search) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member mem = null;
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
		return mem;
	}
	
	@Override
	public Optional<Member> selectByMember(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
		return optionMem;
	}

	@Override
	public Optional<Member> selectByName(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
		return optionMem;
	}
	
	@Override
	public Optional<Member> selectByIdName(String sql, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Optional<Member> optionMem = Optional.empty();
		
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
		return optionMem;
	}
	
	@Override
	public boolean selectByCount(String sql, String memberid) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberid);
			rs = pstmt.executeQuery();
			
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			return count != 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
	}

	@Override
	public int insert(String sql, Member member) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberid());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPwd());
			pstmt.setString(4, member.getPhone());
			
			conn.setAutoCommit(false);
			int row = pstmt.executeUpdate();
			conn.commit();
			
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SQLIntegrityConstraintViolationException) {
				//가입 실패시 필요한 로직 처리
			}
			return 0;
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public int update(String sql, Member member) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPwd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getMemberid());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return row;
	}

	@Override
	public int delete(String sql, Member member) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberid());
			
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return row;
	}

	@Override
	public Member checkIdPwd(String sql, Member member) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Member loginMem = null;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberid());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginMem = Member.builder()
						.memberid(rs.getString(1))
						.name(rs.getString(2))
						.pwd(rs.getString(3))
						.phone(rs.getString(4))
						.build();
				if(!loginMem.isEqualPwd(member)) {
					loginMem = null;
				}
			}
			return loginMem;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public boolean insert_ProcedureCall(Member member) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "{call member_insert(?,?,?,?,?)}";
			cstmt = conn.prepareCall(sql);
			
			//3개 input, 1개 output 일반 타입
			
			cstmt.setString(1, member.getMemberid());
			cstmt.setString(2, member.getName());
			cstmt.setString(3, member.getPwd());
			cstmt.setString(4, member.getPhone());
			cstmt.registerOutParameter(5, Types.VARCHAR);
			
			cstmt.execute();
			
			String oracle_msg = (String) cstmt.getObject(5);
			System.out.println("oracle server message : " + oracle_msg);
			boolean result = false;
			if (oracle_msg.equals("SUCCESS")) result = true;
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.close(cstmt);
			ConnectionUtil.close(conn);
		}
		
	}

	@Override
	public boolean update_ProcedureCall(Member member) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "{call member_update(?,?,?,?,?)}";
			cstmt = conn.prepareCall(sql);
			
			//3개 input, 1개 output 일반 타입
			
			cstmt.setString(1, member.getName());
			cstmt.setString(2, member.getPwd());
			cstmt.setString(3, member.getPhone());
			cstmt.setString(4, member.getMemberid());
			cstmt.registerOutParameter(5, Types.VARCHAR);
			
			cstmt.execute();
			
			String oracle_msg = (String) cstmt.getObject(5);
			System.out.println("oracle server message : " + oracle_msg);
			boolean result = false;
			if (oracle_msg.equals("SUCCESS")) result = true;
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.close(cstmt);
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public boolean delete_ProcedureCall(Member member) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "{call member_delete(?,?)}";
			cstmt = conn.prepareCall(sql);
			
			//3개 input, 1개 output 일반 타입
			
			cstmt.setString(1, member.getMemberid());
			
			cstmt.execute();
			
			String oracle_msg = (String) cstmt.getObject(5);
			System.out.println("oracle server message : " + oracle_msg);
			
			boolean result = false;
			if (oracle_msg.equals("SUCCESS")) result = true;
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ConnectionUtil.close(cstmt);
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public List<?> selectList(String sql) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList();
		try {
			conn = ConnectionUtil.getConnection();
		
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Member mem = Member.builder()
							.memberid(rs.getString("memberid"))
							.name(rs.getString("name"))
							.pwd(rs.getString("pwd"))
							.phone(rs.getString("phone"))
							.membernum(rs.getInt("membernum"))
							.build();
					memberList.add(mem);
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return memberList;
	}


}
