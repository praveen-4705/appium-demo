package testscripts;

import org.testng.annotations.Test;

import pages.HomePage;
import pages.SearchPage;
import pages.SignInPage;
import test.BaseTest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class AmazonTest extends BaseTest{

	@BeforeMethod
	public void beforeMethod(ITestContext context, Method m) throws MalformedURLException {
		super.intializeDriver(m.getName(), context.getName(), true,true);
	}

	@Test
	public void skipSignTest() throws InterruptedException {
		// Open app
		SignInPage signInPage	= new SignInPage(d);
		// CLick on Skip Sign in
		HomePage homePage		= signInPage.clickOnSkipSignIn();
		// Search for Product
		SearchPage searchPage	= homePage.searchForProductFromExcel();
		// Print product Name
		searchPage.printItemName();
	}
}
