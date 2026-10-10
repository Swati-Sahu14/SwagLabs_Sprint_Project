package objRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class LoginPage {
	
	WebDriver driver;
	
	// Initialize the WebElements of the Login page
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver , this);
	}

	@FindBy(id="user-name")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="login-button")
	private WebElement login;
	
	@FindBy(xpath="//h3[@role='alert']")
	private WebElement message;

	// Store the entered username and password for reporting
	private String Username;
	private String Password;
	
	// Enter the username
	public void getUsername(String value) {
		this.Username = value;
		username.sendKeys(value);
	}
	
	// Enter the password
	public void getPassword(String value) {
		this.Password = value;
		password.sendKeys(value);
	}
	
	// Click the Login button
	public void getLogin() {
		login.click();
	}
	
	// Perform login using the given username and password
	public void login(String username, String password) {
	    getUsername(username);
	    getPassword(password);
	    getLogin();
	}
	
	// Verify and report the error message for invalid login
	public void getErrorMessage()
	{
		Assert.assertTrue(message.isDisplayed(),"Error message is not displayed");
		String actualMessage = message.getText();
		Assert.assertTrue(!actualMessage.isEmpty(),"Error message is not displayed for: " + username);
		Reporter.log("Username: " + Username,true);
	    Reporter.log("Password: " + Password,true);
	    Reporter.log("Error Message: " + actualMessage,true);    
	}
}
