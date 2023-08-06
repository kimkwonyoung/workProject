package workDto;

import Utils.CommonProperty;

/**
 * Param값들 정리
 * @author kky
 *
 */
public class SearchVO {
	
	private String chk; //게시판 글쓰기 타입 ex)write : 글쓰기, update : 글수정
	private String sMemid; //게시판 글 작성자
	
	private int sBoard_num; // PK 게시판 번호(request로 넘어온 값 : String) 
	private int sBoard_code; // 게시판 코드 10 : 일반 게시판 , 20 : 공지사항 게시판
	
	public int getsBoard_num() {
		return sBoard_num;
	}

	public void setsBoard_num(String sBoard_num) {
		this.sBoard_num = Integer.parseInt(sBoard_num);
	}

	public int getsBoard_code() {
		return sBoard_code;
	}

	public void setsBoard_code(String sBoard_code) {
		this.sBoard_code = Integer.parseInt(sBoard_code);
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getsMemid() {
		return sMemid;
	}

	public void setsMemid(String sMemid) {
		this.sMemid = sMemid;
	}

}
