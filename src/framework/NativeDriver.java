package framework;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import utilities.PropertyFileReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class NativeDriver{
	
	AppiumDriver driver;
	static Logger logger = Logger.getLogger(NativeDriver.class);
	public WebElement webEle;
	protected PageControlMap curPageInfo;
	private Map info;
	protected final ObjectMap om;
	public static ObjectMap objMap;
	static PropertyFileReader prop = new PropertyFileReader();
	public String time="2";
	public String value="";
	
	static {
		try {
			objMap = new ObjectMap(prop.readPropFile("objmapFilePath"),
					prop.readPropFile("objmapSheetName"));
		} catch (Exception e) {
			throw new RuntimeException("ObjectMap:" + e);
		}
	}
	
	public NativeDriver(AppiumDriver driver) 
	{
		this.driver = driver;
		this.om = objMap;
	}
	
	public boolean setPage(final String pageName) throws Exception {
						try {
							info = om.getPageMap(pageName);
							if (info == null) {
								throwError("Invalid pageName:" + pageName);
								return false;
							}
							curPageInfo = new PageControlMap(driver, info,"android");
							logger.info("SetPage - : " + pageName
									+ " - Successfull");
						} catch (ClassCastException e) {
							logger.error("SetPage - : " + pageName
									+ " - Failed");
							throwError("Invalid Page control type: %s",
									pageName.toString());
							return false;
						}
						return true;
			}


	public void touch(final String ctrlName) throws Exception
	{
		try {
			//get control of UI element
			webEle = getControl(ctrlName);
			webEle.click();
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "TouchSuccess")));
			ExtentTestManager.getTest().log(LogStatus.PASS, "Touch for the element ---"+ctrlName+" Success");
			
			logger.info("touch - " + ctrlName+ " - Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "TouchFailure")));
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Touch for the element ---"+ctrlName+" failuer");
			logger.error("touch - " + ctrlName + " - Failed");
			throwError("Invalid control type:" + ctrlName);
		}
		wait(time);
	}
	
	public void enterText(final String ctrlName, final String value) throws Exception {
						try {
							webEle = getControl(ctrlName);
							webEle.clear();
							webEle.sendKeys(value);
							ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "EntetTest success")));
							ExtentTestManager.getTest().log(LogStatus.PASS, "entertext for the element ---"+ctrlName+" Success");
							logger.info("enterText - in: " + ctrlName
									+ " value: " + value + " - Successfull");
							
							
						} catch (ClassCastException e) {
							ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "EntetTest failure")));
							ExtentTestManager.getTest().log(LogStatus.FAIL, "entertext for the element ---"+ctrlName+" failure");
							logger.error("enterText - in: " + ctrlName
									+ " value: " + value + " - Failed");
							throwError("Invalid control type: %s", ctrlName);
						}
		wait(time);
	}
	
	public String ElementGetText(String ctrlName)
			throws Exception {

						try {
							Map ctrlInfo = PageControlMap.objectInfo
									.get(ctrlName);
							String xpath = (String) ctrlInfo.get("Xpath");
							//System.out.println("xpath is: "+xpath); 
							
							value = driver.findElement(By.xpath(xpath))
									.getText();
							ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "element get success")));
							ExtentTestManager.getTest().log(LogStatus.PASS, "element get for the element ---"+ctrlName+" success");
							logger.info("elementGetText -" + ctrlName
									+ " - Successfull");
							

						} catch (Exception e) {
							ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(NativeDriver.capture(driver, "Element get failure")));
							ExtentTestManager.getTest().log(LogStatus.FAIL, "element get for the element ---"+ctrlName+" failure");
							logger.error("elementGetText  : " + ctrlName
									+ " : Failed ");
							throwErrors("Issue while Element get Text");
						}
						return value;
					}
	
	public void swipeDown(final String ctrlName) throws Exception
	{
		try {
			Map ctrlInfo = PageControlMap.objectInfo
					.get(ctrlName);
			String xpath = (String) ctrlInfo.get("Id");
			System.out.println("xpath is: "+xpath); 
			TouchAction action=null;
			Dimension dim= driver.manage().window().getSize();
			Double X_start =  (dim.getHeight() * 0.8);
			Double X_end =  (dim.getHeight() * 0.2);
			
			int scroll_start = X_start.intValue();
			int scroll_end = X_end.intValue();
			while(driver.findElementsById(xpath).size()==0)
			{
				action = new TouchAction(driver);
				action.press(0,scroll_start).waitAction(1000).moveTo(0,scroll_end).release().perform();
			}
			logger.info("scroll till the button is visible");
		}
		catch(Exception e)
		{
			logger.error("Scroll failed");
			e.printStackTrace();
		}
		wait(time);
	}
	
	public static  String capture(AppiumDriver driver, String screenShotName)
			throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		Date date = new Date();
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir")
				+ "\\Results\\HTML\\" + screenShotName
				+ dateFormat.format(date) + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);

		return dest;
	}
	
	public void wait(String time) throws Exception {
				try {
					
					Thread.sleep(S2Time(time));
					logger.info("wait - for " + time + " Successfull");
				} catch (ClassCastException e) {
					throwError("Issue while Waiting " + time + "secs");
				}
			}
	
	public int S2Time(String s) {
		final String LW = "2"; // LowWait
		final String MW = "5"; // MediumWait
		final String HW = "10"; // HighWait
		int wait = 0;
		//logger.info("S2Time - " + s + " - Successfull");
		if (isNumeric(s) == true) {
			wait = (Integer.parseInt(s) * 1000);
			return wait;
		} else {
			if (s.equalsIgnoreCase("LW")) {
				wait = (Integer.parseInt(LW) * 1000);
			} else if (s.equalsIgnoreCase("MW")) {
				wait = (Integer.parseInt(MW) * 1000);
			} else if (s.equalsIgnoreCase("HW")) {
				wait = (Integer.parseInt(HW) * 1000);
			}
			return wait;
		}
	}
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}
		
	
	public WebElement getControl(String ctrlName) throws Exception {
		final WebElement ctrl = fetchControl(ctrlName);
		if (ctrl == null) {
			throwError("Cant find control:" + ctrlName);
		}
		return ctrl;
	}

	// Fetch Control of the Properties form Mapping CSV.
	public WebElement fetchControl(String name) throws Exception {
		if (curPageInfo == null) 
		{
			throwError("current page not set");
		}
		return curPageInfo.getControl(name);
	}
	
	private void throwError(String msg) throws Exception {
		throwError(msg, null);
	}

	private void throwErrors(String msg) throws Exception {
		throwErrors(msg, null);
	}

	private void throwErrors(String msg, Object... e) throws Exception {
		final StringBuffer buffer = new StringBuffer();
		final Formatter formatters = new Formatter(buffer, Locale.US);
		buffer.setLength(0);
		logger.error("ThrowErrors - " + msg + " : " + e + " - Failed");
		throw new RuntimeException(msg, formatters.ioException());
	}
	
	private void throwError(String msg, Object... e) throws Exception {
		final StringBuffer buffer = new StringBuffer();
		final Formatter formatters = new Formatter(buffer, Locale.US);
		buffer.setLength(0);
		logger.error("ThrowErrors - " + msg + " : " + e + " - Failed");
		throw new RuntimeException(msg, formatters.ioException());
	}

	public void assertEqual(String data, String data2) {

		  Assert.assertEquals(data, data2);
		
	}
	
}
