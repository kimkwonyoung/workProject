package workDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board_comment {
	private int comment_num;
	private String mem_id;
	private int board_num;
	private String detail;
	private String reg_date;
	
}
