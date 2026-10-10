package genricUtility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavaScriptUtility {

    JavascriptExecutor js;

    public JavaScriptUtility(WebDriver driver) {
        js = (JavascriptExecutor) driver;
    }

    public void scrollDown(int pixels) {
        js.executeScript("window.scrollBy(0," + pixels + ")");
    }
}

