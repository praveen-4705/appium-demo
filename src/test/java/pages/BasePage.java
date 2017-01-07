package pages;

import org.openqa.selenium.WebElement;

import utilities.Driver;

public class BasePage {

	protected Driver driver;

	public BasePage(Driver driver) {
		this.driver = driver;
	}
	
	/**
	 * This method is useful for clicking an element
	 * 
	 * @param w
	 */
	public void click(WebElement w) {
		
		w.click();
	}
	
	/**
	 * Enter text in text box
	 * 
	 * @param w
	 * @param text
	 */
	public void type(WebElement w, String text){
		w.clear();
		w.sendKeys(text);
	}
	
	/**
	 * Get text
	 * @param w
	 * @return
	 */
	public String getText(WebElement w){
		return w.getText();	
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
