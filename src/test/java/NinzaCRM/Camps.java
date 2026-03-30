package NinzaCRM;

	// ---------------------- IMPORT STATEMENTS ----------------------
	// Selenium imports
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;

	// Java utility imports
	import java.io.IOException;
	import java.util.HashMap;
	import java.util.Map;

	// Framework Utility Classes
	import GenericUtility.ExcelUtility;
	import GenericUtility.JavaUtility;
	import GenericUtility.PropertyFileUtility;
	import GenericUtility.WebDriverUtility;

	// Object Repository Classes
	import ObjectRepository.CampaignPage;
	import ObjectRepository.HomePage;
	import ObjectRepository.LoginPage;
	
	public class Camps {



		public static void main(String[] args) throws InterruptedException, IOException {

			// ----------------------------------------------------------
			// STEP 1 : CREATE OBJECTS OF ALL GENERIC UTILITIES
			// These utilities help to reuse common code across the framework
			// ----------------------------------------------------------

			PropertyFileUtility putil = new PropertyFileUtility();  // Used to read data from properties file
			WebDriverUtility wutil = new WebDriverUtility();        // Contains Selenium helper methods
			ExcelUtility eutil = new ExcelUtility();                // Used to read data from Excel
			JavaUtility jutil = new JavaUtility();                  // Contains Java helper methods

			// ----------------------------------------------------------
			// STEP 2 : READ TEST DATA FROM PROPERTIES FILE
			// These values are stored inside CommonData.properties
			// ----------------------------------------------------------

			String BROWSER = putil.getDataFromPropertyFile("Browser");
			String URL = putil.getDataFromPropertyFile("Url");
			String USERNAME = putil.getDataFromPropertyFile("Username");
			String PASSWORD = putil.getDataFromPropertyFile("Password");

			// ----------------------------------------------------------
			// STEP 3 : LAUNCH BROWSER BASED ON BROWSER VALUE
			// Browser type is taken from properties file
			// ----------------------------------------------------------

			WebDriver driver = null;

			if (BROWSER.equals("Chrome")) {

				// Chrome settings to disable password manager popup
				ChromeOptions settings = new ChromeOptions();

				Map<String, Object> prefs = new HashMap<>();
				prefs.put("profile.password_manager_leak_detection", false);

				settings.setExperimentalOption("prefs", prefs);

				driver = new ChromeDriver(settings);
			}

			else if (BROWSER.equals("Edge")) {
				driver = new EdgeDriver();
			}

			else if (BROWSER.equals("Firefox")) {
				driver = new FirefoxDriver();
			}

			// ----------------------------------------------------------
			// STEP 4 : BROWSER SETUP
			// Maximize window and wait for page load
			// ----------------------------------------------------------

			driver.manage().window().maximize();
			wutil.waitforPageToLoad(driver);

			// Open application URL
			driver.get(URL);

			// ----------------------------------------------------------
			// STEP 5 : LOGIN TO APPLICATION USING PAGE OBJECT MODEL
			// ----------------------------------------------------------

			LoginPage lp = new LoginPage(driver);

			lp.getUsernameTF().sendKeys(USERNAME);
			lp.getPwdTF().sendKeys(PASSWORD);
			lp.getSignIn().click();

			Thread.sleep(3000);

			// ----------------------------------------------------------
			// STEP 6 : READ TEST DATA FROM EXCEL FILE
			// ----------------------------------------------------------

			String campname = eutil.readDataFromExcel("Campaign", 1, 2);
			String targetsize = eutil.readDataFromExcel("Campaign", 1, 3);

			System.out.println("Campaign Name : " + campname);
			System.out.println("Target Size : " + targetsize);

			// ----------------------------------------------------------
			// STEP 7 : GENERATE RANDOM NUMBER USING JAVA UTILITY
			// Used to avoid duplicate campaign names
			// ----------------------------------------------------------

			int ran = jutil.togetRandomNumber();

			// ----------------------------------------------------------
			// STEP 8 : GENERATE FUTURE DATE (EXPECTED CLOSE DATE)
			// ----------------------------------------------------------

			String daterequired = jutil.toExpDate(20);

			// ----------------------------------------------------------
			// STEP 9 : NAVIGATE TO CREATE CAMPAIGN PAGE
			// ----------------------------------------------------------

			HomePage hp = new HomePage(driver);

			hp.getCreateCampBtn().click();

			Thread.sleep(3000);

			// ----------------------------------------------------------
			// STEP 10 : ENTER CAMPAIGN DETAILS
			// ----------------------------------------------------------

			CampaignPage cp = new CampaignPage(driver);

			// Enter campaign name with random number
			cp.getCampaignNameTF().sendKeys(campname + ran);

			// Enter target size
			cp.getTargetTF().clear();
			cp.getTargetTF().sendKeys(targetsize);

			Thread.sleep(3000);

			// Enter expected close date
			wutil.passInputs(driver, cp.getDateeTF(), jutil.toExpDate(30));

			Thread.sleep(2000);

			// Click create campaign button
			cp.getCreateCampSubmitBtn().click();

			Thread.sleep(3000);

			// ----------------------------------------------------------
			// STEP 11 : VALIDATE CAMPAIGN CREATION
			// Check toast message
			// ----------------------------------------------------------

			wutil.waitForVisiblityOfElement(hp.getToastmsg(), driver);

			String msg = hp.getToastmsg().getText();

			if (msg.contains(campname)) {
				System.out.println("Campaign Created Successfully");
			} else {
				System.out.println("Campaign Creation Failed");
			}

			// Close toast message
			hp.getCloseMsg().click();

			Thread.sleep(3000);

			// ----------------------------------------------------------
			// STEP 12 : LOGOUT FROM APPLICATION
			// ----------------------------------------------------------

			hp.getUsericon().click();

			Thread.sleep(3000);

			hp.getLogoutBtn().click();

			Thread.sleep(3000);

			// ----------------------------------------------------------
			// STEP 13 : CLOSE BROWSER
			// ----------------------------------------------------------

			driver.quit();
		}
}
