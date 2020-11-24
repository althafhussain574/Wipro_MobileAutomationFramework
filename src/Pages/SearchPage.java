package Pages;

import framework.CommonPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import framework.BaseTestCase;

public class SearchPage extends BaseTestCase{

	public static void searchItem(CommonPage oND,String item)
	{
		try
		{
			oND.enterText("search_box", item);
			((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.ENTER);
			oND.wait("MW");
			oND.touch("search_tv");
			oND.wait("MW");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
