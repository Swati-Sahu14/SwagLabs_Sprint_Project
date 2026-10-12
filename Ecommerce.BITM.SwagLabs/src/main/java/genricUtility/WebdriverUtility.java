package genricUtility;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//Utility class to launch and configure different browsers
public class WebdriverUtility {
    public static WebDriver launchbrowser(String browser) {
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException("Browser name is missing in the property file.");
        }
        browser = browser.trim();
        WebDriver driver;
        if (browser.equalsIgnoreCase("chrome")) {

        	ChromeOptions settings = new ChromeOptions();

        	Map<String, Object> prefs = new HashMap<>();

        	// Disable password leak detection
        	prefs.put("profile.password_manager_leak_detection", false);

        	// Set download folder to Jenkins project workspace
        	String downloadPath = System.getProperty("user.dir")
        	        + File.separator + "downloads";

        	File folder = new File(downloadPath);

        	if (!folder.exists()) {
        	    folder.mkdirs();
        	}

        	// Configure Chrome downloads
        	prefs.put("download.default_directory", downloadPath);
        	prefs.put("download.prompt_for_download", false);
        	prefs.put("download.directory_upgrade", true);

        	// Download PDF instead of opening it in Chrome
        	prefs.put("plugins.always_open_pdf_externally", true);

        	settings.setExperimentalOption("prefs", prefs);

        	driver = new ChromeDriver(settings);

        } else if (browser.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}