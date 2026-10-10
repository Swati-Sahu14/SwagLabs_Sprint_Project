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
        HomePage home = new HomePage(driver);
        home.getTitle();
        home.getAddItemToCart();
        home.getCart();

        CartPage cart = new CartPage(driver);
        cart.getProductName();
        cart.getCheckoutbtn();

        CheckoutPage cop = new CheckoutPage(driver);

        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));

        cop.getContinuebtn();
        cop.verifyContinuebtn();

        OverviewPage overview = new OverviewPage(driver);
        overview.getSummary();
        overview.getFinishbtn();

        CompletePage complete = new CompletePage(driver);
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
        HomePage h = new HomePage(driver);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(driver);
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(driver);
        cop.getFn("TestUser");
        cop.getContinuebtn();
        String errorMessage = cop.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Last Name is required"),"Missing Last Name was not rejected");
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-complete.html"),"Incomplete order reached the completion page");
        Reporter.log("Incomplete checkout information rejected successfully", true);
        ScreenshotUtility.takeScreenshot(driver, "Checkout_IncompleteInfo");
    }
}