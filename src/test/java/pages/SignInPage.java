package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class SignInPage extends BasePage {
	
	@FindBy(id="skip_sign_in_button")
	private WebElement skipSign;

	public SignInPage(Driver driver) {
		super(driver);
		PageFactory.initElements(driver.getDriver(), this);
	}
	
	/**
	 * Click on Sign
	 * 
	 * @return
	 */
	public HomePage clickOnSkipSignIn(){
		
		// Click on Skip Sign In
		click(skipSign);
		
		// Return Home page Object
		return new HomePage(driver);
	}
}
