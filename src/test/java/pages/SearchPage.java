package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class SearchPage extends BasePage{
	
	@FindBy(id="item_title")
	private WebElement itemTitle;

	public SearchPage(Driver driver) {
		super(driver);
		PageFactory.initElements(driver.getDriver(), this);
	}
	
	public void printItemName(){
		
		System.out.println(getText(itemTitle));
	}
}
