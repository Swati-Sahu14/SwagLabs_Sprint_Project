package objRepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class CheckoutPage {

    WebDriver d;
    WebDriverWait wait;

    // Initialize the WebDriver, explicit wait, and page elements.
    public CheckoutPage(WebDriver d) {
        this.d = d;
        this.wait = new WebDriverWait(d, Duration.ofSeconds(10));
        PageFactory.initElements(d, this);
    }

    // Checkout information fields.
    @FindBy(id = "first-name")
    private WebElement Fn;

    @FindBy(id = "last-name")
    private WebElement Ln;

    @FindBy(id = "postal-code")
    private WebElement PCode;

    // Checkout buttons.
    @FindBy(id = "continue")
    private WebElement continuebtn;

    @FindBy(id = "cancel")
    private WebElement cancelbtn;

    // Checkout validation error message.
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // Enter First Name.
    public void getFn(String value) {
        wait.until(ExpectedConditions.visibilityOf(Fn)).sendKeys(value);
    }

    // Enter Last Name.
    public void getLn(String value) {
        wait.until(ExpectedConditions.visibilityOf(Ln)).sendKeys(value);
    }

    // Enter Postal Code.
    public void getPCode(String value) {
        wait.until(ExpectedConditions.visibilityOf(PCode)).sendKeys(value);
    }

    // Click Continue to proceed to Checkout Overview
    public void getContinuebtn() {      
        wait.until(ExpectedConditions.elementToBeClickable(continuebtn)).click();
        System.out.println("Current URL: " + d.getCurrentUrl());
        if (!d.findElements(By.cssSelector("[data-test='error']")).isEmpty()) {
        	System.out.println("Page message: " +d.findElement(By.cssSelector("[data-test='error']")).getText());
        }
    }
    // Click Cancel and wait for the Cart page URL.
    public void getCancelbtn() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelbtn)).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    // Verify the entered First Name.
    public void verifyFirstName(String expected) {
        String actual = Fn.getAttribute("value");
        Assert.assertEquals(actual,expected,"First Name is not entered correctly");
        Reporter.log("First Name entered correctly: " + actual, true);
    }

    // Verify the entered Last Name.
    public void verifyLastName(String expected) {
        String actual = Ln.getAttribute("value");
        Assert.assertEquals(actual,expected,"Last Name is not entered correctly");
        Reporter.log("Last Name entered correctly: " + actual, true);
    }

    // Verify the entered Postal Code.
    public void verifyPCode(String expected) {
        String actual = PCode.getAttribute("value");
        Assert.assertEquals(actual,expected,"Postal Code is not entered correctly");
        Reporter.log("Postal Code entered correctly: " + actual, true);
    }

    // Retrieve the checkout validation error message.
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    // Verify navigation to Checkout Overview page
    public void verifyContinuebtn() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        Assert.assertTrue(d.getCurrentUrl().contains("checkout-step-two.html"),"Checkout Overview page is not displayed");
        Reporter.log("Checkout Overview page is displayed", true);
    }

    // Verify navigation back to the Cart page.
    public void verifyCancelbtn() {
        Assert.assertTrue(d.getCurrentUrl().contains("cart.html"),"Your Cart page is not displayed");
        Reporter.log("Your Cart page is displayed", true);
    }
    
    // Verifies that the user remains on the checkout information page.
    public void verifyCheckoutInformationPage() {
	    Assert.assertTrue(d.getCurrentUrl().contains("checkout-step-one.html"),"Checkout proceeded despite invalid information");
	    Reporter.log("User remains on Checkout Information page", true);
	}

}