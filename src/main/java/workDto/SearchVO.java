package workDto;


/**
 * Param값들 정리
 * @author kky
 *
 */
public class SearchVO {
	private String board_type; // 게시판 타입 ex)nomal : 일반, notice : 공지사항

	public String getBoard_type() {
		return board_type;
	}

	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}

}
