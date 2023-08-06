package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonProperty {
	
	private static String boardPath = getProperty("boardPath");
	private static String jspPath = getProperty("jspPath");
	private static String memberPath = getProperty("memberPath");
	private static String write = getProperty("write");
	private static String update = getProperty("update");
	
	
	public static String getProperty(String keyName) {
		Properties prop = new Properties();
		String value = "";
		try {
			InputStream input = CommonProperty.class.getClassLoader().getResourceAsStream("common.properties");
			prop.load(input);
			
			if(prop.getProperty(keyName) == null) return "";
			value = prop.getProperty(keyName).trim();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	public static String getBoardPath() {
		return boardPath;
	}

	public static String getJspPath() {
		return jspPath;
	}

	public static String getMemberPath() {
		return memberPath;
	}


	public static String getWrite() {
		return write;
	}

	public static String getUpdate() {
		return update;
	}
	

}
