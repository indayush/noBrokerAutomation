package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import core.Base;
import core.Utilities;
import pages.NoBroker;

public class NoBrokerTest extends Base {

	NoBroker oNoBroker;

	@Test(description = "Validate Webpage Title")
	public void validateNoBrokerPageIsOpened() {
		try {
			System.out.println(this.getClass().getName());

			oNoBroker = new NoBroker(driver);
			oNoBroker.openApp();

			String pageTitle = driver.getTitle();
			Utilities.debugMessage("Webpage Title = " + pageTitle);

			if (pageTitle.equals(Utilities.getConfigProperty("title"))) {
				Utilities.assertion(true, "Page Title is matched as - " + pageTitle);
			} else {
				Utilities.assertion(false, "Page Title is matched as - " + pageTitle);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(description = "Validate Property Search"/* , dependsOnGroups = "validateNoBrokerPageIsOpened" */)
	public void validatePropertySearch() {

		try {
			oNoBroker = new NoBroker(driver);
			oNoBroker.openApp();

			// Clicking the But Tab Button
			oNoBroker.buttonTabBuy.click();

			// Checking if provided City is selected
			String cityToSelect = Utilities.getConfigProperty("City");

			Utilities.debugMessage("Pre Selected City = " + oNoBroker.citySelector.getText());
			Utilities.debugMessage("City To Select = " + cityToSelect);

			// Selecting the appropriate city dynamically
			if (oNoBroker.citySelector.getText().equalsIgnoreCase(cityToSelect)) {
				Utilities.assertion(true, "Selected City = " + cityToSelect);
			} else {

				@SuppressWarnings("unused")
				String dynamicXpath = null;
				String initialCity = oNoBroker.citySelector.getText();

				if (!initialCity.equals(cityToSelect)) {

					oNoBroker.citySelector.click();
					Thread.sleep(1000);

					dynamicXpath = "(//div[text()='Buy']/parent::div/following::div//div[text()='" + cityToSelect
							+ "'])";
					WebElement selectCityElement = driver.findElement(By.xpath(dynamicXpath));
					selectCityElement.click();
					Thread.sleep(1000);

					Utilities.assertion(true, "Selected City = " + oNoBroker.citySelector.getText());
				}
			}

			// Enter locations on Search box

			String location_1 = Utilities.getConfigProperty("maladEastBefore");
			String location_2 = Utilities.getConfigProperty("maladWestBefore");

//			oNoBroker.enterLocationValuesInSearchBoxViaAction(oNoBroker.inputHomepageSearch, location_1);
//
//			oNoBroker.enterLocationValuesInSearchBoxViaAction(oNoBroker.inputHomepageSearch, location_2);
//			oNoBroker.enterLocationValuesInSearchBoxViaRobot(oNoBroker.inputHomepageSearch, location_1);

			// Select the BHKs
			oNoBroker.selectorForBHKType.click();
			Thread.sleep(1000);
			
			String valueAttributeForBHKSelectorAfterSelectingAnyObject = "";
			
			
			String[] bhkArray = Utilities.getConfigProperty("BHK").split(",");
			for (String s : bhkArray) {

				for (WebElement e : oNoBroker.checkBoxesForBHKTypes) {
					
					if (e.getAttribute("value").equals("BHK" + s)) {
						System.out.println(e.getAttribute("value"));
						e.click();
						Thread.sleep(1000);
//						valueAttributeForBHKSelectorAfterSelectingAnyObject = s + " BHK" ;
//						String dynamicXpathForBHKSelector = "//div[text()='" + valueAttributeForBHKSelectorAfterSelectingAnyObject
//								+ "']/parent::div/parent::div";
//						WebElement tempBHKSelector = driver.findElement(By.xpath(dynamicXpathForBHKSelector));
//						tempBHKSelector.click();
					}
					Thread.sleep(100);
				}

			}
			
			
			// Click on Search Button
			Thread.sleep(2000);
			oNoBroker.buttonHomepageSearch.click();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 0)
	public void selectBuyPropertyOptionOnHomepage() {

	}

}
