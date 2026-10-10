package objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Negative_HomePage {

    WebDriver d;
    WebDriverWait wait;

    public Negative_HomePage(WebDriver d) {
        this.d = d;
        this.wait = new WebDriverWait(d, Duration.ofSeconds(10));

        // Initialize Page Factory elements
        PageFactory.initElements(d, this);
    }

    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement filter;

    @FindBy(xpath = "//nav[@class='bm-item-list']/*")
    private List<WebElement> menuList;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public boolean isHomePageDisplayed() {
        return d.getCurrentUrl().contains("inventory.html");
    }

    public boolean isLoginPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(loginButton)
        ).isDisplayed();
    }

    // Verify the filter remains unchanged without user interaction
    public void verifyFilterWithoutClick() {
        Select select = new Select(filter);
        String selectedOption = select.getFirstSelectedOption().getText();

        Assert.assertEquals(
                selectedOption,
                "Name (A to Z)",
                "Filter option changed without clicking"
        );

        Reporter.log("Filter remains unchanged: " + selectedOption, true);
    }

    // Verify menu items remain hidden before opening the menu
    public void verifyMenuItemsWithoutClick() {
        Assert.assertTrue(
                wait.until(ExpectedConditions.invisibilityOfAllElements(menuList)),
                "Menu items are displayed without clicking the menu bar"
        );

        Reporter.log("Menu items are hidden before opening the menu", true);
    }
}