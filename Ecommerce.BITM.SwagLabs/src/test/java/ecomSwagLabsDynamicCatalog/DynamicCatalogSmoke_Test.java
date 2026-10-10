package ecomSwagLabsDynamicCatalog;

import org.testng.annotations.Test;

import baseClass.BaseClass;
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;

public class DynamicCatalogSmoke_Test extends BaseClass {

	//Verify Lazy Load functionality and navigation to All Items.
	@Test(priority=1,groups = {"SM", "Pos"})
    public void dynamicCatalogSmoke()
            throws Exception {

    	login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getLazyLoad();
        h.getMenubar();
        dp.getallItems();
        h.getTitle();
        logout();
    }

	//Verify Spinner functionality and product display.
	@Test(priority=2,groups = {"SM", "Pos"})
    public void spinnerSmoke()
            throws Exception {
    	login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getSpinner();
        dp.verifySpinnerProducts();
        h.getMenubar();
        dp.getallItems();
        h.getTitle();
        logout();
    }

	//Verify Slider functionality and product display
	@Test(priority=3,groups = {"SM", "Pos"})
    public void sliderSmoke() throws Exception {
    	login();
        HomePage h = new HomePage(d);
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getDynamicCatalog();
        dp.getSlider();
        dp.verifySliderProducts();
        h.getMenubar();
        dp.getallItems();
        h.getTitle();
        logout();
    }
 // Test verifies that the spinner page does not display more than six product cards.
 	@Test(priority=2,groups = {"SM", "Neg"})
     public void spinnerDuplicateProductsTest() throws Exception {

         login();

         HomePage h = new HomePage(d);
         h.getMenubar();

         DynamicCatalogPage dp = new DynamicCatalogPage(d);
         dp.getDynamicCatalog();
         dp.getSpinner();

         dp.verifySpinnerProductLimit();
     }
}