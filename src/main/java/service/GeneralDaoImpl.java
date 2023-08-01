package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Utils.SingletonConnectionHelper;
import workDao.GeneralDAO;
import workDto.Member;

public class GeneralDaoImpl implements GeneralDAO {
	
	Connection conn = null;
	
	public GeneralDaoImpl() {
		try {
			conn = SingletonConnectionHelper.getConnection("oracle");
			System.out.println("DB연결 확인 = " + conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Object selectBySearch(String sql, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> selectByList(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList();
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
//			if(rs.next()) {
//				do {
//					memberList.add(new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
//				} while(rs.next());
//			} else {
//				System.out.println("데이터 없음");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		return memberList;
	}

	@Override
	public int insert(String sql, Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String sql, Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String sql, Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

}
