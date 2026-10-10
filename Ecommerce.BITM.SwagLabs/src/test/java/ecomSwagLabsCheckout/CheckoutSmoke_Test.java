package ecomSwagLabsCheckout;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.PropertyFileUtility;
import objRepo.CartPage;
import objRepo.CompletePage;
import objRepo.HomePage;
import objRepo.OverviewPage;
import objRepo.CheckoutPage;

public class CheckoutSmoke_Test extends BaseClass {

    // Verify the complete checkout flow
    @Test(groups = {"SM", "Pos"})
    public void basicTest() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getTitle();
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getProductName();
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(d);
        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        cop.getContinuebtn();
        cop.verifyContinuebtn();
        OverviewPage op = new OverviewPage(d);
        op.getSummary();
        op.getFinishbtn();
        CompletePage cp = new CompletePage(d);
        cp.getMessage();
        cp.getBackToHomebtn();
        logout();
    }
    
    // Verify checkout rejects empty customer information
    @Test(groups = {"SM", "Neg"})
    public void checkoutMustRejectEmptyInformation() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();
        CartPage c = new CartPage(d);
        c.getCheckoutbtn();
        CheckoutPage cop = new CheckoutPage(d);
        cop.getContinuebtn();
        String errorMessage = cop.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("First Name is required"),"Empty checkout information was not rejected");
        Assert.assertTrue(d.getCurrentUrl().contains("checkout-step-one.html"),"Checkout moved forward despite invalid information");
        Reporter.log("Checkout correctly rejected empty customer information", true);
    }
}