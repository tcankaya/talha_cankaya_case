package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import utils.LoggerUtils;

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
}

