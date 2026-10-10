package ecomSwagLabsCheckout;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.DataProviderClass;
import genricUtility.PropertyFileUtility;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.CompletePage;
import objRepo.HomePage;
import objRepo.OverviewPage;
import objRepo.CheckoutPage;

public class CheckoutFunctional_Test extends BaseClass {

    // Navigate to the Checkout Information page.
    private CheckoutPage navigateToCheckout() throws Exception {
        login();
        HomePage hp = new HomePage(d);
        hp.getAddItemToCart();
        hp.getCart();
        CartPage cp = new CartPage(d);
        cp.getCheckoutbtn();
        return new CheckoutPage(d);
    }

    // Enter checkout information and navigate to the Overview page.
    private OverviewPage navigateToOverview() throws Exception {
        CheckoutPage cop = navigateToCheckout();
        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        cop.getContinuebtn();
        return new OverviewPage(d);
    }

    // Complete the checkout process and reach the confirmation page.
    private CompletePage completeOrder() throws Exception {
        OverviewPage op = navigateToOverview();
        op.getFinishbtn();
        return new CompletePage(d);
    }
    
 // Retrieve the checkout validation error message.
    private String getErrorMessage() {
        return d.findElement(By.cssSelector("[data-test='error']")).getText();
    }

    //Verify checkout information can be entered.
    @Test(priority = 1, groups = {"FT", "Pos"})
    public void verifyCheckoutInformationTest() throws Exception {

        CheckoutPage cop = navigateToCheckout();
        String firstName =PropertyFileUtility.getDataFromProperty("FirstName");
        String lastName =PropertyFileUtility.getDataFromProperty("LastName");
        String zipCode =PropertyFileUtility.getDataFromProperty("Zip_code");
        cop.getFn(firstName);
        cop.getLn(lastName);
        cop.getPCode(zipCode);
        cop.verifyFirstName(firstName);
        cop.verifyLastName(lastName);
        cop.verifyPCode(zipCode);
    }

    //Verify Continue navigates to the Overview page.
    @Test(priority = 2, groups = {"FT", "Pos"})
    public void verifyContinueButtonTest() throws Exception {

        CheckoutPage cop = navigateToCheckout();
        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        cop.getContinuebtn();
        cop.verifyContinuebtn();
    }

    // Verify Cancel returns to the previous shopping page.
    @Test(priority = 3, groups = {"FT", "Pos"})
    public void verifyCheckoutCancelButtonTest() throws Exception {
        CheckoutPage cop = navigateToCheckout();
        cop.getCancelbtn();
        cop.verifyCancelbtn();
    }

    //Verify product information on the Overview page.
    @Test(priority = 4, groups = {"FT", "Pos"})
    public void verifyOrderSummaryTest() throws Exception {
        OverviewPage op = navigateToOverview();
        op.getSummary();
    }

    //Verify Cancel on the Overview page.
    @Test(priority = 5, groups = {"FT", "Pos"})
    public void verifyOverviewCancelButtonTest() throws Exception {
        OverviewPage op = navigateToOverview();
        op.getCancelbtn();
    }

    //Verify the order confirmation message.
    @Test(priority = 6, groups = {"FT", "Pos"})
    public void verifyOrderConfirmationMessageTest() throws Exception {
        CompletePage cp = completeOrder();
        cp.getMessage();
    }

    // Verify Back to Home navigation after order completion.
    @Test(priority = 7, groups = {"FT", "Pos"})
    public void verifyBackToHomeButtonTest() throws Exception {
        CompletePage cp = completeOrder();
        cp.getBackToHomebtn();
    }

    //Verify the PDF receipt functionality.
    @Test(priority = 8, groups = {"FT", "Pos"})
    public void verifyPdfReceiptTest() throws Exception {
        CompletePage cp = completeOrder();
        cp.getPdfbtn();
    }
    
    // Verify validation when all checkout fields are empty.
    @Test(priority = 9,groups = {"FT", "Neg"},dataProvider = "emptyAllFields",dataProviderClass = DataProviderClass.class)
    public void emptyAllFieldsTest(String firstName, String lastName, String postalCode) throws Exception {
        CheckoutPage cop = navigateToCheckout();
        cop.getFn(firstName);
        cop.getLn(lastName);
        cop.getPCode(postalCode);
        cop.getContinuebtn();
        Assert.assertTrue(getErrorMessage().contains("First Name is required"),"First Name validation message was not displayed");
        Assert.assertTrue(d.getCurrentUrl().contains("checkout-step-one.html"),"Checkout proceeded despite empty fields");
        Reporter.log("All-empty checkout validation verified", true);
        ScreenshotUtility.takeScreenshot(d, "Checkout_AllFieldsEmpty");
    }

    // Verify validation when First Name is empty.
    @Test(priority = 10,groups = {"FT", "Neg"},dataProvider = "emptyFirstName",dataProviderClass = DataProviderClass.class)
    public void emptyFirstNameTest(String firstName, String lastName, String postalCode) throws Exception {
        CheckoutPage cop = navigateToCheckout();
        cop.getFn(firstName);
        cop.getLn(lastName);
        cop.getPCode(postalCode);
        cop.getContinuebtn();

        Assert.assertTrue(getErrorMessage().contains("First Name is required"),"First Name validation message was not displayed");
        Assert.assertTrue(d.getCurrentUrl().contains("checkout-step-one.html"),"Checkout proceeded without First Name");
        Reporter.log("Empty First Name validation verified", true);
        ScreenshotUtility.takeScreenshot(d, "Checkout_EmptyFirstName");
    }

    //Verify validation when Last Name is empty.
    @Test(priority = 11,groups = {"FT", "Neg"},dataProvider = "emptyLastName",dataProviderClass = DataProviderClass.class)
    public void emptyLastNameTest(String firstName, String lastName, String postalCode) throws Exception {

        CheckoutPage cop = navigateToCheckout();
        cop.getFn(firstName);
        cop.getLn(lastName);
        cop.getPCode(postalCode);
        cop.getContinuebtn();
        Assert.assertTrue(getErrorMessage().contains("Last Name is required"),"Last Name validation message was not displayed");

        Assert.assertTrue( d.getCurrentUrl().contains("checkout-step-one.html"),"Checkout proceeded without Last Name");
        Reporter.log("Empty Last Name validation verified", true);
        ScreenshotUtility.takeScreenshot(d, "Checkout_EmptyLastName");
    }
}
