package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 자주 쓰는 문자열 파일로 읽어 오는 클래스
 * @author kky
 *
 */
public class CommonProperty {
	
	private static String boardPath = getProperty("boardPath");
	private static String jspPath = getProperty("jspPath");
	private static String memberPath = getProperty("memberPath");
	private static String write = getProperty("write");
	private static String update = getProperty("update");
	private static String findid = getProperty("findid");
	private static String findpwd = getProperty("findpwd");
	private static String messageFid = getProperty("messageFid");
	private static String messageFpwd = getProperty("messageFpwd");
	private static String messageMissid = getProperty("messageMissid");
	private static String messageMisspwd = getProperty("messageMisspwd");
	private static String messageLoginSuccess = getProperty("messageLoginSuccess");
	private static String messageMemMiss = getProperty("messageMemMiss");
	private static String messageUpdate = getProperty("messageUpdate");
	private static String messageWithdraw = getProperty("messageWithdraw");
	private static String messageExist = getProperty("messageExist");
	private static String messageInsertSuccess = getProperty("messageInsertSuccess");
	private static String messageLogout = getProperty("messageLogout");
	private static String alertmessage = getProperty("alertmessage");
	private static String alerthref = getProperty("alerthref");
	
	
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


	public static String getFindid() {
		return findid;
	}


	public static String getFindpwd() {
		return findpwd;
	}


	public static String getMessageFid() {
		return messageFid;
	}


	public static String getMessageFpwd() {
		return messageFpwd;
	}


	public static String getMessageMissid() {
		return messageMissid;
	}


	public static String getMessageMisspwd() {
		return messageMisspwd;
	}


	public static String getMessageLoginSuccess() {
		return messageLoginSuccess;
	}

	public static String getMessageUpdate() {
		return messageUpdate;
	}


	public static void setMessageUpdate(String messageUpdate) {
		CommonProperty.messageUpdate = messageUpdate;
	}


	public static String getMessageMemMiss() {
		return messageMemMiss;
	}


	public static String getAlertmessage() {
		return alertmessage;
	}


	public static String getAlerthref() {
		return alerthref;
	}


	public static String getMessageWithdraw() {
		return messageWithdraw;
	}


	public static String getMessageExist() {
		return messageExist;
	}


	public static String getMessageInsertSuccess() {
		return messageInsertSuccess;
	}


	public static String getMessageLogout() {
		return messageLogout;
	}


}
