package BaseTest;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import genericUtility.PropertiesFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class BaseClass {

	// WebDriver reference
	public WebDriver driver = null;

	// Utility class objects
	public PropertiesFileUtility puti = new PropertiesFileUtility();
	public WebDriverUtility wutil = new WebDriverUtility();

	// ===============================
	// BEFORE SUITE – Executes once before the entire suite
	// Used to initialize DB connection or environment setup
	// ===============================
	@BeforeSuite(groups = { "smoke", "regression" })
	public void beforeSuite() {
		Reporter.log("DB open", true);
	}

	// ===============================
	// BEFORE CLASS – Launch browser
	// Runs once before test methods in a class
	// ===============================
	@BeforeClass(groups = { "smoke", "regression" })
	public void beforeClass() throws Throwable {

		// Read browser value from properties file
		String BROWSER = puti.toReadDataFromPropertiesFile("Browser");

		if (BROWSER.equalsIgnoreCase("Chrome")) {

			// Disable Chrome password manager popup
			ChromeOptions settings = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.password_manager_leak_detection", false);
			settings.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(settings);
		}

		else if (BROWSER.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		}

		else if (BROWSER.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		}

		else {
			throw new RuntimeException("Invalid browser name: " + BROWSER);
		}

		Reporter.log("Browser launched", true);
	}

	// ===============================
	// BEFORE METHOD – Login to application
	// Runs before every test method
	// ===============================
	@BeforeMethod(groups = { "smoke", "regression" })
	public void beforeMethod() throws Throwable {

		String URL = puti.toReadDataFromPropertiesFile("Url");
		String USERNAME = puti.toReadDataFromPropertiesFile("Username");
		String PASSWORD = puti.toReadDataFromPropertiesFile("Password");

		driver.manage().window().maximize();
		wutil.waitForPageToLoad(driver);

		driver.get(URL);

		// Login using Page Object Model
		LoginPage lp = new LoginPage(driver);
		lp.getUsernameTF().sendKeys(USERNAME);
		lp.getPwdTF().sendKeys(PASSWORD);
		lp.getSignIn().click();

		Reporter.log("Login successful", true);
	}

	// ===============================
	// AFTER METHOD – Logout
	// Runs after every test method
	// ===============================
	@AfterMethod(groups = { "smoke", "regression" })
	public void afterMethod() {

		HomePage hp = new HomePage(driver);
		WebElement icon = hp.getUsericon();
		wutil.mouseHoverOnWebElement(driver, icon);
		hp.getLogoutBtn().click();
		Reporter.log("Logout successful", true);
	}

	// ===============================
	// AFTER CLASS – Close browser
	// ===============================
	@AfterClass(groups = { "smoke", "regression" })
	public void afterClass() {
		driver.quit();
		Reporter.log("Browser closed", true);
	}

	// ===============================
	// AFTER SUITE – Close DB
	// ===============================
	@AfterSuite(groups = { "smoke", "regression" })
	public void afterSuite() {
		Reporter.log("DB close", true);
	}
}