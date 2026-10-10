package objRepo;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import genricUtility.JavaScriptUtility;
import genricUtility.PropertyFileUtility;

public class DynamicCatalogPage {

    WebDriver d;
    WebDriverWait wait;
    JavaScriptUtility js;

    public DynamicCatalogPage(WebDriver d) {
        this.d = d;
        this.wait = new WebDriverWait(d, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(d, this);
        js = new JavaScriptUtility(d);
    }


    @FindBy(id = "dynamic_catalog_sidebar_link")
    private WebElement dynamicCatalog;

    @FindBy(id = "dynamic_catalog_lazy_load_link")
    private WebElement lazyLoad;

    @FindBy(id = "dynamic_catalog_spinner_link")
    private WebElement spinner;

    @FindBy(id = "dynamic_catalog_slider_link")
    private WebElement slider;
    
    @FindBy(xpath = "//div[@class='dynamic_catalog_lazy_load_container']")
    private List<WebElement> products_Lazy;
 
    @FindBy(xpath = "//div[@class='dynamic_catalog_card']")
    private List<WebElement> products_spinner;
    
    @FindBy(xpath = "//div[@class='dynamic_catalog_slider_container']")
    private List<WebElement> products_slider;

    @FindBy(xpath = "//button[@class='dynamic_catalog_slider_dot']")
    private List<WebElement> sliderDots;
    
    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItems;
    
    public void getallItems() throws InterruptedException {
    	allItems.click();
    	Thread.sleep(2000);
    }

	public void getDynamicCatalog() throws InterruptedException {
		dynamicCatalog.click();
		Thread.sleep(600);
		Reporter.log("Dynamic Catalog is opened", true);
	}

	public void getLazyLoad() throws InterruptedException {
		lazyLoad.click();
		Thread.sleep(2000);
		Reporter.log("Lazy Load is opened", true);
	}

	public void getSpinner() throws InterruptedException {
		spinner.click();
		Thread.sleep(2000);
		Reporter.log("Spinner is opened", true);
	}

	public void getSlider() throws InterruptedException {
		slider.click();
		Thread.sleep(2000);
		Reporter.log("Slider is opened", true);
	}

	public void getProducts_spinner() {
		for(WebElement ele :products_spinner)
			Reporter.log(ele.getText(), true);
	}

	public void getProducts_slider() throws InterruptedException {
		for (int i = 0; i < sliderDots.size(); i++) {
	        sliderDots.get(i).click();
	        Thread.sleep(500);
	        String product = products_slider.get(0).getText();
	        Reporter.log("Slider " + (i + 1) + " Product: " + product,true);
	        Assert.assertTrue(!product.isEmpty(),"Product is not displayed on slider " + (i + 1));
	    }
	    Reporter.log("All slider products are displayed successfully", true);
	}

	public void verifyLazyLoadProducts() {
        Assert.assertTrue(products_Lazy.size() > 0,"Lazy Load products are not displayed");
        Reporter.log("Lazy Load products displayed",true);
    }
	
	 public void verifySpinnerProducts() {
	     Assert.assertTrue(products_spinner.size() > 0,"Spinner products are not displayed");
	     Reporter.log("Spinner products displayed",true);
	  }

	 public void verifySliderProducts() {
	     Assert.assertTrue(products_slider.size() > 0,"Slider products are not displayed");
	     Reporter.log("Slider products displayed",true);
	 }
	 
	 public void verifySliderDots() {
		    Assert.assertTrue(sliderDots.size() > 0,"Slider navigation dots are not displayed");
		    Set<String> dotIdentifiers = new HashSet<>();
		    for (WebElement dot : sliderDots) {
		        Assert.assertTrue(dot.isDisplayed(),"Slider navigation dot is not displayed");
		        Assert.assertTrue(dot.isEnabled(),"Slider navigation dot is not enabled");
		        String identifier = dot.getAttribute("outerHTML");
		        Assert.assertTrue(dotIdentifiers.add(identifier),"Duplicate slider navigation dots were found");
		    }
		    Reporter.log("Slider dots are displayed, enabled, and unique",true);
	}
	 public void verifyAllItemsWithoutSliderDots() {
		    Assert.assertTrue(d.getCurrentUrl().contains("inventory.html"),"All Items page was not opened");
		    Assert.assertEquals(sliderDots.size(),0,"Slider navigation dots are still displayed on All Items");
		    Reporter.log("Slider elements are not displayed on All Items",true);
	 }
	 public void verifySpinnerProductLimit() {
		    Assert.assertTrue(products_spinner.size() <= 6,"Spinner page displays more than six product cards");
		    Reporter.log("Spinner page does not display more than six product cards",true);
	 }


	 public void verifyRepeatedScrollStability() throws InterruptedException {

	     for (int i = 1; i <= 5; i++) {
	         ((org.openqa.selenium.JavascriptExecutor) d).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	         Thread.sleep(1000);
	         Assert.assertTrue(products_Lazy.size() > 0,"Products disappeared during repeated scrolling");
	         Reporter.log("Products remain available after scroll " + i,true
	     );
	     }
	     Reporter.log("Repeated scrolling completed successfully", true);
	 }

	 @FindBy(id = "login-button")
	 private WebElement loginButton;

	
	 public void verifyCatalogAccessAfterLogout() throws IOException {

	     d.get(PropertyFileUtility.getDataFromProperty("url") + "inventory.html");

	     wait.until(ExpectedConditions.visibilityOf(loginButton));

	     Assert.assertTrue(
	         loginButton.isDisplayed(),
	         "Logged-out user was not redirected to login"
	     );

	     Reporter.log("Catalog is not accessible after logout", true);
	 }
	 
	 public void verifyLazyLoadOnScroll() throws InterruptedException {
		 for (int i = 1; i <= 5; i++) {
		        js.scrollDown(700);
		        Thread.sleep(1000);
		        Assert.assertTrue(products_Lazy.get(0).isDisplayed(),"Products are not displayed after scroll " + i);
		        Reporter.log("Products are displayed after scroll " + i,true);
		    }
		 Reporter.log("Lazy Load is working after multiple scrolling",true);
	}
}
