package ecomSwagLabsDynamicCatalog;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;

public class DynamicCatalogSystem_Test extends BaseClass {
	//Verify the complete Dynamic Catalog flow across Lazy Load, Spinner, Slider, and All Items.
	@Test(priority=1,groups = {"ST", "Pos"})
    public void completeDynamicCatalogFlow() throws Exception {
    	login();
        HomePage h = new HomePage(driver);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
        dp.getDynamicCatalog();
        dp.getLazyLoad();
        h.getMenubar();
        dp.getDynamicCatalog();
        dp.getSpinner();
        h.getMenubar();
        dp.getDynamicCatalog();
        dp.getSlider();
        h.getMenubar();
        dp.getallItems();
        h.getTitle();
        logout();
    }
	
	//Verify that products remain available after repeated scrolling.
		@Test(priority=2,groups = {"ST", "Neg"})
	    public void repeatedScrollStabilityTest() throws Exception {

	        login();

	        HomePage h = new HomePage(driver);
	        h.getMenubar();

	        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
	        dp.getDynamicCatalog();
	        dp.getLazyLoad();

	        dp.verifyRepeatedScrollStability();
	    }

		//Verify that the catalog cannot be accessed after logout.
		@Test(priority=3,groups = {"ST", "Neg"})
	    public void catalogAccessAfterLogoutTest() throws Exception {

	        login();
	        logout();

	        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
	        dp.verifyCatalogAccessAfterLogout();
	    }

}