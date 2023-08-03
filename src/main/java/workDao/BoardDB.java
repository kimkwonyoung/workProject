package workDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		System.out.println("쿼리 확인 = " + sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardList = new ArrayList<>();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					boardList.add(new Board(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getDate(5),
								rs.getDate(6),
								rs.getInt(7),
								rs.getInt(8),
								rs.getString(9)
							));
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(rs);
		}
		for(Board board : boardList) {
			System.out.println("데이터값 = " + board);
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
					boardList.add(new Board(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getDate(5),
								rs.getDate(6),
								rs.getInt(7),
								rs.getInt(8),
								rs.getString(9)
							));
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
	public List<Board> selectByBoardCode(String sql, int code) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					boardList.add(new Board(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getDate(5),
								rs.getDate(6),
								rs.getInt(7),
								rs.getInt(8),
								rs.getString(9)
							));
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
				Board board = new Board(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDate(5),
						rs.getDate(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getString(9)
					);
				optionBoard = optionBoard.of(board);
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
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			int board_num = 0;
			String sql2 = "select board_num.nextval from dual";
			pstmt2 = conn.prepareStatement(sql2);
			rs = pstmt2.executeQuery();
			
			
			if (rs.next()) {
				board_num = rs.getInt(1);
			}
			System.out.println("입력된 게시판내용 값 = " + board);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, board.getMem_id());
			pstmt.setString(3, board.getTitle());
			pstmt.setString(4, board.getContent());
			pstmt.setInt(5, board.getBoard_code());
			pstmt.setString(6, board.getFixed_yn());
			
			row = pstmt.executeUpdate();
			
			if(row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
			SingletonConnectionHelper.close(pstmt2);
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
			
			if(row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
			
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
			
			if(row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}

	@Override
	public int updateViewCount(String sql, int num) {
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			row = pstmt.executeUpdate();
			
			if(row > 0) {
				System.out.println("반영된 행의 수 : " + row);
			} else {
				System.out.println("반영 X");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SingletonConnectionHelper.close(pstmt);
		}
		return row;
	}


}
