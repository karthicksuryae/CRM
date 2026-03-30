package GenericUtility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
	
	 // Implicit wait
	
	 public void waitforPageToLoad(WebDriver driver) 
	 {
		 driver.manage().window().maximize();
	 }
	 
	 // Explicit wait
	 
	 public void waitForVisiblityOfElement(WebElement element, WebDriver driver) 
	 {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		 wait.until(ExpectedConditions.visibilityOf(element));
	 }
	 
	  // Switch to frame by index
	 
	 public void switchToFrame(WebDriver driver, int index)
	 {
	     driver.switchTo().frame(index);
	 }
	 
	 // Switch to frame by name or id  
	 
	 public void switchToFrame(WebDriver driver, String nameOrId)
	 {
	     driver.switchTo().frame(nameOrId);
	 }
	 
	 // Switch to frame by WebElement
	 
	 public void switchToFrame(WebDriver driver, WebElement frameElement)
	 {
	     driver.switchTo().frame(frameElement);
	 }
	 
	 // Accept Alert
	 
	 public void switchToAlertandAccept(WebDriver driver)
	 {
		 driver.switchTo().alert().accept();
	 }

	// Dismiss Alert
	 
	 public void switchToAlertAndDismiss(WebDriver driver) 
	 {
	     driver.switchTo().alert().dismiss();
	 }
	 
	 // Text Alert
	 
	 public String switchToAlertAndText(WebDriver driver) 
	 {
         String text1 = driver.switchTo().alert().getText();
         return text1;
	 }

	 // Sendkeys Alert 
	 
	 public void switchToAlertAndSendKeys(WebDriver driver,String text) 
	 {
		 driver.switchTo().alert().sendKeys(text);
     }
	 
	 // Select
	 
	 public void select(WebElement element,int index)
     {
         Select sel= new Select(element);
         sel.selectByIndex(index);
	 }
	    
     public void select(WebElement element,String value)
     {
    	 Select sel= new Select(element);
	     sel.selectByValue(value);
     }
	    
     public void select(String text,WebElement element)
     {
         Select sel= new Select(element);
         sel.selectByVisibleText(text);
     }
     
     // Mouse Actions
     
     public void mouseHoverOnWebElement(WebDriver driver,WebElement element)
     {
         Actions act=new Actions(driver);
         act.moveToElement(element).perform();
     }
     
     public void clickOnWebElement(WebDriver driver, WebElement element) 
     { 
     		  Actions act = new Actions(driver); 
     		  act.moveToElement(element).click().perform(); 
     } 
     
     public void doubleClick(WebDriver driver, WebElement element)
     {
         Actions act=new Actions(driver);
         act.doubleClick(element).perform();
     }
     
     public void rightClickOnWebElement(WebDriver driver, WebElement element) 
     { 
     		  Actions act = new Actions(driver); 
     		  act.contextClick(element).perform(); 
     }
     
     public void passInput(WebDriver driver,WebElement element,String text)
     {
         Actions act=new Actions(driver);
         act.click(element).sendKeys(text).perform();
     }
     
     public void toSwitchtheWindow(WebDriver driver)
     {
       Set<String> allWinId = driver.getWindowHandles();
       for(String windowiD:allWinId)
       {
     	  driver.switchTo().window(windowiD);
       }
     }
     
     public void toTakeSS(WebDriver driver, String filename) throws IOException
     {
     	TakesScreenshot ts=(TakesScreenshot) driver;
     	File temp = ts.getScreenshotAs(OutputType.FILE);
     	File perm = new File("./errorshots/"+filename+".png");
     	FileHandler.copy(temp, perm);
     }
     
     public void scrollBy(WebDriver driver, int x, int y)
     {
     	JavascriptExecutor jse=(JavascriptExecutor) driver;
     	jse.executeScript("window.scrollBy("+x+", "+y+")");
     }
     
     public void clickOnWebElement1(WebDriver driver, WebElement element) 
     {
         element.click();
     }
     
     public void passInputs(WebDriver driver, WebElement element, String value)
     {
         element.clear();
         element.sendKeys(value);
     }
	    
}
