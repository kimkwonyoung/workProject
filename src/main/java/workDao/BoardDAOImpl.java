package workDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import Utils.ConnectionUtil;
import Utils.StringUtil;
import workDto.Board;
import workDto.Board_comment;

public class BoardDAOImpl implements BoardDAO {
	
	@Override
	public List<Board> selectByAddList(Board board) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "select * from ( "
					+  "    select * from board "
					+  "    where board_code = 10 ";
			if (board.getBoard_num() != 0) {
				sql += " and board_num < ? ";
			}
				sql += "    order by board_num desc "
					+  ") "
					+  "where rownum <= ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			if (board.getBoard_num() != 0) {
				pstmt.setInt(1, board.getBoard_num());
				pstmt.setInt(2, board.getNrow());
			} else {
				pstmt.setInt(1, board.getNrow());
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				boardList.add(Board.builder()
							  .board_num(rs.getInt("board_num"))
							  .mem_id(rs.getString("mem_id"))
							  .title(rs.getString("title"))
							  .content(rs.getString("content"))
							  .mod_date(rs.getString("mod_date"))
							  .board_code(rs.getInt("board_code"))
							  .fixed_yn(rs.getString("fixed_yn"))
							  .view_count(rs.getInt("view_count"))
							  .build());
			}
			
			return boardList;
			
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
	public int selectPageTotalCount(Board board) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int count = 0;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			String sql = "select count(*) from board ";
				   sql += " where board_code = ? ";
			if (StringUtil.isEmpty(board.getSearchTitle())) {
				sql += " and title like concat(concat('%', ?), '%')";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, board.getBoard_code());
			if (StringUtil.isEmpty(board.getSearchTitle())) {
				pstmt.setString(2, board.getSearchTitle());
			}
		
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			return count;
			
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
	public List<?> selectByPageList(Board board) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		
		try {
			conn = ConnectionUtil.getConnection();
			
			String sql = "select c.* from ( "
					+ "    select rownum nrow, b.* from ( "
					+ "        select /*+ index_desc(a PK_BOARD) */ a.* "
					+ "        from board a "
					+ "        where board_code = ? ";
					
					
			if (!StringUtil.isEmpty(board.getSearchTitle())) {
				sql += " and title like concat(concat('%', ?), '%')";	
			}
				sql += "        order by fixed_yn desc "
					+  "    ) b "
					+  "    where rownum <= " + board.getEndNo()
					+  " ) c "
					+  " where nrow between " + board.getStartNo() + " and " + board.getEndNo();
			
				
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoard_code());
			
			if (!StringUtil.isEmpty(board.getSearchTitle())) {
				pstmt.setString(2, board.getSearchTitle());
			}
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				boardList.add(Board.builder()
							  .nrow(rs.getInt("nrow"))
							  .board_num(rs.getInt("board_num"))
							  .mem_id(rs.getString("mem_id"))
							  .title(rs.getString("title"))
							  .content(rs.getString("content"))
							  .mod_date(rs.getString("mod_date"))
							  .board_code(rs.getInt("board_code"))
							  .fixed_yn(rs.getString("fixed_yn"))
							  .view_count(rs.getInt("view_count"))
							  .build());
			}
			
			return boardList;
			
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
	public List<Board> selectByPageRow(Board board) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			
			String sql = "select c.* from ( "
					+ "    select rownum nrow, b.* from ( "
					+ "        select /*+ index_desc(a PK_BOARD) */ a.* "
					+ "        from board a "
					+ "        where board_code = ? ";
				sql += "        order by fixed_yn desc "
					+  "    ) b "
					+  "    where rownum <= " + board.getEndNo()
					+  " ) c "
					+  " where nrow between " + board.getStartNo() + " and " + board.getEndNo() + " and nrow = + " + board.getEndNo();
			
				
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoard_code());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				boardList.add(Board.builder()
							  .nrow(rs.getInt("nrow"))
							  .board_num(rs.getInt("board_num"))
							  .mem_id(rs.getString("mem_id"))
							  .title(rs.getString("title"))
							  .content(rs.getString("content"))
							  .board_code(rs.getInt("board_code"))
							  .mod_date(rs.getNString("mod_date"))
							  .fixed_yn(rs.getString("fixed_yn"))
							  .build());
			}
			
			return boardList;
			
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
	public List<Board> selectByPageRow2(Board board) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			
			String sql = "select c.* from ( "
					+ "    select rownum nrow, b.* from ( "
					+ "        select /*+ index_desc(a PK_BOARD) */ a.* "
					+ "        from board a "
					+ "        where board_code = ? ";
				sql += "        order by fixed_yn desc "
					+  "    ) b "
					+  "    where rownum <= " + board.getEndNo()
					+  " ) c "
					+  " where nrow between " + board.getStartNo() + " and " + board.getEndNo() + " and nrow > + " + (board.getEndNo() - board.getCount());
			
				
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoard_code());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				boardList.add(Board.builder()
							  .nrow(rs.getInt("nrow"))
							  .board_num(rs.getInt("board_num"))
							  .mem_id(rs.getString("mem_id"))
							  .title(rs.getString("title"))
							  .content(rs.getString("content"))
							  .board_code(rs.getInt("board_code"))
							  .mod_date(rs.getNString("mod_date"))
							  .fixed_yn(rs.getString("fixed_yn"))
							  .build());
			}
			
			return boardList;
			
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
	public List<Board> selectByBoardList(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					Board board = Board.builder()
							.board_num(rs.getInt(1))
							.mem_id(rs.getString(2))
							.title(rs.getString(3))
							.content(rs.getString(4))
							.reg_date(rs.getString(5))
							.mod_date(rs.getString(6))
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		
		return boardList;
	}

	@Override
	public List<Board> selectByIdBoardList(String sql, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board> boardList = new ArrayList<>();
		
		try {
			conn = ConnectionUtil.getConnection();
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
							.reg_date(rs.getString(5))
							.mod_date(rs.getString(6))
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return boardList;
	}
	

	@Override
	public Optional<Board> selectByBoardNum(String sql, int num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Optional<Board> optionBoard = Optional.empty();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Board board = Board.builder()
								.board_num(rs.getInt(1))
								.mem_id(rs.getString(2))
								.title(rs.getString(3))
								.content(rs.getString(4))
								.reg_date(rs.getString(5))
								.mod_date(rs.getString(6))
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return optionBoard;
	}

	@Override
	public int insert(String sql, Board board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int row = 0;
		
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return row;
	}

	@Override
	public int update(String sql, Board board) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		try {
			java.sql.Date nowDate = new java.sql.Date(System.currentTimeMillis());
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return row;
	}

	@Override
	public int delete(String sql, int num) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return row;
	}
	
	public void delete(String sql, String num) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
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
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
	}

	@Override
	public int updateViewCount(String sql, int num) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
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
	public List<Board_comment> selectByCommentList(String sql, int num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Board_comment> commentList = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					Board_comment comment = Board_comment.builder()
											.comment_num(rs.getInt(1))
											.mem_id(rs.getString(2))
											.board_num(rs.getInt(3))
											.detail(rs.getString(4))
											.reg_date(rs.getString(5))
											.build();
					commentList.add(comment);
				} while(rs.next());
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return commentList;
	}

	@Override
	public int selectByCommentCount(String sql, int num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int count = 0;
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			} else {
				System.out.println("데이터 없음");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(conn);
		}
		return count;
	}

	@Override
	public int updateComment(String sql, Board_comment comment) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int row = 0;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parsedDate = sdf.parse(comment.getReg_date());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getDetail());
			pstmt.setTimestamp(2, timestamp);
			pstmt.setInt(3, comment.getComment_num());
			
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
	public int insertComment(String sql, Board_comment comment) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int row = 0;
		
		try {
			conn = ConnectionUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getMem_id());
			pstmt.setInt(2, comment.getBoard_num());
			pstmt.setString(3, comment.getDetail());
			row = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(pstmt);
			ConnectionUtil.close(rs);
			ConnectionUtil.close(conn);
		}
		return row;
	}



	

//	@Override
//	public Board_comment selectByComment(String sql, Board_comment comment) {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		Board_comment board_comment = new Board_comment();
//		try {
//			conn = ConnectionUtil.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, comment.getMem_id());
//			pstmt.setInt(2, comment.getBoard_num());
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				board_comment = Board_comment.builder()
//												.comment_num(rs.getInt(1))
//												.mem_id(rs.getString(2))
//												.board_num(rs.getInt(3))
//												.detail(rs.getString(4))
//												.reg_date(rs.getString(5))
//												.build();
//			} else {
//				System.out.println("데이터 없음");
//			}
//		} catch (Exception e) {
//			
//		} finally {
//			ConnectionUtil.close(pstmt);
//			ConnectionUtil.close(rs);
//			ConnectionUtil.close(conn);
//		}
//		return board_comment;
//	}
//	
	


}
