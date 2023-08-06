package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryProperty {
	
	private static Properties properties;
	
	
	static {
		properties = new Properties();
		
		try {
			InputStream input = QueryProperty.class.getClassLoader().getResourceAsStream("query.properties");
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getQuery(String key) {
		return properties.getProperty(key);
	}
	








	
	

}
