package framework;

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
			instance = new ExtentReports(System.getProperty("user.dir") + "/Results/HTML/"+dateFormat.format(date)+"SprintestHTMLReport.html");
		}
		
		return instance;
	}
}
