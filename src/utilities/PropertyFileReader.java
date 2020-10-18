
/*
$Id: PropertyFileReader.java 190 2015-05-25 05:39:49Z 812203 $
Copyright 2012 iGATE GROUP OF COMPANIES. All rights reserved
(Subject to Limited Distribution and Restricted Disclosure Only.)
THIS SOURCE FILE MAY CONTAIN INFORMATION WHICH IS THE PROPRIETARY 
 INFORMATION of iGATE GROUP OF COMPANIES AND IS INTENDED FOR USE 
 ONLY BY THE ENTITY WHO IS ENTITLED TO AND MAY CONTAIN 
 INFORMATION THAT IS PRIVILEGED, CONFIDENTIAL, OR EXEMPT FROM         
 DISCLOSURE UNDER APPLICABLE LAW.
YOUR ACCESS TO THIS SOURCE FILE IS GOVERNED BY THE TERMS AND           
 CONDITIONS OF AN AGREEMENT BETWEEN YOU AND IGATE GROUP OF COMPANIES.                  
 The USE, DISCLOSURE REPRODUCTION OR TRANSFER OF THIS PROGRAM IS      
 RESTRICTED AS SET FORTH THEREIN.
*/

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
	

	/**
	 * Read Configuration Property file
	 * 
	 * @return value of the key
	 */
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

	/**
	 * Read Parallel Property file //used for Parallel execution
	 * 
	 * @return value of the key
	 */
	public String readPara(String key) {
		final Properties prop = new Properties();
		InputStream input = null;
		String getProp = null;
		try {
			try {
				input = new FileInputStream(strRelPath + "Configurations/parallel.properties");
				// load a properties file
				prop.load(input);
				// get the property value and print it out
				getProp = prop.getProperty(key);
			} catch (Exception e) {
				input = new FileInputStream(strRelPath + "Configs/parallel_Appium.properties");
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

	/**
	 * Read Parallel Property file //used for Parallel execution
	 * 
	 * @return value of the key
	 */
	public void writePara(String key, String val) {
		final Properties prop = new Properties();
		InputStream input = null;
		FileOutputStream out = null;
		String getProp = null;
		try {
 
			try {
				input = new FileInputStream(strRelPath + "Configurations/parallel.properties");
				// load a properties file
				prop.load(input);
				out = new FileOutputStream(strRelPath + "Configurations/parallel.properties");
				prop.setProperty(key, val);
				// Store to Store Parameter file
				prop.store(out, "This is an optional header comment string");
			} catch (Exception e) {
				input = new FileInputStream(strRelPath + "Configs/parallel_Appium.properties");
				// load a properties file
				prop.load(input);
				out = new FileOutputStream(strRelPath + "Configs/parallel_Appium.properties");
				prop.setProperty(key, val);
				// Store to Store Parameter file
				prop.store(out, "This is an optional header comment string");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
