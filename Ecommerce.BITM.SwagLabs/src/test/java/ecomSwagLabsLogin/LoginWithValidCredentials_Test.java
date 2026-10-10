package ecomSwagLabsLogin;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.DataProviderClass;
import objRepo.HomePage;
import objRepo.LoginPage;

public class LoginWithValidCredentials_Test extends BaseClass {

    // Verify that the user is able to log in with valid credentials
    @Test(priority = 1,groups = {"FT", "Pos"})
    public void validLoginTest() throws IOException, InterruptedException {

        login();
        String title = d.getTitle();
        String currentUrl = d.getCurrentUrl();
        Reporter.log("Home Page Title: " + title, true);
        Reporter.log("Current URL: " + currentUrl, true);

        Assert.assertTrue(title.contains("Swag Labs"),"Home page title is incorrect");
        Assert.assertTrue(currentUrl.contains("inventory.html"),"User is not navigated to the Products page");
        Reporter.log("Login successful", true);
    }

    // Verify that the user is able to log in using multiple valid credentials
    @Test(priority = 2, groups = {"FT", "Pos"},
          dataProvider = "loginDetailsxlx",
          dataProviderClass = DataProviderClass.class)
    public void validLoginTestWithMultipleCredentials(String username, String password) {

        LoginPage l = new LoginPage(d);
        l.getUsername(username);
        l.getPassword(password);
        l.getLogin();
        String title = d.getTitle();
        String currentUrl = d.getCurrentUrl();
        Reporter.log("Username: " + username, true);
        Reporter.log("Home Page Title: " + title, true);
        Reporter.log("Current URL: " + currentUrl, true);

        Assert.assertTrue(title.contains("Swag Labs"),"Home page title is incorrect for user: " + username);
        Assert.assertTrue(currentUrl.contains("inventory.html"),"Products page is not displayed for user: " + username);
        Reporter.log("Login successful for user: " + username, true);
    }
    
 // Verify that login navigates to the Products page
    @Test(priority = 3, groups = {"IT", "Pos"})
    public void verifyLoginNavigatesToProductPage() throws IOException, InterruptedException {

    		login();
        HomePage hp = new HomePage(d);

        Assert.assertEquals(hp.getProductsTitle() ,"Products","Login did not navigate to the Products page");
        Reporter.log("Products page is displayed successfully", true);
    }
}