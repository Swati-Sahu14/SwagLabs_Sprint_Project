package ecomSwagLabsCart;


import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.HomePage;

public class CartSmoke_Test extends BaseClass {

	//Verify that the Cart page is accessible and displays the added product successfully
	@Test(priority=1,groups = {"SM", "Pos"})
    public void cartSmokeTest() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();;
        h.getCart();
        CartPage cp = new CartPage(d);
        cp.verifyProductsInCart();
        logout();
	}
	
	// Test verifies that the cart cannot be accessed without login.
	@Test(priority=2,groups = {"SM", "Neg"})
    public void verifyCartWithoutLogin() throws Exception {

        HomePage h = new HomePage(d);
        h.verifyCartRequiresLogin();

        ScreenshotUtility.takeScreenshot(d, "Cart_WithoutLogin");
    }
}
