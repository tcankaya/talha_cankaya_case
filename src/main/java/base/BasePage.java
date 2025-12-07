package base;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import utils.LoggerUtils;
import java.time.Duration;
import static utils.WaitUtils.*;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.log = LoggerUtils.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log.info("Clicked on element: " + elementName);
    }

    protected void type(WebElement element, String text, String elementName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated((By) element)).sendKeys(text);
        log.info("Typed: " + text + " on the element: " + elementName);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    protected int getCount(By locator) {
        return driver.findElements(locator).size();
    }

    protected void moveToElementAndWaitVisible(WebElement element, String name) {
        new Actions(driver).moveToElement(element).perform();
        waitVisible(driver, element);
        log.info("Hovered and visible: " + name);
    }

    protected void moveToElementAndWaitClickable(WebElement element, String name) {
        new Actions(driver).moveToElement(element).perform();
        waitVisible(driver, element);
        waitClickable(driver, element);
        log.info("Hovered and clickable: " + name);
    }

    protected void scrollToElementAndWaitVisible(WebElement element, String name) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );

        try {
            waitInViewport(driver, element, 3);
        } catch (Exception ignored) {}

        waitVisible(driver, element);

        log.info("Scrolled and visible: " + name);
    }

    protected void waitForDocumentReadyState() {
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigReader.getIntProperty("pageLoadWait"))
        );

        wait.until(d -> "complete".equals(
                ((JavascriptExecutor) d).executeScript("return document.readyState")
        ));
    }
}

