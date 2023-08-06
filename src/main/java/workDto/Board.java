package workDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	private int board_num;
	private String mem_id;
	private String title;
	private String content;
	private Date reg_date;
	private Date mod_date;
	private int view_count;
	private int board_code;
	private String fixed_yn;
	
	
	
	

}
