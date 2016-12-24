package utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Driver implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

	public AppiumDriver<MobileElement> driver;

	DesiredCapabilities desiredCapabilities;

	public static Logger log = Logger.getLogger("devpinoyLogger");

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
		}

	}

	public void createAndoridDriver(boolean isLocal) {

		if (isLocal) {
			log.info("Adding desired capabilities");
			desiredCapabilities = DesiredCapabilities.android();

			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
			desiredCapabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
			desiredCapabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");

			// File apkFile = new
			// File("/Users/praveen/Documents/praveen/pomFramework/src/test/resources/Amazon.apk");

			// desiredCapabilities.setCapability("app",
			// apkFile.getAbsolutePath());

			log.info("Launching dirver");
			try {

				driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723//wd/hub"),
						desiredCapabilities);

			} catch (Exception e) {

				log.error("Unable to laucnh the driver");

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