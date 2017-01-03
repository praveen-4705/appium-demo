package iOSPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import utilities.Driver;

public class DatePickerPage extends BasePage {
	
	@FindBy(xpath="//UIAApplication[1]/UIAWindow[2]/UIAPicker[1]/UIAPickerWheel[2]")
	private WebElement hours;
	
	@FindBy(xpath="//UIAApplication[1]/UIAWindow[2]/UIAPicker[1]/UIAPickerWheel[3]")
	private WebElement minutes;
	
	@FindBy(xpath="//UIAApplication[1]/UIAWindow[2]/UIAPicker[1]/UIAPickerWheel[1]")
	private WebElement date;

	public DatePickerPage(Driver driver) {
		super(driver);
		PageFactory.initElements(driver.getDriver(), this);
	}
	
	public void selectDate(String dateText, String hoursText, String minutesText) throws InterruptedException{
		hours.sendKeys(hoursText);
		
		Thread.sleep(3000l);
		
		minutes.sendKeys(minutesText);
		
		Thread.sleep(3000l);
		
		date.sendKeys(dateText);
	}
}
