package ecomSwagLabsDynamicCatalog;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;

public class DynamicCatalogFunctional_Test extends BaseClass {

	//Verify that Lazy Load products are displayed correctly and additional products load when scrolling.
	 @Test(priority = 1, groups = {"FT", "Pos"})
    public void lazyLoadTest() throws Exception {

        login();
        HomePage h = new HomePage(driver);
        h.getMenubar();

        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
        dp.getDynamicCatalog();
        dp.getLazyLoad();
        dp.verifyLazyLoadProducts();
        dp.verifyLazyLoadOnScroll();
    }


    // Verify that the Spinner page displays its products correctly and allows product interaction.
	 @Test(priority = 2, groups = {"FT", "Pos"})
    public void spinnerTest() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.getMenubar();

        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
        dp.getDynamicCatalog();
        dp.getSpinner();
        dp.verifySpinnerProducts();
        dp.getProducts_spinner();
    }


    //Verify that the Slider page displays its products correctly and allows product interaction.
	 @Test(priority = 3, groups = {"FT", "Pos"})
    public void sliderTest() throws Exception {
        login();
        HomePage h = new HomePage(driver);
        h.getMenubar();

        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
        dp.getDynamicCatalog();
        dp.getSlider();
        dp.verifySliderProducts();
        dp.getProducts_slider();
    }
	 
	// Test verifies that slider navigation dots are unique and usable.
	 @Test(priority = 3, groups = {"FT", "Neg"})
	    public void sliderDuplicateDotsTest() throws Exception {

	        login();
	        HomePage h = new HomePage(driver);
	        h.getMenubar();
	        DynamicCatalogPage dp = new DynamicCatalogPage(driver);
	        dp.getDynamicCatalog();
	        dp.getSlider();
	        dp.verifySliderDots();
	    }


}