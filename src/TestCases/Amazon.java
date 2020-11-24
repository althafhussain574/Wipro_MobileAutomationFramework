package TestCases;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import utilities.PropertyFileReader;
import framework.BaseTestCase;
import framework.CommonPage;
import Pages.*;

public class Amazon extends BaseTestCase{
	CommonPage oND ;
	
	@BeforeTest 
	public void startDriver() 
	{
		oND= new CommonPage(driver);
	}
	
	PropertyFileReader prop = new PropertyFileReader();

	@SuppressWarnings("deprecation")
	@Test()
	public void test() throws Exception
	{
		try
		{
			oND= new CommonPage(driver);
			oND.setPage("HomePage");
			oND.wait("MW");
			LoginPage.Login(oND,prop.readPropFile("email"),prop.readPropFile("password"));
			oND.setPage("SearchPage");
			SearchPage.searchItem(oND,prop.readPropFile("product"));
			String actual_data=oND.elementGettext("product_title");
			CartPage.product_cart(oND);
			String expected_data = oND.elementGettext("product_title");
			oND.assertEqual(actual_data,expected_data);
			oND.wait("MW");
		}
		catch(Exception e)
		{
			Assert.assertNotNull(e);
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
