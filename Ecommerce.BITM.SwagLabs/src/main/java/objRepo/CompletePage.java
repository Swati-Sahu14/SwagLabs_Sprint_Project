package objRepo;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class CompletePage {

	WebDriver d;
	// Constructor to initialize WebDriver and PageFactory
	public CompletePage(WebDriver d)
	{
		this.d = d;
		PageFactory.initElements(d,this);
	}
	
	// Locators for the checkout completion page elements
	@FindBy(xpath = "//span[@class='title']")
	private WebElement OrderTitle;

	@FindBy(id = "checkout_complete_container")
	private WebElement message;
	
	@FindBy(id = "back-to-products")
	private WebElement backToHomebtn;
	
	@FindBy(id = "generate-pdf-order")
	private WebElement pdfbtn;
	
	// Verifies the checkout completion page title
	public void getOrderTitle() {
		String title = OrderTitle.getText();
		Reporter.log("Order Title: "+title,true);
		Assert.assertEquals(title,"Checkout: Complete!","Order completed page is not displayed");
		Reporter.log("Order Complete page is displayed",true);
	}

	// Verifies the order confirmation message
	public void getMessage() {
		String m = message.getText();
		Reporter.log(m,true);
		Assert.assertTrue(m.contains("Thank you for your order!"),"Order confirmation message is incorrect");
	}

	// Navigates back to the home page and verifies the URL
	public void getBackToHomebtn() throws InterruptedException {
		backToHomebtn.click();
		Thread.sleep(2000);
		 Assert.assertTrue(d.getCurrentUrl().contains("inventory.html"),"Inventory/Home page is not displayed");
		Reporter.log("HomePage is displayed",true);
	}

	public void getPdfbtn() throws InterruptedException {

	    // Click the Generate PDF button
	    pdfbtn.click();

	    // Use the same download folder configured in ChromeOptions
	    String downloadPath = System.getProperty("user.dir")
	            + File.separator + "downloads";

	    File folder = new File(downloadPath);

	    // Wait up to 30 seconds for the PDF to download
	    boolean pdfFound = false;
	    long endTime = System.currentTimeMillis() + 30000;

	    while (System.currentTimeMillis() < endTime) {

	        File[] files = folder.listFiles();

	        if (files != null) {
	            for (File file : files) {

	                if (file.isFile()
	                        && file.getName().toLowerCase().endsWith(".pdf")
	                        && file.length() > 0) {

	                    // Ensure the PDF download is complete
	                    File crdownload = new File(
	                            downloadPath,
	                            file.getName() + ".crdownload");

	                    if (!crdownload.exists()) {
	                        pdfFound = true;
	                        System.out.println("PDF downloaded successfully: "+ file.getAbsolutePath());
	                        break;
	                    }
	                }
	            }
	        }

	        if (pdfFound) {
	            break;
	        }
	        Thread.sleep(1000);
	    }

	    // Verify the PDF download
	    Assert.assertTrue(pdfFound,"PDF file was not downloaded successfully. Checked folder: "+ downloadPath);
	}

}
