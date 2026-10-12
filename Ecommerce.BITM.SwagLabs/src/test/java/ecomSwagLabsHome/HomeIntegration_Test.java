package ecomSwagLabsHome;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.ScreenshotUtility;
import objRepo.CartPage;
import objRepo.HomePage;

public class HomeIntegration_Test extends BaseClass {

	//Verify integration between the Home page and Cart by checking the Home page title, adding a product to the Cart, opening the Cart, and logging out.
    @Test(priority = 1,groups = {"IT", "Pos"})
    public void verifyHomeToCart() throws Exception {
        login();

        HomePage h = new HomePage(driver);
        h.getTitle();
        h.getAddItemToCart();
        h.getCart();

        logout();
    }

    // Verify that the Home page product sorting options work correctly for name in ascending and descending order and price in low-to-high and high-to-low order.
    @Test(priority = 2,groups = {"IT", "Pos"})
    public void verifySortingOptions() throws Exception {
        login();

        HomePage h = new HomePage(driver);
        h.getFilter("Name (A to Z)");
        h.printProductNames("A to Z");
        h.getFilter("Name (Z to A)");
        h.printProductNames("Z to A");
        h.getFilter("Price (low to high)");
        h.printProductPrices("Low to high");
        h.getFilter("Price (high to low)");
        h.printProductPrices("High to low");
        logout();
    }
    
    //Cart count should not increase without adding a product
    @Test(priority = 3,groups = {"IT", "Neg"})
    public void verifyCartWithoutAddingProduct() throws Exception {
    	 login();

         HomePage h = new HomePage(driver);
         h.verifyCartBadgeNotDisplayed();
         h.getCart();

         CartPage c = new CartPage(driver);
         c.verifyProductRemoved();
         ScreenshotUtility.takeScreenshot(driver, "Cart_Empty");
         logout();

    }

    //Product should not remain after removal
    @Test(priority = 4,groups = {"IT", "Neg"})
    public void verifyProductRemovalIntegration() throws Exception {
    	login();

        HomePage h = new HomePage(driver);
        h.getAddItemToCart();
        h.removeProductFromInventory();
        h.verifyRemoveButtonNotDisplayed();
        h.getCart();
        CartPage c = new CartPage(driver);
        c.verifyProductRemoved();
        ScreenshotUtility.takeScreenshot(driver, "Product_Removed");
        logout();

    }
}

