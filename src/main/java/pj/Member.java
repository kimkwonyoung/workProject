package pj;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Member implements Serializable {
	private static final long serialVersionUID = -1036524153261734687L;
	
	private String uid;
	private String name;
	private String pwd;
	private String phone;
	
	public Member(String uid, String name, String pwd) {
		this.uid = uid;
		this.name = name;
		this.pwd = pwd;
	}
	public Member(String uid, String name) {
		this.uid = uid;
		this.name = name;
		
	}
	
	public Member(String uid, String name, String pwd, String phone) {
		this.uid = uid;
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
		return Objects.equals(uid, other.uid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}
}
