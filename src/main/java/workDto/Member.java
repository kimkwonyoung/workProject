package workDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {
	
	private String memberid;
	private String name;
	private String pwd;
	private String phone;
	
	
}
