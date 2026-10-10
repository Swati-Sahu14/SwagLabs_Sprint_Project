package genricUtility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtility {

    public static void takeScreenshot(WebDriver d, String name)
            throws IOException {

        // Capture screenshot
        TakesScreenshot tks = (TakesScreenshot) d;
        File src = tks.getScreenshotAs(OutputType.FILE);

        // Set destination path
        File des = new File("./Screenshots/" + name + ".png");

        // Copy screenshot to folder
        FileHandler.copy(src, des);

        System.out.println("Screenshot saved successfully: "+ des.getAbsolutePath());
    }
}



















