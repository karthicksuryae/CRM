package GenericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtility {

	private Properties prop;

	public PropertyFileUtility() throws IOException {

		FileInputStream fis = new FileInputStream("./src/test/resources/CommonData.properties");
		prop = new Properties();
		prop.load(fis);
		fis.close();
	}

	public String getDataFromPropertyFile(String key) {
		return prop.getProperty(key);
	}
}
