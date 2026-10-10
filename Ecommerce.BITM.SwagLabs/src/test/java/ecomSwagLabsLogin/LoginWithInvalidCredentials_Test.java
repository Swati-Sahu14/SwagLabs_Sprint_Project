package ecomSwagLabsLogin;

import org.testng.Reporter;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import genricUtility.DataProviderClass;
import genricUtility.ScreenshotUtility;
import objRepo.LoginPage;

public class LoginWithInvalidCredentials_Test extends BaseClass {

    // Verify that an error message is displayed for invalid login credentials
    @Test(groups = {"FT", "Neg"},dataProvider = "loginDetails",dataProviderClass = DataProviderClass.class)
    public void invalidLoginTest(String username, String password) throws Exception {

        LoginPage l = new LoginPage(d);
        l.login(username, password);
        l.getErrorMessage();
        Reporter.log("Invalid login validation completed", true);
        ScreenshotUtility.takeScreenshot(d, "InvalidLogin");
    }
    
// // Verify that the login form handles boundary input values correctly
//    @Test(priority = 2,groups = {"BVA", "Neg"},dataProvider = "loginDetails",dataProviderClass = DataProviderClass.class)
//    public void loginBoundaryTest(String username, String password) throws Exception {
//
//        LoginPage l = new LoginPage(d);
//        l.login(username, password);
//        l.getErrorMessage();
//        Reporter.log("Boundary login input validated successfully", true);
//    }
}