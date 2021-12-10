package core;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Utilities extends Base {
	public static FileReader file = null;

	public static String getConfigProperty(String key) {

		debugMessage("Fetching Config values for " + key);
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

		debugMessage("Waiting for element to be displayed");
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

	public static void assertion(boolean assertInputBoolean, String assertMessage) {

		try {
			if (assertInputBoolean == true) {
				Assert.assertEquals(true, assertInputBoolean, assertMessage);
			} else {
				Assert.assertEquals(true, assertInputBoolean, assertMessage);
			}

			Reporter.log(assertMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void openApp(WebDriver driver) {

		try {
			debugMessage("Opening URL = ");
			driver.manage().window().maximize();
			driver.get(Utilities.getConfigProperty("url"));
			debugMessage("Opening URL = " + Utilities.getConfigProperty("url"));
			waitForPageLoaded(30);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void debugMessage(String sDebugMessage) {
		try {
			if (isDevModeEnabled == true) {
				System.out.println("------------------------------------------------------------");
				System.out.println("DEBUG: " + sDebugMessage);
				System.out.println("------------------------------------------------------------");
				System.out.println();
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
			Thread.sleep(50);
		}
		Thread.sleep(2000);

	}

	public static void pressKeysViaRobot(String data) throws Exception {
		try {

			debugMessage("Pressing Keys via Robot for " + data);

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

	public static void scrollToElement(WebDriver driver, WebElement element) {

		try {

			int maxMsgLength = 50;

			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();

			debugMessage("Scrolling to element with text = "
					+ StringUtils.abbreviate(element.getText().toString(), maxMsgLength));
			Thread.sleep(4000);

		} catch (MoveTargetOutOfBoundsException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void waitForPageLoaded(int maxTimeOut) throws InterruptedException {
		final int max = maxTimeOut;
		Thread.sleep(1000);

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				debugMessage("Waiting for page load. Max Timeout = " + String.valueOf(max));
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver, maxTimeOut);
			wait.until(expectation);
		} catch (Throwable error) {
			assertion(false, "Timeout waiting for Page Load Request to complete");
		}
	}

	public static void clickElementByJS(WebElement element) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
