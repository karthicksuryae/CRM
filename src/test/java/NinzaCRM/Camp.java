package NinzaCRM;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import GenericUtility.ExcelUtility;
import GenericUtility.JavaUtility;
import GenericUtility.PropertyFileUtility;
import GenericUtility.WebDriverUtility;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;

public class Camp {

	public static void main(String[] args) throws InterruptedException, IOException {

		// Property Utility

		PropertyFileUtility putil = new PropertyFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();
		String BROWSER = putil.getDataFromPropertyFile("Browser");
		String URL = putil.getDataFromPropertyFile("Url");
		String USERNAME = putil.getDataFromPropertyFile("Username");
		String PASSWORD = putil.getDataFromPropertyFile("Password");

		// Reading data from Properties file

//			  	 FileInputStream fis=new FileInputStream("./src/test/resources/CommonData.properties");
//			  	 Properties prop=new Properties();
//			  	 prop.load(fis);
//			  	 //getProperty
//			  	 String BROWSER = prop.getProperty("Browser");
//			  	 String URL = prop.getProperty("Url");
//			   	 String USERNAME = prop.getProperty("Username");
//			   	 String PASSWORD = prop.getProperty("Password");

		WebDriver driver = null;

		if (BROWSER.equals("Chrome")) {

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

//			     WebDriver driver=new ChromeDriver(); 
		driver.manage().window().maximize();
//			     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
		wutil.waitforPageToLoad(driver);

		driver.get(URL);

		// Obj Res - Login Page

		LoginPage lp = new LoginPage(driver);
		lp.getUsernameTF().sendKeys(USERNAME);
		lp.getPwdTF().sendKeys(PASSWORD);
		lp.getSignIn().click();

		// login to NINZA CRM

//			     driver.findElement(By.id("username")).sendKeys(USERNAME); 
//			     driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD); 
//			     driver.findElement(By.xpath("//button[text()='Sign In']")).click(); 
		Thread.sleep(3000);

		// ExcelUtility

		ExcelUtility eutil = new ExcelUtility();
		String campname = eutil.readDataFromExcel("Campaign", 1, 2);
		String targetsize = eutil.readDataFromExcel("Campaign", 1, 3);

		System.out.println(campname);
		System.out.println(targetsize);

		// ExcelDDT

//			     FileInputStream fis1=new FileInputStream("./src/test/resources/AdvPractice.xlsx");
//			     Workbook wb= WorkbookFactory.create(fis1);
//			     Sheet sh=wb.getSheet("Campaign");
//			     Row r=sh.getRow(1);
//			     String campname = r.getCell(2).toString();
//			     System.out.println(campname);
//			     String targetsize = r.getCell(3).toString();
//			     System.out.println(targetsize);
//			     wb.close();

		// JavaUitltiy

		JavaUtility jutil = new JavaUtility();
		int ran = jutil.togetRandomNumber();

//			     Random ran=new Random(); 
//			     int randomcount = ran.nextInt(1000);

		// JavaUitltiy

		String daterquired = jutil.toExpDate(20);

		// Date Picker

//			     Date date = new Date();
//			     SimpleDateFormat sim=new SimpleDateFormat("dd-MM-yyyy"); 
//			     sim.format(date); 
//			     Calendar cal = sim.getCalendar(); 
//			     cal.add(Calendar.DAY_OF_MONTH,30); 
//			     String daterequired = sim.format(cal.getTime());

		// HomePage ObjectRespositry
		HomePage hp = new HomePage(driver);
		hp.getCreateCampBtn().click();

		// create campaign
//			     driver.findElement(By.xpath("//span[text()='Create Campaign']")).click(); 
		Thread.sleep(3000);

		// Campaign Pages
		CampaignPage cp = new CampaignPage(driver);
		cp.getCampaignNameTF().sendKeys(campname + +jutil.togetRandomNumber());
		cp.getTargetTF().clear();
		cp.getTargetTF().sendKeys(targetsize);

		//
//			     driver.findElement(By.name("campaignName")).sendKeys(campname + jutil.togetRandomNumber()); 
		Thread.sleep(3000);
//			     WebElement target = driver.findElement(By.name("targetSize")); 
//			     target.clear(); 
//			     target.sendKeys(targetsize); 
		Thread.sleep(3000);
		driver.findElement(By.name("expectedCloseDate")).sendKeys(jutil.toExpDate(20));

		wutil.passInputs(driver, cp.getDateeTF(), jutil.toExpDate(30));

		Thread.sleep(2000);
		cp.getCreateCampSubmitBtn().click();
//			     driver.findElement(By.xpath("//button[text()='Create Campaign']")).click(); 
		Thread.sleep(3000);

		// validation

//			    WebElement toastmsg = driver.findElement(By.xpath("//div[@role='alert']"));

		wutil.waitForVisiblityOfElement(hp.getToastmsg(), driver);

//			    WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10)); 
//			    wait.until(ExpectedConditions.visibilityOf(toastmsg)); 
		String msg = hp.getToastmsg().getText();

		if (msg.contains(campname)) {
			System.out.println("campaign created");
		} else {
			System.out.println("campaign not created");
		}

//			  driver.findElement(By.xpath("//button[@aria-label='close']")).click(); 
		hp.getCloseMsg().click();
		Thread.sleep(3000);

		hp.getUsericon().click();
		Thread.sleep(3000);

		hp.getLogoutBtn().click();

		// logout
//			  WebElement icon = driver.findElement(By.xpath("//div[@class='user-icon']")); 
//			  wutil.mouseHoverOnWebElement(driver, icon);

//			  Actions act=new Actions(driver); 
//			  act.moveToElement(icon).perform(); 

//			  WebElement logoutbtn =driver.findElement(By.xpath("//div[@class='dropdown-item logout']")); 
//			  wutil.clickOnWebElement(driver, logoutbtn);

//			  act.moveToElement(logoutbtn).click().perform(); 
		Thread.sleep(3000);
		driver.quit();
	}
}
