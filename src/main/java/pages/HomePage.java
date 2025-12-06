package pages;

import config.ConfigReader;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static utils.WaitUtils.waitForVisibility;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean HomePageURL() {
        String actualUrl = driver.getCurrentUrl();
        log.info("Home Page URL: " + actualUrl);
        return actualUrl.equals(ConfigReader.getProperty("baseUrl"));
    }

    public boolean HomePageTitle()
    {
        String actualTitle = getPageTitle();
        String expectedTitle = ConfigReader.getProperty("expectedHomePageTitle");
        log.info("Home Page Title: " + actualTitle);
        log.info("Expected Home Page Title: " + expectedTitle);
        return actualTitle.contains(expectedTitle);
    }

    @FindBy(css = "img[alt='insider_logo']")
    private WebElement insiderLogo;

    public boolean isHomePageLogoVisible()
    {
        try
        {
            waitForVisibility(driver, insiderLogo, 5);
            return true;
        }
        catch (TimeoutException e)
        {
            return false;
        }
    }

    @FindBy(css = "div#desktop_hero_24.hp_hero_with_animation")
    private WebElement heroSection;

    public boolean isHeroVisible() {
        return waitForVisibility(driver, heroSection, 5).isDisplayed();
    }
}