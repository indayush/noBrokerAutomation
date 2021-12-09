package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import core.Utilities;

public class NoBroker {

	WebDriver driver;

	public NoBroker(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[text()='Buy'])[1]")
	public WebElement buttonTabBuy;

	@FindBy(xpath = "//div[contains(@class,'prop-search-city-selector')]/div")
	public WebElement citySelector;

	@FindBy(xpath = "//div[text()='Buy']/parent::div/following::div//div[text()='Mumbai']")
	public List<WebElement> citySelectorWithTextMumbai;

	@FindBy(xpath = "(//div[text()='Buy']/parent::div/following::div//div[text()='Mumbai'])[2]")
	public WebElement mumbaiOptionInCitySelectorDropdownAfterClick;
	
	@FindBy(xpath = "//input[@id='listPageSearchLocality']")
	public WebElement inputHomepageSearch;

	@FindBy(xpath = "//button[text()='Search']")
	public WebElement buttonHomepageSearch;

	@FindBy(xpath = "//div[text()='Apartment Type']/parent::div/parent::div/following::div/div[@class=\"nb-checkbox checkbox\"]//input")
	public List<WebElement> checkBoxesForBHKTypes;

	@FindBy(xpath = "//div[text()='Apartment Type']/parent::div/parent::div/following::div/div[@class=\"nb-checkbox checkbox\"]//span")
	public List<WebElement> namesForBHKTypes;

	@FindBy(xpath = "//article/div[2]")
	public List<WebElement> cardsOnProperties;

	@FindBy(xpath = "//div[@id='description']")
	public WebElement resultPageDescriptionSection;

	public void openApp() {

		try {
			System.out.println("Launching Webpage URL");
			driver.manage().window().maximize();
			driver.get(Utilities.getConfigProperty("url"));
//			Utilities.waitForPageLoad(driver);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
