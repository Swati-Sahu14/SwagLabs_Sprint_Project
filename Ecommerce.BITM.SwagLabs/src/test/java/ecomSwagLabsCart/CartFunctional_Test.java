package ecomSwagLabsCart;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.CheckoutPage;
import objRepo.HomePage;

public class CartFunctional_Test extends BaseClass {
    
	 //Verify that the product added to the cart is displayed on the cart page
	@Test(priority = 1,groups = {"FT", "Pos"})
    public void verifyProductInCartTest() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(driver);
        c.getCarttitle();
    }

    //Verify that the products added to the cart are displayed correctly on the cart page
	@Test(priority = 2,groups = {"FT", "Pos"})
	   public void ProductsInCartPage() throws Exception {
		   login();
		   HomePage h = new HomePage(driver);
		   h.getAddItemToCart();
	       h.getCart();
		   CartPage c = new CartPage(driver);
		   c.verifyProductsInCart();
	   }

    //Verify that clicking the Continue Shopping button navigates back to the home page
	@Test(priority = 3,groups = {"FT", "Pos"})
    public void continueShoppingTest() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(driver);
        c.getContinueShopping();
        h.getTitle();
    }

    //Verify that a product can be removed from the cart successfully
	@Test(priority = 4,groups = {"FT", "Pos"})
    public void removeTest() throws Exception {
    login();
    HomePage h = new HomePage(driver);
    h.getAddItemToCart();
    h.getCart();
    CartPage c = new CartPage(driver);
    c.getRemovebtn();
    c.verifyProductRemoved();
    }

    //Verify that clicking the Checkout button initiates the checkout process
    @Test(priority = 5,groups = {"FT", "Pos"})
    public void verifyCheckoutbtnTest() throws Exception {
    	login();
    	HomePage h = new HomePage(driver);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(driver);
        c.getCheckoutbtn();
    }
 
    // Verify checkout behavior when the cart is empty.
    @Test(priority = 6,groups = {"FT", "Neg"})
    public void emptyCartCheckoutTest() throws Exception {

        login();
        HomePage h = new HomePage(driver);
        h.getCart();
        CartPage c = new CartPage(driver);
        c.verifyCartEmpty();
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(driver);
        cop.verifyCheckoutInformationPage();
        ScreenshotUtility.takeScreenshot(driver, "EmptyCart_Checkout");
    }
    
}
