package ecomSwagLabsHome;

import org.testng.Assert;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import objRepo.HomePage;

public class HomeSmoke_Test extends BaseClass {

	//Verify that the home page loads successfully.
    @Test(priority = 1, groups = {"SM", "Pos"})
    public void verifyHomePageLoads() throws Exception {
        login();
        HomePage h = new HomePage(d);
        h.getTitle();
        h.getProducts();
        Assert.assertTrue(d.getCurrentUrl().contains("inventory.html"),"Home page did not load");
        logout();
    }
    
    // Verify that inventory cannot be accessed without login.
    @Test(priority = 2, groups = {"SM", "Neg"})
    public void verifyInventoryRequiresLogin() {
        HomePage h = new HomePage(d);
        h.verifyInventoryRequiresLogin();
    }

}

