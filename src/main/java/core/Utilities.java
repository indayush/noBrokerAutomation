package core;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities extends Base {
	public static FileReader file = null;

	public static String getConfigProperty(String key) {

		Properties p = new Properties();

		try {
			file = new FileReader(System.getProperty("user.dir") + File.separator + "Resources" + File.separator
					+ "config.properties");
			p.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return p.getProperty(key);
	}

	public static boolean waitForElementToBeDisplayed(WebElement element, int maxTimeout) throws InterruptedException {

		boolean result = false;
		for (int i = 0; i < maxTimeout; i++) {
			Thread.sleep(1000);
			if (element.isDisplayed()) {
				result = true;
				break;
			}
		}

		return result;
	}

	public static boolean waitForPageLoad(WebDriver driver) throws InterruptedException {
		boolean isLoaded = false;
		int maxTimeout = 60;
		Thread.sleep(2000);

		try {
			ExpectedCondition<Boolean> pageLoadViaJS = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readystate").equals("complete");
				}
			};

			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver, maxTimeout);
			wait.until(pageLoadViaJS);
			isLoaded = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isLoaded;
	}

	public static void assertion(boolean assertInput, String assertMessage) {

		if (assertInput) {
			assertTrue(assertInput, assertMessage);
		} else {
			assertFalse(assertInput, assertMessage);
		}

	}

	public static void openApp(WebDriver driver) {

		try {
			driver.manage().window().maximize();
			driver.get(Utilities.getConfigProperty("url"));
			Utilities.waitForPageLoad(driver);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void debugMessage(String sDebugMessage) {
		try {
			if (isDevModeEnabled == true) {
				System.out.println("DEBUG: " + sDebugMessage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void pressKeysViaAction(WebElement element, String inputString) throws InterruptedException {

		for (char s : inputString.toCharArray()) {
			Actions action = new Actions(driver);
			action.sendKeys(element, Character.toString(s)).build().perform();

//			Actions actionProvider = new Actions(driver);
//			Action keydown = actionProvider.keyDown(Keys.CONTROL).sendKeys(Character.toString(s)).build();
//			keydown.perform();

			Thread.sleep(50);
		}
		Thread.sleep(1000);

	}

	public static void pressKeysViaRobot(String data) throws Exception {
		try {
			Robot robot = new Robot();
			Thread.sleep(2000);
			byte[] bytes = data.getBytes();
			for (byte b : bytes) {
				int code = b;
				if (code > 96 && code < 123) {
					code = code - 32;
					robot.delay(40);
					robot.keyPress(code);
					robot.keyRelease(code);
				} else if (code > 64 && code < 91) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(code);
					robot.keyRelease(code);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 92) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_BACK_SLASH);
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);
				} else if (code == 95) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 58) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 46) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_PERIOD);
					robot.keyRelease(KeyEvent.VK_PERIOD);
				} else {
					robot.delay(40);
					robot.keyPress(code);
					robot.keyRelease(code);
				}
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
