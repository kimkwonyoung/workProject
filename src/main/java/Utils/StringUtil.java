package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	
	/** String 빈값, null 체크*/
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	/*
	 * String params = Stream.of(boardIds).collect(Collectors.joining("','", "('", "')"));
	 * */
	/**
	 * 날짜 객체를 형식에 맞는 문자열로 가져옵니다.
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/**
	 * yyyy-MM-dd 형식의 문자열을 Date 객체로 가져옵니다.
	 * @param input
	 * @return
	 */
	public static Date getDate(String input) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(input);
	}
	

}
