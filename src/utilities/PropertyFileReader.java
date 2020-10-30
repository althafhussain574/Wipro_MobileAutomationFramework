package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyFileReader {
	/*String strRelPath = PropertyFileReader.class.getProtectionDomain().getCodeSource().getLocation().getPath()
			.split("bin")[0].replace("%20", " ");*/
	final String strRelPath = "./";
	

	
	public String readPropFile(String key) {
		final Properties prop = new Properties();
		InputStream input = null;
		String getProp = null;

		try {
			try {
				//input = new FileInputStream(strRelPath+"./Configurations/configuration.properties");
				input = new FileInputStream("./Configurations/configuration.properties");
				// load a properties file
				prop.load(input);
				// get the property value and print it out
				getProp = prop.getProperty(key);

			} catch (Exception e) {
				//input = new FileInputStream(strRelPath+"./Configurations/configuration.properties");
				input = new FileInputStream("./Configurations/configuration.properties");
				// load a properties file
				prop.load(input);
				// get the property value and print it out
				getProp = prop.getProperty(key);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return getProp;
	}
	
	
}
