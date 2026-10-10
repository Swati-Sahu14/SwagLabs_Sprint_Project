package objRepo;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class OverviewPage {

	WebDriver d;
	public OverviewPage(WebDriver d) {
		this.d = d;
		PageFactory.initElements(d , this);
	}
	
	@FindBy(xpath = "//div[@class='summary_info']")
	private WebElement summary;
	
	@FindBy(id = "finish")
	private WebElement finishbtn;
	
	@FindBy(id = "cancel")
	private WebElement cancelbtn;
	
	@FindBy(xpath = "//span[@class='title']")
	private WebElement OverviewTitle;
	
	@FindBy(className = "cart_item")
	private List<WebElement> overviewProducts;
	
	
	public void getSummary() {
		Reporter.log(summary.getText(),true);;
	}

	public void getFinishbtn() throws InterruptedException {
		finishbtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(d.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html","Order complete page is not displayed");
		Reporter.log("Order Completed page is displayed",true);
	}

	public void getCancelbtn() throws InterruptedException {
		cancelbtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(d.getCurrentUrl(), "https://www.saucedemo.com/inventory.html","HomePage is not displayed");
		Reporter.log("HomePage is displayed",true);
	}

	public void getOverviewTitle() {
		Assert.assertEquals(OverviewTitle.getText(),"Checkout: Overview","Checkout Overvirew page is not displayed");
		Reporter.log("Checkout Overview page is displayed",true);
	}
	
	public void verifyRemovedProductNotInOverview(String productName) {
	    Assert.assertEquals(overviewProducts.size(),0,"Removed product appeared in checkout Overview");
	    Assert.assertFalse(d.getPageSource().contains(productName),"Removed product name was found on the Overview page");
	    Reporter.log("Removed product is not displayed in checkout Overview",true);
	}

}
