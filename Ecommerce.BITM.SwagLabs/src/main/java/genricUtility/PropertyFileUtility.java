package genricUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//Utility class to read data from a properties file
public class PropertyFileUtility {
	
	public static String getDataFromProperty(String key) throws IOException
	{
		FileInputStream file = new FileInputStream("./src/test/resources/commonData.properties");
		Properties p = new Properties();
		p.load(file);
		return p.getProperty(key);
	}
}
