package Pages;

import framework.NativeDriver;

public class LoginPage {

	public static void Login(NativeDriver oND,String uname,String pwd)
	{
		try
		{
		oND.touch("signin");
		oND.enterText("email", uname);
		oND.touch("continue");
		oND.enterText("password",pwd);
		oND.touch("login");
		oND.wait("SW");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
