package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import utilities.Driver;

public class BasePage {

	protected Driver driver;
	
	public static Logger log = Logger.getLogger("devpinoyLogger");

	public BasePage(Driver driver) {
		this.driver = driver;
	}
	
	/**
	 * This method is useful for clicking an element
	 * 
	 * @param w
	 */
	public void click(WebElement w) {
		log.info("Click on " + w);
		w.click();
	}
	
	/**
	 * Enter text in text box
	 * 
	 * @param w
	 * @param text
	 */
	public void type(WebElement w, String text){
		log.info("Entering text on " + w);
		w.clear();
		w.sendKeys(text);
	}
	
	/**
	 * Get text
	 * @param w
	 * @return
	 */
	public String getText(WebElement w){
		
		log.info("Getting test from " + w);
		return w.getText();	
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
