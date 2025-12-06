package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {

    private WebDriver driver;

    public ElementUtils(WebDriver driver)
    {
        this.driver = driver;
    }

    public void click(WebElement element)
    {
        element = WaitUtils.waitForClickability(driver, element, 10);
        element.click();
    }

    public void type(WebElement element, String text)
    {
        WebElement input = WaitUtils.waitForVisibility(driver, element, 10);
        input.sendKeys(text);
    }

    public String getText(WebElement element)
    {
        element = WaitUtils.waitForVisibility(driver, element, 10);
        return element.getText();
    }
}