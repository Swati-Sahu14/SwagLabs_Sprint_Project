package ecomSwagLabsCheckout;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.PropertyFileUtility;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.CheckoutPage;
import objRepo.CompletePage;
import objRepo.HomePage;
import objRepo.OverviewPage;

public class CheckoutSystem_Test extends BaseClass {

    // Verify the complete order flow from login to logout.
    @Test(priority = 1, groups = {"ST", "Pos"})
    public void orderProductTest() throws Exception {

        login();
        HomePage home = new HomePage(d);
        home.getTitle();
        home.getAddItemToCart();
        home.getCart();

        CartPage cart = new CartPage(d);
        cart.getProductName();
        cart.getCheckoutbtn();

        CheckoutPage cop = new CheckoutPage(d);

        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));

        cop.getContinuebtn();
        cop.verifyContinuebtn();

        OverviewPage overview = new OverviewPage(d);
        overview.getSummary();
        overview.getFinishbtn();

        CompletePage complete = new CompletePage(d);
        complete.getOrderTitle();
        complete.getMessage();
        complete.getBackToHomebtn();
        home.getTitle();
        logout();

        Reporter.log("Complete order flow executed successfully", true);
    }
    
    //Incomplete checkout information must be rejected
	@Test(priority = 1,groups = {"ST", "Neg"})
    public void incompleteOrderMustNotComplete() throws Exception {

        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(d);
        cop.getFn("TestUser");
        cop.getContinuebtn();
        String errorMessage = cop.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Last Name is required"),"Missing Last Name was not rejected");
        Assert.assertFalse(d.getCurrentUrl().contains("checkout-complete.html"),"Incomplete order reached the completion page");
        Reporter.log("Incomplete checkout information rejected successfully", true);
        ScreenshotUtility.takeScreenshot(d, "Checkout_IncompleteInfo");
    }
}