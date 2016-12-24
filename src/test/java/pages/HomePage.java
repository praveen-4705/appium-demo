package pages;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import freemarker.template.utility.NullArgumentException;
import utilities.Driver;
import utilities.ExcelReader;

public class HomePage extends BasePage {

	@FindBy(id = "rs_search_src_text")
	private WebElement searchBox;

	@FindBy(id = "rs_search_dropdown_item_text")
	private WebElement searchDDText;

	public HomePage(Driver driver) {
		super(driver);
		PageFactory.initElements(driver.getDriver(), this);
	}

	/**
	 * Search for product
	 * 
	 * @param productName
	 * @throws InterruptedException
	 */
	public SearchPage searchFor(String productName) throws InterruptedException {

		// Click on Search Box
		click(searchBox);

		// Type the product Name
		type(searchBox, productName);

		// Select product
		click(searchDDText);

		Thread.sleep(3000l);

		// Return Search Page Object
		return new SearchPage(driver);
	}

	public SearchPage searchForProductFromExcel() {

		String prodcutName = null;

		try {
			prodcutName = ExcelReader
					.readExcel(System.getProperty("user.dir") + "/srs/test/resources/TestData.xls", "ProductList")
					.get(0);
			System.out.println(prodcutName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (prodcutName == null) {

				throw new NullArgumentException(prodcutName);
			}

			searchFor(prodcutName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new SearchPage(driver);
	}
}
