package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import workDto.Member;


public class FileUpdate {
	
	
	public List<Member> loadFileUserList() throws Exception {
		String fileName = "c:\\temp\\member.db";
		File file = new File(fileName);
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
	
		List<Member> memberList = (List<Member>) ois.readObject();
		memberList.stream().forEach(member -> System.out.println("로드했을때 = " + member));
		ois.close();
		return memberList;
	}
	
	public void saveFileuserList(List<Member> mem) throws Exception {
		String fileName = "c:\\temp\\member.db";
		File file = new File(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		oos.writeObject(mem);
		oos.close();
		
		mem.stream().forEach(member -> System.out.println("저장했을때 = " + member));
		
	}
	
	

}
