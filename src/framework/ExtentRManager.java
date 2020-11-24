package framework;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentRManager {
	public static ExtentReports instance;
	
	public static synchronized ExtentReports getInstance() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		 Date date = new Date();
		if (instance == null) {
			File filepath = new File(System.getProperty("user.dir") + "/Results/HTML/"+dateFormat);
			if(!filepath.exists())
			{
				filepath.mkdir();
			}
			instance = new ExtentReports(System.getProperty("user.dir") + "/Results/HTML/"+dateFormat.format(date)+"SprintestHTMLReport.html");
		}
		
		return instance;
	}
}
