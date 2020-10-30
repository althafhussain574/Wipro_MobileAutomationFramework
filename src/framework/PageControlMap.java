package framework;

import io.appium.java_client.AppiumDriver;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.PropertyFileReader;


public class PageControlMap {

	static Map<String, Map> objectInfo;
	private Formatter formatter = format();
	private AppiumDriver driver;
	private WebElement currObj;
	protected String platform;

	public PageControlMap(AppiumDriver driver, Map<String, Map> objectInfo, String platform) {
		this.objectInfo = objectInfo;
		this.driver = driver;
		final PropertyFileReader prop = new PropertyFileReader();
		this.platform = platform;
	}


	public WebElement getControl(String ctrlName) {
		final Map ctrlInfo = objectInfo.get(ctrlName);
		if (ctrlInfo == null)
			throwError(formatter.format("invalid control name:" + ctrlName).toString());
		return getControl(ctrlInfo);
	}

	private WebElement getControl(Map ctrlInfo) {
		String className = null;
		
		final String parentName = (String) ctrlInfo.get("Parent");
		if (parentName != null) {
			return getControlParent(ctrlInfo, className);
		}
		final String getID = (String) ctrlInfo.get("Id");
		if (getID != null) { // connect with Index
			boolean txt_Flag = false;
			WebElement indexEle = null;
			final String getIndex = (String) ctrlInfo.get("Index");
			final String gettxt = (String) ctrlInfo.get("Text");

			if (getID.contains("grid")) {
				final List<WebElement> dd = driver.findElements(By.id(getID));
				for (int ix = 0; ix < dd.size(); ix++) {
					if (ctrlInfo.get("Index").toString().equals(Integer.toString(ix))) {
						indexEle = dd.get(ix);
						txt_Flag = true;
						break;
					}
				}
				if (txt_Flag == true) {
					return indexEle;
				}
			} else if (getIndex != null) {
				final int getIntdex = Integer.parseInt(getIndex);
				final WebElement getText = (WebElement) driver.findElementsById(getID).get(getIntdex);
				return getText;
			} else if (gettxt != null) {
				final List<WebElement> getDisplayName = driver.findElementsById(getID);
				WebElement getEle = null;
				for (WebElement getDName : getDisplayName) {
					if (getDName.getText().contains(gettxt)) {
						getEle = getDName;
						break;
					}
				}
				return getEle;
			} else {
				final WebElement getText = driver.findElement(By.id(getID));
				return getText;
			}
		}
		final String textName = (String) ctrlInfo.get("Text");
		if (textName != null) {
			// connect with Index
			final List<WebElement> getText = driver.findElementsByClassName(className);
			boolean txt_Flag = false;
			WebElement indexEle = null;
			for (int ix = 0; ix < getText.size(); ix++) {
				if (getText.get(ix).getText().equals(textName)) {
					indexEle = getText.get(ix);
					txt_Flag = true;
					break;
				}
			}
			if (txt_Flag == true) {
				return indexEle;
			}
			return null;
		}

		final String index = (String) ctrlInfo.get("Index");
		if (index != null) { // connect with Index
			final List<WebElement> idx = driver.findElementsByClassName(className);
			boolean idx_Flag = false;
			WebElement indexEle = null;
			for (int ix = 0; ix < idx.size(); ix++) {
				if (Integer.toString(ix).equals(index)) {
					indexEle = idx.get(ix);
					idx_Flag = true;
					break;
				}
			}
			if (idx_Flag == true) {
				return indexEle;
			}
			return null;
		}
		final String xpath = (String) ctrlInfo.get("Xpath");
		if (xpath != null) { // connect with Xpath
			final WebElement xpathEle = driver.findElementByXPath(xpath);
			return xpathEle;
		}
		final String ID = (String) ctrlInfo.get("Id");
		if (ID != null) { // connect with Xpath
			final WebElement IDEle = driver.findElement(By.id(ID));
			return IDEle;
		}
		return null;
	}

	private WebElement getControlParent(Map ctrlInfo, String className) {
		final String index = (String) ctrlInfo.get("Index");
		if (index != null) { // connect with Index
			final List<WebElement> idx = currObj.findElements(By.className("button"));
			boolean idx_Flag = false;
			WebElement indexEle = null;
			for (int ix = 0; ix < idx.size(); ix++) {
				if (Integer.toString(ix).equals(index)) {
					indexEle = idx.get(ix);
					idx_Flag = true;
					break;
				}
			}
			if (idx_Flag == true) {
				return indexEle;
			}
			return null;
		}
		final String textName = (String) ctrlInfo.get("Text");
		if (textName != null) { // connect with Index
			final List<WebElement> idx = driver.findElementsByClassName(className);
			boolean idx_Flag = false;
			WebElement indexEle = null;
			for (int ix = 0; ix < idx.size(); ix++) {
				if (Integer.toString(ix).equals(textName)) {
					indexEle = idx.get(ix);
					idx_Flag = true;
					break;
				}
			}
			if (idx_Flag == true) {
				return indexEle;
			}
			return null;
		}
		final String xpath = (String) ctrlInfo.get("Xpath");
		if (xpath != null) { // connect with Xpath
			final WebElement xpathEle = currObj.findElement(By.xpath(xpath));
			return xpathEle;
		}
		return null;
	}


	// Log Message format
	private Formatter format() {
		final StringBuffer buffer = new StringBuffer();
		final Formatter formatter = new Formatter(buffer, Locale.US);
		return formatter;
	}

	// Exception Handling
	private void throwError(String msg) {
		throwError(msg, null);
	}

	// private void throwError(String msg, Exception e=null) {
	private void throwError(String msg, Exception e) {
		throw new RuntimeException(msg, e);
	}

}
