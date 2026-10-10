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
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;
import objRepo.OverviewPage;

public class CheckoutIntegrationTest extends BaseClass {

    // Navigate from Home to Checkout Information.
    private CheckoutPage navigateToCheckout() throws Exception {

        HomePage home = new HomePage(driver);
        home.getAddItemToCart();
        home.getCart();
        CartPage cart = new CartPage(driver);
        cart.getCheckoutbtn();
        return new CheckoutPage(driver);
    }

    // Enter valid checkout details and navigate to Overview.
    private OverviewPage navigateToOverview() throws Exception {
        CheckoutPage checkout = navigateToCheckout();
        checkout.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        checkout.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        checkout.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        checkout.getContinuebtn();
        checkout.verifyContinuebtn();

        return new OverviewPage(driver);
    }

    // Complete checkout and reach the confirmation page.
    private CompletePage completeOrder() throws Exception {
        OverviewPage overview = navigateToOverview();
        overview.getFinishbtn();
        return new CompletePage(driver);
    }

    // Verify that the selected product remains the same in Cart.
    @Test(priority = 1, groups = {"IT", "Pos"})
    public void verifyHomeToCartProductConsistencyTest() throws Exception {

        login();
        HomePage home = new HomePage(driver);
        String homeProduct = home.getHomeProductName();
        home.getAddItemToCart();
        home.getCart();
        CartPage cart = new CartPage(driver);
        String cartProduct = cart.getProductName();

        Assert.assertEquals(cartProduct,homeProduct,"Home page product and Cart product do not match");
        Reporter.log("Home-to-Cart product consistency verified", true);
        cart.getCheckoutbtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),"Checkout Information page was not displayed");
    }

    // Verify Checkout Information to Overview navigation.
    @Test(priority = 2, groups = {"IT", "Pos"})
    public void verifyCheckoutToOverviewTest() throws Exception {

        login();
        CheckoutPage checkout = navigateToCheckout();
        checkout.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        checkout.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        checkout.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        checkout.getContinuebtn();
        checkout.verifyContinuebtn();
        Reporter.log("Checkout-to-Overview transition verified", true);
    }

    // Verify Overview to order completion.
    @Test(priority = 3, groups = {"IT", "Pos"})
    public void verifyOverviewToCompleteTest() throws Exception {

        login();
        CompletePage complete = completeOrder();
        complete.getOrderTitle();
        complete.getMessage();
        Reporter.log("Order completion and confirmation verified", true);
    }

    // Verify completed order navigation back to Home.
    @Test(priority = 4, groups = {"IT", "Pos"})
    public void verifyCompleteToHomeTest() throws Exception {
        login();
        CompletePage complete = completeOrder();
        complete.getBackToHomebtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),"User was not navigated back to the Products page");
        Reporter.log("Complete-to-Home navigation verified", true);
    }

    // Verify logout from the Checkout Information page.
    @Test(priority = 5, groups = {"IT", "Pos"})
    public void verifyCheckoutToLogoutTest() throws Exception {
        login();
        navigateToCheckout();
        logout();
        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/"),"User was not returned to the login page after logout");
        Reporter.log("Logout from Checkout verified", true);
    }

    // Verify order completion and navigation through Dynamic Catalog.
    @Test(priority = 6, groups = {"IT", "Pos"})
    public void verifyOrderToDynamicCatalogTest() throws Exception {
        login();
        CompletePage complete = completeOrder();
        complete.getMessage();
        HomePage home = new HomePage(driver);
        home.getMenubar();
        DynamicCatalogPage catalog = new DynamicCatalogPage(driver);
        catalog.getDynamicCatalog();
        Reporter.log("Order completion and Dynamic Catalog interactions executed",true);
    }
    
 // Verify that checkout cannot proceed without customer information
    @Test(priority = 7, groups = {"IT", "Neg"})
    public void invalidCheckoutMustNotOpenOverview() throws Exception {

        login();
        CheckoutPage checkout = navigateToCheckout();
        checkout.getContinuebtn();
        Assert.assertTrue(checkout.getErrorMessage().contains("First Name is required"),"Required-field error was not displayed");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),"Invalid checkout reached the Overview page");
        Reporter.log("Checkout validation verified", true);
        ScreenshotUtility.takeScreenshot(driver, "Checkout_RequiredFields");
    }
}