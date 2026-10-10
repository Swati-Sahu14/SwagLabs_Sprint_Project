package objRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage {

	WebDriver driver;

	public LogoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver , this);
	}
	
	@FindBy(id="react-burger-menu-btn")
	private WebElement menu;

	@FindBy(id="logout_sidebar_link")
	private WebElement logout;
	
	public void getMenu() {
		menu.click();
	}

	public void getLogout() {
		logout.click();
	}	
}
