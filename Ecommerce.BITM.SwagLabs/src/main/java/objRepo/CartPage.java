package objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class CartPage {

    WebDriver d;
    WebDriverWait wait;

    public CartPage(WebDriver d) {
        this.d = d;
        this.wait = new WebDriverWait(d, Duration.ofSeconds(10));
        PageFactory.initElements(d, this);
    }

    @FindBy(xpath = "//span[@class='title']")
    private WebElement CartTitle;

    @FindBy(xpath = "//button[@class='btn btn_secondary btn_small cart_button']")
    private WebElement removebtn;

    @FindBy(id = "continue-shopping")
    private WebElement continueShopping;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private WebElement CartProductName;

    @FindBy(xpath = "//div[@class='cart_list']//div[@class='cart_item']")
    private List<WebElement> cartProducts;

    @FindBy(id = "checkout")
    private WebElement checkoutbtn;

    // Verify that the Cart page is displayed.
    public void getCarttitle() {
        String actualTitle = wait.until(
            ExpectedConditions.visibilityOf(CartTitle)
        ).getText();

        Assert.assertEquals(
            actualTitle,
            "Your Cart",
            "Cart page is not displayed"
        );

        Reporter.log("Cart page is displayed", true);
    }

    // Remove a product from the cart.
    public void getRemovebtn() {
        wait.until(
            ExpectedConditions.elementToBeClickable(removebtn)
        ).click();
    }

    // Return to the Products page.
    public void getContinueShopping() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueShopping)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("inventory.html")
        );
    }

    // Retrieve the product name from the cart.
    public String getProductName() {
        String cartProductName = wait.until(
            ExpectedConditions.visibilityOf(CartProductName)
        ).getText();

        Reporter.log("Cart product: " + cartProductName, true);
        return cartProductName;
    }

    // Verify that products are present in the cart.
    public void verifyProductsInCart() {
        wait.until(
            ExpectedConditions.visibilityOf(CartTitle)
        );

        Assert.assertTrue(
            cartProducts.size() > 0,
            "Products are not present in the cart"
        );

        Reporter.log(
            "Number of products in cart: " + cartProducts.size(),
            true
        );
    }

    // Verify that the cart is empty.
    public void verifyProductRemoved() {
        Assert.assertEquals(
            cartProducts.size(),
            0,
            "Product is still present in the cart"
        );

        Reporter.log("Product is removed from the cart", true);
    }

 // Navigate to Checkout Information page
    public void getCheckoutbtn() {
        
        wait.until(ExpectedConditions.elementToBeClickable(checkoutbtn)).click();

        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

        Reporter.log("Checkout Information page is displayed", true);
    }
    
    public void verifyCartEmpty() {
	    Assert.assertEquals(cartProducts.size(),0,"Precondition failed: cart is not empty");
	    Reporter.log("Cart is empty", true);
	}

	public void refreshAndVerifyCartEmpty() throws InterruptedException {
	    d.navigate().refresh();
	    Thread.sleep(2000);
	    Assert.assertEquals(cartProducts.size(),0,"Removed product reappeared after refresh");
	    Reporter.log("Removed product did not reappear after refresh",true);
	}

}