package utils;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private static int getDefaultWait()
    {
        return ConfigReader.getIntProperty("explicitWait");
    }

    // Method Overload - Visibility (WebElement)
    public static WebElement waitForVisibility(WebDriver driver, WebElement element)
    {
        return waitForVisibility(driver, element, getDefaultWait());
    }

    public static WebElement waitForVisibility(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibility(WebDriver driver, By locator)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(getDefaultWait()));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Method Overload - Clickability
    public static WebElement waitForClickability(WebDriver driver, WebElement element)
    {
        return waitForClickability(driver, element, getDefaultWait());
    }

    public static WebElement waitForClickability(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(getDefaultWait()));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static void waitForAnyVisible(WebDriver driver, By locator)
    {
        int waitSec = Integer.parseInt(ConfigReader.getProperty("pageLoadWait"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSec));

        wait.until(d -> d.findElements(locator).stream().anyMatch(WebElement::isDisplayed));
    }

    public static void waitForCountMoreThan(WebDriver driver, By locator, int oldCount)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(getDefaultWait()));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, oldCount));
    }

    public static void waitForInViewport(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(d -> (Boolean) ((JavascriptExecutor) d).executeScript(
                "const r = arguments[0].getBoundingClientRect();" +
                        "return (r.bottom > 0 && r.right > 0 && " +
                        "r.top < (window.innerHeight || document.documentElement.clientHeight) && " +
                        "r.left < (window.innerWidth || document.documentElement.clientWidth));",
                element
        ));
    }

    protected void pressEscape(WebDriver driver)
    {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
}