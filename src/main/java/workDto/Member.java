package workDto;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Member implements GeneralModel {
	
	private String memberid;
	private String name;
	private String pwd;
	private String phone;
	
	public Member(String memberid, String name, String pwd) {
		this.memberid = memberid;
		this.name = name;
		this.pwd = pwd;
	}
	public Member(String memberid, String name) {
		this.memberid = memberid;
		this.name = name;
		
	}
	
	public Member(String memberid, String name, String pwd, String phone) {
		this.memberid = memberid;
		this.name = name;
		this.pwd = pwd;
		this.phone = phone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(memberid, other.memberid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(memberid);
	}
}
