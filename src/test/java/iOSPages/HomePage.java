package iOSPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.iOSFindBy;
import pages.BasePage;
import utilities.Driver;

public class HomePage extends BasePage {
	
	@iOSFindBy(accessibility="Date Picker")
	private WebElement datePicker;

	public HomePage(Driver driver) {
		super(driver);
		PageFactory.initElements(driver.getDriver(), this);
	}
	
	/**
	 * Navigate to Date Picker Page
	 * @return
	 */
	public DatePickerPage navigateToDatePickerPage(){
		
		datePicker.click();
		
		return new DatePickerPage(driver);
	}

}
