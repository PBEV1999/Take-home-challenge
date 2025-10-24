package util;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class ScreenshotUtil {

    // Captura del viewport actual (lo que está visible)
    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
            System.out.println(" Screenshot guardado: " + name);
        } catch (Exception e) {
            System.out.println(" Error al capturar screenshot: " + e.getMessage());
        }
    }
}
