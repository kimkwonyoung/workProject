package workDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import Utils.SingletonConnectionHelper;
import workDto.Board;

public class BoardDB implements BoardDAO {
	
	private Connection conn = null;
	
	public BoardDB() {
		try {
			conn = SingletonConnectionHelper.getConnection("oracle");
			System.out.println("DB연결 확인 = " + conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Board> selectByBoardList(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardList = new ArrayList<>();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Board board = Board.builder()
							.board_num(rs.getInt(1))
							.mem_id(rs.getString(2))
							.title(rs.getString(3))
							.content(rs.getString(4))
							.reg_date(rs.getDate(5))
							.mod_date(rs.getDate(6))
							.view_count(rs.getInt(7))
							.board_code(rs.getInt(8))
							.fixed_yn(rs.getString(9))
							.build();
					
					boardList.add(board);
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		
		return boardList;
	}

	@Override
	public List<Board> selectByIdBoardList(String sql, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Board board = Board.builder()
							.board_num(rs.getInt(1))
							.mem_id(rs.getString(2))
							.title(rs.getString(3))
							.content(rs.getString(4))
							.reg_date(rs.getDate(5))
							.mod_date(rs.getDate(6))
							.view_count(rs.getInt(7))
							.board_code(rs.getInt(8))
							.fixed_yn(rs.getString(9))
							.build();
					
					boardList.add(board);		
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		return boardList;
	}
	

	@Override
	public Optional<Board> selectByBoardNum(String sql, int num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Optional<Board> optionBoard = Optional.empty();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Board board = Board.builder()
								.board_num(rs.getInt(1))
								.mem_id(rs.getString(2))
								.title(rs.getString(3))
								.content(rs.getString(4))
								.reg_date(rs.getDate(5))
								.mod_date(rs.getDate(6))
								.view_count(rs.getInt(7))
								.board_code(rs.getInt(8))
								.fixed_yn(rs.getString(9))
								.build();
				
				optionBoard = Optional.of(board);
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		return optionBoard;
	}

	@Override
	public int insert(String sql, Board board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getMem_id());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getBoard_code());
			pstmt.setString(5, board.getFixed_yn());
			
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		return row;
	}

	@Override
	public int update(String sql, Board board) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			java.sql.Date nowDate = new java.sql.Date(System.currentTimeMillis());
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setDate(3, nowDate);
			pstmt.setInt(4, board.getBoard_code());
			pstmt.setString(5, board.getFixed_yn());
			pstmt.setInt(6, board.getBoard_num());
			
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}

	@Override
	public int delete(String sql, int num) {
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}
	
	public void delete(String sql, String num) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			String[] numsArray = num.split(",");
			for (String numValue : numsArray) {
			    pstmt.setString(1, numValue); // 인덱스를 1로 고정
			    pstmt.addBatch(); // 배치로 추가
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
	}

	@Override
	public int updateViewCount(String sql, int num) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}


}
