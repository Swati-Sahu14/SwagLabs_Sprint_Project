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
        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getCarttitle();
    }

    //Verify that the products added to the cart are displayed correctly on the cart page
	@Test(priority = 2,groups = {"FT", "Pos"})
	   public void ProductsInCartPage() throws Exception {
		   login();
		   HomePage h = new HomePage(d);
		   h.getAddItemToCart();
	       h.getCart();
		   CartPage c = new CartPage(d);
		   c.verifyProductsInCart();
	   }

    //Verify that clicking the Continue Shopping button navigates back to the home page
	@Test(priority = 3,groups = {"FT", "Pos"})
    public void continueShoppingTest() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getContinueShopping();
        h.getTitle();
    }

    //Verify that a product can be removed from the cart successfully
	@Test(priority = 4,groups = {"FT", "Pos"})
    public void removeTest() throws Exception {
    login();
    HomePage h = new HomePage(d);
    h.getAddItemToCart();
    h.getCart();
    CartPage c = new CartPage(d);
    c.getRemovebtn();
    c.verifyProductRemoved();
    }

    //Verify that clicking the Checkout button initiates the checkout process
    @Test(priority = 5,groups = {"FT", "Pos"})
    public void verifyCheckoutbtnTest() throws Exception {
    	login();
    	HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getCheckoutbtn();
    }
 
    // Verify checkout behavior when the cart is empty.
    @Test(priority = 6,groups = {"FT", "Neg"})
    public void emptyCartCheckoutTest() throws Exception {

        login();
        HomePage h = new HomePage(d);
        h.getCart();
        CartPage c = new CartPage(d);
        c.verifyCartEmpty();
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(d);
        cop.verifyCheckoutInformationPage();
        ScreenshotUtility.takeScreenshot(d, "EmptyCart_Checkout");
    }
    
}
