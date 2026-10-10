package ecomSwagLabsCart;


import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.PropertyFileUtility;
import objRepo.CartPage;
import objRepo.CheckoutPage;
import objRepo.DynamicCatalogPage;
import objRepo.HomePage;
import objRepo.OverviewPage;

public class CartIntegration_Test extends BaseClass {


	//Verify that the product name on the Home Page matches the product name displayed in the Cart
	@Test(priority = 1, groups = {"IT", "Pos"})
	public void ProductInCart() throws Exception {
		login();
	    HomePage h = new HomePage(d);
        String homeProduct = h.getHomeProductName();
        h.getAddItemToCart();
	    h.getCart();
        CartPage c = new CartPage(d);
	    String cartProduct = c.getProductName();
	    Assert.assertEquals(cartProduct,homeProduct,"Home Page product and Cart product are different");
	    Reporter.log("Both product names are same", true);
    }

    //Verify navigation from the Cart back to the Home Page using the Continue Shopping button
	@Test(priority = 2, groups = {"IT", "Pos"})
    public void cartToProductTest() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();;
        h.getCart();
        CartPage cp = new CartPage(d);
        cp.getContinueShopping();;
        h.getTitle();
        logout();
    }

    //Verify integration between the Cart and the All Items page through the menu
	@Test(priority = 3, groups = {"IT", "Pos"})
    public void addProductToAllItem() throws Exception
    {
    	login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();;
        h.getCart();
        CartPage cp = new CartPage(d);
        cp.verifyProductsInCart();
        h.getMenubar();
        DynamicCatalogPage dp = new DynamicCatalogPage(d);
        dp.getallItems();
        h.getTitle();
    }

    //Verify navigation between the Cart and the Dynamic Catalog pages, including Lazy Load, Spinner, and Slider
	@Test(priority = 4, groups = {"IT", "Pos"})
    public void addproductToDynamicCatalog() throws Exception{
    	login();
        HomePage h = new HomePage(d);
        h.getAddItemToCart();;
        h.getCart();
        CartPage cp = new CartPage(d);
        cp.verifyProductsInCart();
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
	
	// Test verifies that a removed product does not reappear during checkout.
	@Test(priority = 5, groups = {"IT", "Neg"})
    public void removedProductNotInCheckoutTest() throws Exception {

        login();

        HomePage h = new HomePage(d);
        h.getAddItemToCart();
        h.getCart();

        CartPage c = new CartPage(d);
        String productName = c.getProductName();

        c.getRemovebtn();
        c.verifyProductRemoved();
        c.getCheckoutbtn();

        CheckoutPage cop = new CheckoutPage(d);
        cop.getFn(PropertyFileUtility.getDataFromProperty("FirstName"));
        cop.getLn(PropertyFileUtility.getDataFromProperty("LastName"));
        cop.getPCode(PropertyFileUtility.getDataFromProperty("Zip_code"));
        cop.getContinuebtn();

        OverviewPage op = new OverviewPage(d);
        op.verifyRemovedProductNotInOverview(productName);
    }

}
