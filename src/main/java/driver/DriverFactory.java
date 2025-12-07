package driver;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initializeDriver(String browser)
    {
        if(browser == null) browser = "chrome";
        browser = browser.toLowerCase();

        switch(browser)
        {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            case "safari":
                driver.set(new SafariDriver());
                break;
            case "edge":
                driver.set(new EdgeDriver());
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
        }
        return driver.get();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if(driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}