package TestCases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import org.testng.annotations.Test;

import utilities.PropertyFileReader;
import framework.BaseTestCase;
import framework.NativeDriver;

public class Amazon extends BaseTestCase{
	
	PropertyFileReader prop = new PropertyFileReader();

	@Test()
	public void test() throws Exception
	{
		try
		{
			NativeDriver oND = new NativeDriver(driver);
			oND.setPage("HomePage");
			oND.wait("HW");
			oND.touch("signin");
			oND.enterText("email", prop.readPropFile("email"));
			oND.touch("continue");
			oND.enterText("password",prop.readPropFile("password"));
			oND.touch("login");
			oND.wait("SW");
			oND.setPage("SearchPage");
			oND.enterText("search_box", prop.readPropFile("product"));
			((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.ENTER);
			oND.wait("MW");
			oND.touch("search_tv");
			oND.wait("MW");
			String data=oND.ElementGetText("product_title");
			System.out.println(data);
			oND.swipeDown("add_to_cart");
			oND.wait("HW");
			oND.touch("add_to_cart");
			oND.wait("MW");
			oND.assertEqual(data,data);
			oND.wait("MW");
			driver.quit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.quit();
		}
		
	}
	
}
