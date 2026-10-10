package ecomSwagLabsHome;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.ScreenshotUtility;
import objRepo.HomePage;

public class HomeFunctional_Test extends BaseClass {

    //Verify the Home page title, product display, menu list, and Cart navigation.
	@Test(priority = 1,groups = {"FT", "Pos"})
	public void verifyHomePageByMenuList() throws Exception {

		login();
		HomePage h = new HomePage(driver);
		h.getTitle();
		h.getProducts();
		h.getMenubar();
		h.getMenuList();
		h.getCart();
	}

    //Verify that a product can be removed successfully and the removal is verified.
	@Test(priority = 2,groups = {"FT", "Pos"})
    public void removeProductTest() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.getRemovePrd();
        h.verifyRemovePrd();
    }

    //Verify that all products can be added to the Cart and that the added products are verified.
	@Test(priority = 3,groups = {"FT", "Pos"})
    public void verifyAddAllProducts() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.addAllProductsToCart();
        h.verifyProductsAdded();
        logout();
    }
    
 // Test verifies that the Remove button is not displayed before adding a product.
	@Test(priority = 4,groups = {"FT", "Pos"})
    public void verifyRemoveButtonState() throws Exception {

        login();
        HomePage h = new HomePage(driver);
        h.verifyRemoveButtonBeforeAdding();
        ScreenshotUtility.takeScreenshot(driver, "RemoveButton_NotDisplayed");
        logout();
    }

    // Test verifies that products are displayed on the inventory page.
	@Test(priority = 5,groups = {"FT", "Pos"})
    public void verifyProductsNotMissing() throws Exception {

        login();
        HomePage h = new HomePage(driver);
        h.getProducts();
        ScreenshotUtility.takeScreenshot(driver, "Products_Displayed");
        logout();
    }
	
	 //Product should not be removed before adding it
		@Test(priority = 6,groups = {"FT", "Neg"})
		public void verifyRemoveButtonState1() throws Exception {
			 login();
		        HomePage h = new HomePage(driver);
		        h.verifyRemoveButtonBeforeAdding();
		        ScreenshotUtility.takeScreenshot(driver, "RemoveButton_NotDisplayed");
		        logout();
	    }

	 //Products should not be accessible without login
		@Test(priority = 7,groups = {"FT", "Neg"})
	 public void verifyProductsWithoutLogin() throws Exception {
			login();
	        HomePage h = new HomePage(driver);
	        h.getProducts();
	        ScreenshotUtility.takeScreenshot(driver, "Products_Displayed");
	        logout();

	 }
}