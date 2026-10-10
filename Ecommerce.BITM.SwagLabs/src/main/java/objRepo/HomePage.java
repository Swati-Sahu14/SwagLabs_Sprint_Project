package objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import genricUtility.SelectUtility;

public class HomePage {

    WebDriver d;
    SelectUtility s = new SelectUtility();
    WebDriverWait wait;

    public HomePage(WebDriver d) {
        this.d = d;
        this.wait = new WebDriverWait(d, Duration.ofSeconds(10));

        // Initialize Page Factory elements
        PageFactory.initElements(d, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menubar;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cart;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement additemToCart;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement filter;

    @FindBy(xpath = "//span[@class='title']")
    private WebElement title;

    @FindBy(xpath = "//div[@class='inventory_item']")
    private List<WebElement> products;

    @FindBy(xpath = "//nav[@class='bm-item-list']/*")
    private List<WebElement> menuList;

    @FindBy(xpath = "(//div[@class='inventory_item_name '])[2]")
    private WebElement productname;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//button[starts-with(@id,'add-to-cart-')]")
    private List<WebElement> addToCartbtn;

    @FindBy(xpath = "//button[starts-with(@id,'remove-')]")
    private List<WebElement> RemovePrd;

    // Open the navigation menu
    public void getMenubar() {
        wait.until(ExpectedConditions.elementToBeClickable(menubar)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(menuList));
    }

    // Verify that menu items are displayed
    public void getMenuList() {
        Assert.assertTrue(menuList.size() > 0, "Menu items are not displayed");
        Reporter.log("Home page contains menu items", true);
    }

    // Navigate to the cart page and verify its title
    public void getCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();

        wait.until(ExpectedConditions.urlContains("cart.html"));

        String actualTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[@class='title']")))
                .getText();

        Assert.assertEquals(actualTitle, "Your Cart",
                "Cart page is not displayed");

        Reporter.log(actualTitle, true);
        Reporter.log("Cart page is opened", true);
    }

    // Add the Bike Light product to the cart
    public void getAddItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(additemToCart)).click();
    }

    public String getHomeProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productname)).getText();
    }

    public void getFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(filter)).click();
    }

    public void getFilter(String option) {
        s.selectByVisibleText(filter, option);
    }

    public List<WebElement> getFilterOptions() {
        return s.getAllOptions(filter);
    }

    // Log product names after sorting
    public void printProductNames(String type) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < productNames.size(); i++) {
            result.append(productNames.get(i).getText());

            if (i < productNames.size() - 1) {
                result.append(", ");
            }
        }

        Reporter.log("Sorted by Name (" + type + "): " + result, true);
    }

    // Log product prices after sorting
    public void printProductPrices(String type) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < productPrices.size(); i++) {
            result.append(productPrices.get(i).getText());

            if (i < productPrices.size() - 1) {
                result.append(", ");
            }
        }

        Reporter.log("Sorted by Price (" + type + "): " + result, true);
    }

    public String getProductsTitle() {
        return wait.until(ExpectedConditions.visibilityOf(title)).getText();
    }

    // Verify that the Products page is displayed
    public void getTitle() {
        String actualTitle = getProductsTitle();

        Reporter.log("Home Page Title: " + actualTitle, true);

        Assert.assertEquals(actualTitle, "Products",
                "Home page is not displayed");

        Reporter.log("Home Page is displayed", true);
    }

    // Verify that products are displayed
    public void getProducts() {
        wait.until(ExpectedConditions.visibilityOfAllElements(products));

        Assert.assertTrue(products.size() > 0, "Products are not visible");
        Reporter.log("Products are visible", true);
    }

    // Add all available products to the cart
    public void addAllProductsToCart() {
        for (WebElement button : addToCartbtn) {
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        }

        Reporter.log("Products are added to cart", true);
    }

    // Verify that all six products are added to the cart
    public void verifyProductsAdded() {
        wait.until(ExpectedConditions.attributeToBe(
                cart, "aria-label", "Cart, 6 items"));

        Assert.assertEquals(cart.getAttribute("aria-label"), "Cart, 6 items",
                "All items are not added");

        Reporter.log("All products are added to cart", true);
    }

    // Remove all products that currently have a Remove button
    public void getRemovePrd() {
        while (!d.findElements(By.xpath("//button[starts-with(@id,'remove-')]")).isEmpty()) {
            WebElement removeButton = d.findElement(
                    By.xpath("//button[starts-with(@id,'remove-')]"));

            wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();

            wait.until(ExpectedConditions.stalenessOf(removeButton));
        }

        Reporter.log("Products are removed from cart", true);
    }

    // Verify that the cart is empty
    public void verifyRemovePrd() {
        wait.until(ExpectedConditions.attributeToBe(
                cart, "aria-label", "Cart, empty"));

        Assert.assertEquals(cart.getAttribute("aria-label"), "Cart, empty",
                "All items are not removed");

        Reporter.log("All products are removed", true);
    }

    // Verify Remove buttons are not displayed before adding products
    public void verifyRemoveButtonBeforeAdding() {
        Assert.assertTrue(
                d.findElements(By.xpath("//button[starts-with(@id,'remove-')]")).isEmpty(),
                "Remove buttons should not appear before adding products");

        Reporter.log("Remove buttons are not displayed before adding products", true);
    }

    // Verify the cart badge is not displayed when no product is added
    public void verifyCartBadgeNotDisplayed() {
        Assert.assertTrue(
                d.findElements(By.className("shopping_cart_badge")).isEmpty(),
                "Cart badge appeared without adding a product");

        Reporter.log("Cart badge is not displayed", true);
    }

    // Verify inventory access requires login
    public void verifyInventoryRequiresLogin() {
        d.get("https://www.saucedemo.com/inventory.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("login-button")));

        Assert.assertFalse(
                d.getCurrentUrl().contains("inventory.html"),
                "Inventory is accessible without login");

        Reporter.log("Inventory access is blocked without login", true);
    }

    // Verify inventory access is blocked after logout
    public void verifyInventoryAfterLogout() {
        d.get("https://www.saucedemo.com/inventory.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("login-button")));

        Assert.assertFalse(
                d.getCurrentUrl().contains("inventory.html"),
                "Inventory is accessible after logout");

        Reporter.log("Inventory access is blocked after logout", true);
    }

    // Verify cart access requires login
    public void verifyCartRequiresLogin() {
        d.get("https://www.saucedemo.com/cart.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("login-button")));

        Assert.assertFalse(
                d.getCurrentUrl().contains("cart.html"),
                "Cart is accessible without login");

        Reporter.log("Cart is not accessible without login", true);
    }

    // Verify the Remove button appears after adding a product
    public void removeProductFromInventory() {
        Assert.assertTrue(
                d.findElements(By.xpath("//button[starts-with(@id,'remove-')]")).size() > 0,
                "Remove button is not available after adding the product");

        d.findElement(By.xpath("//button[starts-with(@id,'remove-')]")).click();

        Reporter.log("Product removed from inventory", true);
    }

    // Verify the Remove button disappears after product removal
    public void verifyRemoveButtonNotDisplayed() {
        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//button[starts-with(@id,'remove-')]"), 0));

        Reporter.log("Remove button is no longer displayed", true);
    }
}