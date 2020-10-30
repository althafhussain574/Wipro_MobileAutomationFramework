package TestCases;

import org.testng.annotations.*;
import org.testng.annotations.Test;

import utilities.PropertyFileReader;
import framework.BaseTestCase;
import framework.NativeDriver;
import Pages.*;

public class Amazon extends BaseTestCase{
	NativeDriver oND ;
	
	@BeforeTest 
	public void startDriver() 
	{
		oND= new NativeDriver(driver);
	}
	
	PropertyFileReader prop = new PropertyFileReader();

	@Test()
	public void test() throws Exception
	{
		try
		{
			oND= new NativeDriver(driver);
			oND.setPage("HomePage");
			oND.wait("HW");
			LoginPage.Login(oND,prop.readPropFile("email"),prop.readPropFile("password"));
			oND.setPage("SearchPage");
			SearchPage.searchItem(oND,prop.readPropFile("product"));
			String data=oND.ElementGetText("product_title");
			oND.swipeDown("add_to_cart");
			oND.wait("HW");
			oND.touch("add_to_cart");
			oND.wait("MW");
			oND.assertEqual(data,"some text");
			oND.wait("MW");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.quit();
		}
		
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
}
