package Pages;

import framework.BaseTestCase;
import framework.CommonPage;
import junit.framework.Assert;

public class CartPage extends BaseTestCase{

	public static void product_cart(CommonPage oND)
	{
		try
		{
		oND.swipeDown("add_to_cart");
		oND.touch("add_to_cart");
		oND.wait("SW");
		oND.touch("proceed_to_checkout");
		oND.wait("SW");
		oND.touch("proceed_to_buy");
		oND.touch("select_address");
		oND.touch("continue");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
}


