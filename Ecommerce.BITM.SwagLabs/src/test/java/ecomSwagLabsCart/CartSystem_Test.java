package ecomSwagLabsCart;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.HomePage;

public class CartSystem_Test extends BaseClass {

	//Verify the complete cart workflow, including adding a product, viewing the cart,
	@Test(priority = 1, groups = {"ST", "Pos"})
    public void completeCartFlow() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();;
        h.getCart();
        CartPage cp = new CartPage(d);
        cp.verifyProductsInCart();
        cp.getRemovebtn();
        cp.verifyProductRemoved();
        cp.getContinueShopping();
        h.getTitle();
        logout();
    }
    
    //Verify that the cart cannot be accessed after logout.
	@Test(priority = 1, groups = {"ST", "Neg"})
    public void cartAccessAfterLogoutTest() throws Exception {

        login();
        logout();

        HomePage h = new HomePage(d);
        h.verifyCartRequiresLogin();

        ScreenshotUtility.takeScreenshot(d, "Cart_AfterLogout");
    }

    //Verify that a removed product remains absent after refresh.
	@Test(priority = 1, groups = {"ST", "Neg"})
    public void removedProductAfterRefreshTest() throws Exception {

        login();

        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();

        CartPage c = new CartPage(d);
        c.getRemovebtn();
        c.verifyProductRemoved();
        c.refreshAndVerifyCartEmpty();
    }

}
