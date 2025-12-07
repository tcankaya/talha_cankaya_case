package utils;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class WaitUtils {

    private WaitUtils() {}

    private static int defaultWaitSeconds() {
        return ConfigReader.getIntProperty("explicitWait");
    }

    private static WebDriverWait wait(WebDriver driver) {
        return wait(driver, defaultWaitSeconds());
    }

    private static WebDriverWait wait(WebDriver driver, int timeoutSeconds)
    {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    // -------------------- Visibility --------------------

    public static WebElement waitVisible(WebDriver driver, WebElement element)
    {
        return wait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitVisible(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        return wait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitVisible(WebDriver driver, By locator)
    {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitVisible(WebDriver driver, By locator, int timeoutSeconds)
    {
        return wait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitAllVisible(WebDriver driver, By locator)
    {
        return wait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // -------------------- Clickability --------------------

    public static WebElement waitClickable(WebDriver driver, WebElement element)
    {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitClickable(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        return wait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitClickable(WebDriver driver, By locator)
    {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    // -------------------- Collection / Count --------------------

    public static void waitAnyVisible(WebDriver driver, By locator)
    {
        wait(driver).until(d -> d.findElements(locator).stream().anyMatch(WebElement::isDisplayed));
    }

    public static void waitCountMoreThan(WebDriver driver, By locator, int oldCount)
    {
        wait(driver).until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, oldCount));
    }

    // -------------------- Viewport --------------------

    public static void waitInViewport(WebDriver driver, WebElement element)
    {
        waitInViewport(driver, element, defaultWaitSeconds());
    }

    public static void waitInViewport(WebDriver driver, WebElement element, int timeoutSeconds)
    {
        wait(driver, timeoutSeconds).until(d -> (Boolean) ((JavascriptExecutor) d).executeScript(
                "const r = arguments[0].getBoundingClientRect();" +
                        "return (r.bottom > 0 && r.right > 0 && " +
                        "r.top < (window.innerHeight || document.documentElement.clientHeight) && " +
                        "r.left < (window.innerWidth || document.documentElement.clientWidth));",
                element
        ));
    }

    // -------------------- Simple UI actions --------------------

    public static void pressEscape(WebDriver driver)
    {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
}