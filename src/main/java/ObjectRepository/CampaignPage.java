package ObjectRepository;

import java.awt.print.PageFormat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CampaignPage {
	
	public CampaignPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="campaignName")
	private WebElement campaignNameTF;
	
	@FindBy(name="targetSize")
	private WebElement targetTF;
	
	@FindBy(name="expectedCloseDate")
	private WebElement dateeTF;
	
	@FindBy(xpath="//button[text()='Create Campaign']")
	private WebElement createCampSubmitBtn;
	
	@FindBy(xpath="//div[@role='alert']")
	private WebElement toastmasg;
	
	@FindBy(xpath="//button[@aria-label='close']")
	private WebElement closemasg;
	
	public WebElement getCampaignNameTF() {
		return campaignNameTF;
	}

	public WebElement getTargetTF() {
		return targetTF;
	}

	public WebElement getDateeTF() {
		return dateeTF;
	}

	public WebElement getCreateCampSubmitBtn() {
		return createCampSubmitBtn;
	}

	public WebElement getToastmasg() {
		return toastmasg;
	}

	public WebElement getClosemasg() {
		return closemasg;
	}
	
}
