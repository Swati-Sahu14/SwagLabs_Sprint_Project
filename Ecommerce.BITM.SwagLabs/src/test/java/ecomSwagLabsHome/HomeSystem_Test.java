package ecomSwagLabsHome;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.PropertyFileUtility;
import genricUtility.ScreenshotUtility;
import objRepo.HomePage;
import objRepo.Negative_HomePage;

public class HomeSystem_Test extends BaseClass {

    // Verify the complete Home page workflow
    @Test(priority = 1, groups = {"ST", "Pos"})
    public void verifyCompleteHomeFlow() throws Exception {

        login();
        HomePage h = new HomePage(d);
        h.getTitle();
        h.getProducts();
        h.addAllProductsToCart();
        h.verifyProductsAdded();
        h.getCart();

        Assert.assertTrue(d.getCurrentUrl().contains("cart.html"),"Cart page was not opened");
        logout();
    }

    // Verify that inventory is inaccessible without login
    @Test(priority = 2, groups = {"ST", "Neg"})
    public void verifyHomeWithoutLogin() throws IOException {
    	d.get(PropertyFileUtility.getDataFromProperty("url") + "cart.html");

        Negative_HomePage h = new Negative_HomePage(d);
        Assert.assertFalse(h.isHomePageDisplayed(),"Inventory is accessible without login");
        Assert.assertTrue( h.isLoginPageDisplayed(),"Login page was not displayed");
        Reporter.log("Inventory access is blocked without login", true);
        ScreenshotUtility.takeScreenshot(d, "InventoryAccess_WithoutLogin");
    }

    // Verify that inventory remains inaccessible after logout
    @Test(priority = 3, groups = {"ST", "Neg"})
    public void verifyHomeAfterLogout() throws Exception {

        login();
        logout();
        d.get(PropertyFileUtility.getDataFromProperty("url") + "cart.html");
        Negative_HomePage h = new Negative_HomePage(d);
        Assert.assertFalse(h.isHomePageDisplayed(),"Inventory is accessible after logout");
        Assert.assertTrue( h.isLoginPageDisplayed(),"Login page was not displayed after logout");
        Reporter.log("Inventory access is blocked after logout", true);
        ScreenshotUtility.takeScreenshot(d, "InventoryAccess_AfterLogout");
    }
}