package core;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class Base {

	public static WebDriver driver = null;
	public static String chromeProperty = "webdriver.chrome.driver";
	public static String edgeProperty = "webdriver.edge.driver";
	public static String chromePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
			+ "Drivers" + File.separator + "chromedriver.exe";
	public static String edgePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
			+ "Drivers" + File.separator + "msedgedriver.exe";
	public static String devModeValueFromConfig = Utilities.getConfigProperty("DevModeEnabled");
	public static boolean isDevModeEnabled = false;

	@BeforeSuite
	public void setUpSuite() {
		try {

			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /f /im msedgedriver.exe");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@BeforeTest
	public void setUpTest() {
		try {
			System.out.println("BEFORE TEST");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void setUpClass() {

		try {
			System.out.println("BEFORE CLASS");

			String sBrowserType = Utilities.getConfigProperty("Browser");

			if (sBrowserType.equalsIgnoreCase("chrome")) {
				System.setProperty(chromeProperty, chromePath);
				driver = new ChromeDriver();
			} else if (sBrowserType.equalsIgnoreCase("edge")) {
				System.setProperty(edgeProperty, edgePath);
				driver = new EdgeDriver();
			}

			if (devModeValueFromConfig.equalsIgnoreCase("true")) {
				isDevModeEnabled = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void setUpMethod(ITestResult oITestResult) {
		try {
			System.out.println();
			System.out.println("________________________________________________________________________");
			System.out.println("BEFORE METHOD - " + oITestResult.getMethod().getMethodName());
			System.out.println("________________________________________________________________________");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void destroyMethod(ITestResult oITestResult) {
		try {

			System.out.println();
			System.out.println("________________________________________________________________________");
			System.out.println("AFTER METHOD - " + oITestResult.getMethod().getMethodName());
			System.out.println("________________________________________________________________________");
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void destroyClass() {

		try {

			System.out.println("AFTER CLASS");
			
			driver.close();
			driver.quit();

		} catch (Exception e) {
			 e.printStackTrace();
		}

	}

	@AfterTest
	public void destroyTest() {
		try {
			System.out.println("AFTER TEST");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void destroySuite() {
		try {
			System.out.println("AFTER SUITE");

			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /f /im msedgedriver.exe");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
