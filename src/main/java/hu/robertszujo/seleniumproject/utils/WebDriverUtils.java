package hu.robertszujo.seleniumproject.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class WebDriverUtils {

    public static String captureScreenshotAsBase64(WebDriver driver) throws IOException {
        // approach for JPEG
//        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        BufferedImage startImg = ImageIO.read(screenshot);
//        BufferedImage finalImg = new BufferedImage(startImg.getWidth(), startImg.getHeight(), BufferedImage.TYPE_INT_RGB);
//        finalImg.createGraphics().drawImage(startImg, 0, 0, Color.WHITE, null);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(finalImg, "jpeg", baos);
//        return Base64.getEncoder().encodeToString(baos.toByteArray());
        // PNG approach
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

}
