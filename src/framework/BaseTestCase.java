package framework;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.*;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utilities.PropertyFileReader;

public class BaseTestCase {

	 static DesiredCapabilities caps = new DesiredCapabilities();
	 static PropertyFileReader prop = new PropertyFileReader();
	 static Logger log = Logger.getLogger(BaseTestCase.class);
	 public static AppiumDriver driver=null;
	 public static RemoteWebDriver w_driver = null;
	 public static ObjectMap ObjMap;
	 public static String testname;
	 
	 @BeforeMethod
		public void beforeMethod(Method caller) {
			ExtentReportManager.startTest(caller.getName());
			testname=caller.getName();
		}

		@AfterMethod
		public void afterMethod(ITestResult result) throws IOException {
			if (result.isSuccess()) {
				ExtentReportManager.getTest().log(LogStatus.PASS, "Test passed");
			}
			else if (result.getStatus() == ITestResult.FAILURE) {
				ExtentReportManager.getTest().log(LogStatus.FAIL, "Test failed");
				ExtentReportManager.getTest().addScreenCapture("./ExtentReport/Screenshot");
				
			}
			else if (result.getStatus() == ITestResult.SKIP) {
				ExtentReportManager.getTest().log(LogStatus.SKIP, "Test skipped");
			}
			
			ExtentReportManager.endTest();
			ExtentRManager.getInstance().flush();
		}

		@AfterSuite
		public void afterSuite() {
			ExtentRManager.getInstance().flush();
		}

	
	 
	 @BeforeTest
	 public void setUp() throws MalformedURLException
	 {
		 if(prop.readPropFile("platform").equalsIgnoreCase("android"))
		 {
			 if (prop.readPropFile("appType").contains("web")) 
			 {
				 caps.setCapability("platformName", "ANDROID");
				 caps.setCapability("version",prop.readPropFile("version"));
				 caps.setCapability("deviceName",prop.readPropFile("deviceName"));
				 caps.setCapability("browserName", "chrome");
				 caps.setCapability("newCommandTimeout", 60 * 2);
				 w_driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub/"),caps);
				 w_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				 log.info("framework set for mobile web automation");
				 
			 }
			 else
			 {
				 caps.setCapability("platformName", "ANDROID");
				 caps.setCapability("deviceName", prop.readPropFile("deviceName"));
				 caps.setCapability("clearSystemFiles",true);
				 caps.setCapability("appPackage", prop.readPropFile("appPackage"));
				 caps.setCapability("appActivity", prop.readPropFile("appActivity"));
				 caps.setCapability(MobileCapabilityType.NO_RESET, "False");
				 driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),caps);
				 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				 log.info("framework set for mobile native/hybrid app automation");
			 }
		 }
		 else
		 {
			 log.info("framework is for only Android");
		 }
	 }
	
}
