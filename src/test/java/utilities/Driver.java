package utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Driver implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

	public AppiumDriver<MobileElement> driver;

	DesiredCapabilities desiredCapabilities;

	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	private SauceOnDemandAuthentication sauceAuth;
	private SauceREST sauceClient;
	private String sauceUsername;
	private String sauceAccessKey;
	private ArrayList<String> tags = new ArrayList<String>();
	/** Name of the test case. */
	private String testName;

	/** Name of the test defined in the TestNG file. */
	private String testArea;

	public Driver(String testName, String testArea, boolean isAndroid, boolean isLocal) {
		
		this.testArea	= testArea;
		this.testName	= testName;
		
		createDriver(isAndroid, isLocal);
	}

	private void createDriver(boolean isAndroid, boolean isLocal) {

		if (isAndroid) {
			createAndoridDriver(isLocal);
		}else {
			createiOSDriver(isLocal);
		}

	}

	public void createAndoridDriver(boolean isLocal) {

		if (isLocal) {
			
//			File app = new File(System.getProperty("user.dir") + "/src/test/resources/Amzon2.apk");
			
			desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
			desiredCapabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
			desiredCapabilities.setCapability("deviceName", "Android");

//			desiredCapabilities.setCapability("app", app.getAbsolutePath());

			try {

				driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723//wd/hub"),
						desiredCapabilities);

			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

			// Set user info for Saucelabs
			if (System.getenv("SAUCE_USER_NAME") == null)
				sauceUsername = TestConfig.sauceUsername;
			else
				sauceUsername = System.getenv("SAUCE_USER_NAME");

			if (System.getenv("SAUCE_API_KEY") == null)
				sauceAccessKey = TestConfig.sauceAccessKey;
			else
				sauceAccessKey = System.getenv("SAUCE_API_KEY");

			// Create Saucelabs authenticator and REST client
			this.sauceAuth = new SauceOnDemandAuthentication(sauceUsername, sauceAccessKey);
			this.sauceClient = new SauceREST(sauceUsername, sauceAccessKey);

			desiredCapabilities = DesiredCapabilities.android();
			desiredCapabilities.setCapability("appiumVersion", "1.5.3");
			desiredCapabilities.setCapability("deviceName", "Android Emulator");
			desiredCapabilities.setCapability("deviceOrientation", "portrait");
			desiredCapabilities.setCapability("browserName", "");
			desiredCapabilities.setCapability("platformVersion", "5.1");
			desiredCapabilities.setCapability("platformName", "Android");
			desiredCapabilities.setCapability("app","sauce-storage:Selendroid.zip");
			
			tags.add(this.testArea);
			desiredCapabilities.setCapability("name", testName);
			desiredCapabilities.setCapability("tags", tags);

			try {

				// Set the driver
				driver = new AndroidDriver<MobileElement>(
						new URL("http://" + this.getAuthentication().getUsername() + ":"
								+ this.getAuthentication().getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
						desiredCapabilities);

			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
		}
	}
	
	public void createiOSDriver(boolean isLocal){
		
		if (isLocal) {
			
			desiredCapabilities = new DesiredCapabilities();

			File file = new File(System.getProperty("user.dir") + "/src/test/resources/UICatalog.app");

			desiredCapabilities.setCapability("app", file.getAbsolutePath());
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6s");
			desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
			desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			desiredCapabilities.setCapability("automationName", "XCUITest");
			desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);

			try {
				driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723//wd/hub"), desiredCapabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			
		}else {
			
			desiredCapabilities	= new DesiredCapabilities();
			
			// Set user info for Saucelabs
			if (System.getenv("SAUCE_USER_NAME") == null)
				sauceUsername = TestConfig.sauceUsername;
			else
				sauceUsername = System.getenv("SAUCE_USER_NAME");

			if (System.getenv("SAUCE_API_KEY") == null)
				sauceAccessKey = TestConfig.sauceAccessKey;
			else
				sauceAccessKey = System.getenv("SAUCE_API_KEY");

			// Create Saucelabs authenticator and REST client
			this.sauceAuth = new SauceOnDemandAuthentication(sauceUsername, sauceAccessKey);
			this.sauceClient = new SauceREST(sauceUsername, sauceAccessKey);
			
			desiredCapabilities.setCapability("platformName", "iOS");
			desiredCapabilities.setCapability("deviceName", "iPhone 6s Plus");
			desiredCapabilities.setCapability("platformVersion", "9.3");
			desiredCapabilities.setCapability("app", "sauce-storage:IOSApp.zip");
			desiredCapabilities.setCapability("browserName", "");
			desiredCapabilities.setCapability("deviceOrientation", "portrait");
			desiredCapabilities.setCapability("appiumVersion", "1.5.3");
			desiredCapabilities.setCapability("platform", "OS X 10.11");
			
			
			tags.add(this.testArea);
			desiredCapabilities.setCapability("name", testName);
			desiredCapabilities.setCapability("tags", tags);

			try {

				// Set the driver
				driver = new IOSDriver<MobileElement>(
						new URL("http://" + this.getAuthentication().getUsername() + ":"
								+ this.getAuthentication().getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
						desiredCapabilities);

			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
			
		}
		
	}

	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	public void setDriver(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public SauceOnDemandAuthentication getAuthentication() {
		// TODO Auto-generated method stub
		return sauceAuth;
	}

	public String getSessionId() {
		// TODO Auto-generated method stub
		return sessionId.get();
	}

	public SauceREST getSauceClient() {
		return sauceClient;
	}

	public void setSauceClient(SauceREST sauceClient) {
		this.sauceClient = sauceClient;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestArea() {
		return testArea;
	}

	public void setTestArea(String testArea) {
		this.testArea = testArea;
	}
}
