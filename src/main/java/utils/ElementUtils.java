package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static utils.WaitUtils.waitForVisibility;


public class ElementUtils {

    private WebDriver driver;

    public ElementUtils(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "wt-cli-accept-all-btn")
    private WebElement acceptAllCookiesBtn;

    public void acceptCookiesIfVisible()
    {
        try
        {
            waitForVisibility(driver,acceptAllCookiesBtn);
            acceptAllCookiesBtn.click();
        }
        catch (TimeoutException | NoSuchElementException ignored) {}
    }
}