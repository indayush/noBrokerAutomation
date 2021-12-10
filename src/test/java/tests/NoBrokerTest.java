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

	@Test(description = "Property Search on the homepage")
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

				String dynamicCityXpath = null;
				String initialCity = oNoBroker.citySelector.getText();

				if (!initialCity.equals(cityToSelect)) {

					oNoBroker.citySelector.click();
					Thread.sleep(1000);

					dynamicCityXpath = "(//div[text()='Buy']/parent::div/following::div//div[text()='" + cityToSelect
							+ "'])";
					WebElement selectCityElement = driver.findElement(By.xpath(dynamicCityXpath));
					selectCityElement.click();
					Thread.sleep(1000);

					Utilities.assertion(true, "Selected City = " + oNoBroker.citySelector.getText());
				}
			}

			// Enter locations on Search box

			oNoBroker.enterLocationValuesInSearchBoxViaAction(oNoBroker.inputHomepageSearch,
					Utilities.getConfigProperty("maladEastBefore"));
			oNoBroker.enterLocationValuesInSearchBoxViaAction(oNoBroker.inputHomepageSearch,
					Utilities.getConfigProperty("maladWestBefore"));

			/*
			 * oNoBroker.enterLocationValuesInSearchBoxViaRobot(oNoBroker.
			 * inputHomepageSearch, Utilities.getConfigProperty("maladEastBefore"));
			 */

			// Select the Apartment Type

			oNoBroker.selectorForApartmentType.click();
			Thread.sleep(2000);
			

			String[] bhkArray = Utilities.getConfigProperty("BHK").split(",");
			for (String s : bhkArray) {

				for (WebElement eApartmentType : oNoBroker.checkBoxesForApartmentTypes) {

//					System.out.println(e.getAttribute("value"));

					if (eApartmentType.getAttribute("value").equals("BHK" + s)) {
						eApartmentType.click();
						Utilities.debugMessage("Selected Apartment Type = " + "BHK" + s);
						Thread.sleep(1000);
					}

				}

			}

			oNoBroker.selectorForApartmentType.click();
			Thread.sleep(1000);

			// Click on Search Button
			Thread.sleep(2000);
			oNoBroker.buttonHomepageSearch.click();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(description = "Select the fourth Listing from search results page")
	public void selectFourthListing() {

		try {

			Thread.sleep(4000);

			int noOfListings = oNoBroker.cardsOfPropertyListing.size();

			Utilities.debugMessage("No. of Listings = " + noOfListings);

			int listingSelectCount = Integer.valueOf(Utilities.getConfigProperty("listingSelectCount"));

			Utilities.scrollToElement(driver, oNoBroker.cardsOfPropertyListing.get(listingSelectCount));
			oNoBroker.cardsOfPropertyListing.get(listingSelectCount).click();
			Thread.sleep(2000);
			
			
			for (String handle : driver.getWindowHandles()) {
//				System.out.println("Current Window Handle - " + handle);

				if (!driver.getWindowHandle().equals(handle)) {
					driver.switchTo().window(handle);
					Utilities.waitForPageLoaded(20);
					Utilities.debugMessage("Switched to Window Handle = " + handle);
				}
			}

			Utilities.scrollToElement(driver, oNoBroker.resultPageDescriptionSection);
			Thread.sleep(4000);

			String descriptionSize = oNoBroker.resultPageDescriptionSection.getText().toString();
			if (descriptionSize.length() > 0) {
				Utilities.assertion(true, "The description for the property is present");
			} else {
				Utilities.assertion(false, "The description for the property is present");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
