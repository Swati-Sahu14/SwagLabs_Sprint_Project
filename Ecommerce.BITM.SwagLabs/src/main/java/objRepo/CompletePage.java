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
	public CompletePage(WebDriver d)
	{
		this.d = d;
		PageFactory.initElements(d,this);
	}
	
	@FindBy(xpath = "//span[@class='title']")
	private WebElement OrderTitle;

	@FindBy(id = "checkout_complete_container")
	private WebElement message;
	
	@FindBy(id = "back-to-products")
	private WebElement backToHomebtn;
	
	@FindBy(id = "generate-pdf-order")
	private WebElement pdfbtn;
	
	
	public void getOrderTitle() {
		String title = OrderTitle.getText();
		Reporter.log("Order Title: "+title,true);
		Assert.assertEquals(title,"Checkout: Complete!","Order completed page is not displayed");
		Reporter.log("Order Complete page is displayed",true);
	}

	public void getMessage() {
		String m = message.getText();
		Reporter.log(m,true);
		Assert.assertTrue(m.contains("Thank you for your order!"),"Order confirmation message is incorrect");
	}

	public void getBackToHomebtn() throws InterruptedException {
		backToHomebtn.click();
		Thread.sleep(2000);
		 Assert.assertTrue(d.getCurrentUrl().contains("inventory.html"),"Inventory/Home page is not displayed");
		Reporter.log("HomePage is displayed",true);
	}

	public void getPdfbtn() throws InterruptedException {
		pdfbtn.click();
		Thread.sleep(2000);
		File folder = new File(System.getProperty("user.home") + "\\Downloads");
		File[] files = folder.listFiles();
		boolean pdfFound = false;
		for (File file : files) {
			if (file.getName().endsWith(".pdf")) {
		        pdfFound = true;
		        break;
		    }
		}
		Assert.assertTrue(pdfFound, "PDF is not generated");
		Reporter.log("Pdf is downloaded successfully" , true);
	}
	
	
}
