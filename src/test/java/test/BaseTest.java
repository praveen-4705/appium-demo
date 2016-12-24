package test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import utilities.Driver;
import utilities.Screenshot;
import utilities.TestConfig;
import utilities.monitoringMail;

public class BaseTest {

	protected Driver d;
	protected boolean localFlag = false;

	public static Logger log = Logger.getLogger("devpinoyLogger");

	public void intializeDriver(String testName, String testArea, boolean isAndroid, boolean isLocal)
			throws MalformedURLException {

		d = new Driver(testName, testArea, isAndroid, isLocal);
		this.localFlag = isLocal;
		System.out.println(testName + "--" + testArea);
		d.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod(ITestResult result, Method m) {

		if (!localFlag) {
			RemoteWebDriver driver = (RemoteWebDriver) d.getDriver();
			String sauceSessionId = driver.getSessionId().toString();

			// if test passed, add passed information to job, else add failed
			if (result.isSuccess())
				d.getSauceClient().jobPassed(sauceSessionId);
			else
				d.getSauceClient().jobFailed(sauceSessionId);
		} else {

			log.info("Quiting driver");

			System.out.println(result.isSuccess());
			System.out.println(!result.isSuccess());

			try {
				Screenshot.takeScreenshot(d.getDriver(),
						System.getProperty("user.dir") + "/src/test/resources/screenshot/Screenshot.png");
			} catch (IOException e) {
				log.error("Unable to take the screenshot");
				e.printStackTrace();
			}

			monitoringMail mail = new monitoringMail();
			try {
				mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject,
						TestConfig.messageBody,
						System.getProperty("user.dir") + "/src/test/resources/screenshot/Screenshot.png",
						TestConfig.attachmentName);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				log.error("Unable to send mail");
			}

		}

		d.getDriver().quit();
	}
}
