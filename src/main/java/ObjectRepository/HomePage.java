package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Campaigns")
	private WebElement campaign;

	@FindBy(xpath = "//span[text()='Create Campaign']")
	private WebElement createCampBtn;

	@FindBy(xpath = "//div[@class='user-icon']")
	private WebElement usericon;

	@FindBy(xpath = "//div[@class='dropdown-item logout']")
	private WebElement logoutBtn;

	@FindBy(xpath = "//div[@role='alert']")
	private WebElement toastmsg;

	@FindBy(xpath = "//button[@aria-label='close']")
	private WebElement closeMsg;

	public WebElement getCampaign() {
		return campaign;
	}

	public WebElement getCreateCampBtn() {
		return createCampBtn;
	}

	public WebElement getUsericon() {
		return usericon;
	}

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}

	public WebElement getCloseMsg() {
		return closeMsg;
	}

	public WebElement getToastmsg() {
		return toastmsg;
	}
}
