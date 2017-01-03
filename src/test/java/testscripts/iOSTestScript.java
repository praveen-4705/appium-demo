package testscripts;

import org.testng.annotations.Test;

import iOSPages.DatePickerPage;
import iOSPages.HomePage;
import test.BaseTest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class iOSTestScript extends BaseTest{
	
	@BeforeMethod
	public void beforeMethod(ITestContext context, Method m) throws MalformedURLException {
		super.intializeDriver(m.getName(), context.getName(), false,false);
	}
	
	@Test
	public void selectDate() throws InterruptedException{
		HomePage homePage	= new HomePage(d);
		DatePickerPage datePickerPage	= homePage.navigateToDatePickerPage();
		datePickerPage.selectDate("Tue Jan 10", "8", "57");
	}
}
