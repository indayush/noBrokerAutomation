package core;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Base {

	public static WebDriver driver = null;
	public static String chromeProperty = "webdriver.chrome.driver";
	public static String edgeProperty = "webdriver.edge.driver";
	public static String chromePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
			+ "Drivers" + File.separator + "chromedriver.exe";
	public static String edgePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
			+ "Drivers" + File.separator + "msedgedriver.exe";
	public static String devModeValueFromConfig= Utilities.getConfigProperty("DevModeEnabled");
	public static boolean isDevModeEnabled = false;
	
	
	@BeforeSuite
	public void setUp() {
		try {
			System.out.println("BEFORE SUITE");
			String sBrowserType = Utilities.getConfigProperty("Browser");

			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

			if (sBrowserType.equalsIgnoreCase("chrome")) {
				System.setProperty(chromeProperty, chromePath);
				driver = new ChromeDriver();
			} else if (sBrowserType.equalsIgnoreCase("edge")) {
				System.setProperty(edgeProperty, edgePath);
				driver = new EdgeDriver();
			}
			
			if(devModeValueFromConfig.equalsIgnoreCase("true")) {
				isDevModeEnabled = true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AfterSuite
	public void destroy() {
		try {
			System.out.println("AFTER SUITE");
			Thread.sleep(2000);
			driver.close();
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
