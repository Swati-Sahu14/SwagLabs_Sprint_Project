package ecomSwagLabsDynamicCatalog;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;

public class DynamicCatalogIntegration_Test extends BaseClass {

	//Verify navigation from Lazy Load to All Items.
	@Test(priority = 1, groups = {"IT", "Pos"})
    public void loginToLazyLoad() throws Exception {
    	login();
        HomePage h = new HomePage(d);
        h.getMenubar(); 
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getLazyLoad();
        h.getMenubar();
        dp.getallItems();
        h.getTitle();
    }

	//Verify Spinner functionality and navigation to All Items
    @Test(priority = 2, groups = {"IT", "Pos"})
    public void loginToSpinner() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getSpinner();
        dp.verifySpinnerProducts();
        h.getMenubar();
        dp.getallItems();
    }

    //Verify Slider functionality and navigation to All Items
    @Test(priority = 3, groups = {"IT", "Pos"})
    public void loginToSlider() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getSlider();
        dp.verifySliderProducts();
        h.getMenubar();
        dp.getallItems();
    }
    
    //Verify navigation across Lazy Load, Spinner, and Slider
    @Test(priority = 4, groups = {"IT", "Pos"})
    public void DynamicCatalogTOAll() throws Exception {
    	login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getLazyLoad();
        h.getMenubar();
        dp.getDynamicCatalog();
        dp.getSpinner();
        h.getMenubar();
        dp.getDynamicCatalog();
        dp.getSlider();
    }
    
 // Test verifies that slider elements disappear when navigating to All Items.
    @Test(priority = 5, groups = {"IT", "Neg"})
    public void sliderProductsNotRemainInAllItemsTest() throws Exception {

        login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getSlider();
        dp.verifySliderProducts();
        h.getMenubar();
        dp.getallItems();
        dp.verifyAllItemsWithoutSliderDots();
    }
}