package pages;

import base.BasePage;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static utils.WaitUtils.*;

public class CareersPage extends BasePage {

    public CareersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Navbar - Careers Locators
    @FindBy(xpath = "//a[contains(@class,'dropdown-toggle') and normalize-space()='Company']")
    private WebElement companyMenu;

    @FindBy(css = "a.dropdown-sub[href='https://useinsider.com/careers/']")
    private WebElement careersMenuItem;

    @FindBy(css = "#page-head")
    private WebElement careersPageHead;


    // Teams Locators
    @FindBy(css = "#career-find-our-calling")
    private WebElement teamsBlock;

    private final By teamItems = By.cssSelector(".job-item");

    @FindBy(xpath = "//a[normalize-space()='See all teams']")
    private WebElement seeAllTeamsBtn;


    // Locations Locators
    @FindBy(id="career-our-location")
    private WebElement locationsBlock;

    @FindBy(css = "ul.glide__slides")
    private WebElement locationsSlidesContainer;

    @FindBy(css = "ul.glide__slides li.glide__slide")
    private List<WebElement> locationSlides;

    @FindBy(css = "ul.glide__slides li.glide__slide--active .location-info p.mb-0")
    private WebElement activeCityName;

    @FindBy(className = "icon-arrow-right")
    private WebElement locationsNextArrow;

    @FindBy(className = "icon-arrow-left")
    private WebElement locationsPrevArrow;


    // Life at Insider Locators

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']")
    private WebElement lifeTitle;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section[1]")
    private WebElement lifeSection;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section[1]//div[contains(@class,'elementor-widget-text-editor')]//p")
    private WebElement lifeDescription;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section[1]//div[contains(@class,'swiper-container')]")
    private WebElement lifeSwiperContainer;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section[1]//div[contains(@class,'swiper-slide')]")
    private java.util.List<WebElement> lifeSlides;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section[1]//div[contains(@class,'swiper-slide-active')]//div[contains(@class,'elementor-carousel-image')]")
    private WebElement lifeActiveSlideImage;

    // Actions for Careers Page
    public void openCareersFromCompanyMenu() {
        waitForVisibility(driver, companyMenu);
        moveToElementAndWaitClickable(companyMenu, "Company Menu");

        waitForVisibility(driver, careersMenuItem);
        waitForClickability(driver, careersMenuItem);
        click(careersMenuItem, "Company > Careers");
    }

    public boolean isPageHeadVisible() {
        waitForDocumentReadyState();
        waitForVisibility(driver, careersPageHead);
        return careersPageHead.isDisplayed();
    }

    // Actions for Teams Section

    public boolean isTeamsBlockVisible() {
        waitForDocumentReadyState();
        scrollToElementAndWaitVisible(teamsBlock, "Teams Section");
        return teamsBlock.isDisplayed();
    }

    public int getTeamItemsCount() {
        return getCount(teamItems);
    }

    public boolean areTeamItemsVisible() {
        waitForAllVisible(driver, teamItems);
        return !driver.findElements(teamItems).isEmpty();
    }

    public boolean seeAllTeamsButtonVisible() {
        scrollToElementAndWaitVisible(seeAllTeamsBtn, "See All Teams Button");
        return seeAllTeamsBtn.isDisplayed();
    }

    public void viewAllTeams() {
        int oldCount = getTeamItemsCount();
        click(seeAllTeamsBtn, "See All Teams Button");

        waitForCountMoreThan(driver, teamItems, oldCount);
    }

    // Actions for Locations Section

    public boolean isLocationsBlockVisible() {
        waitForDocumentReadyState();
        waitForVisibility(driver, locationsBlock);
        return locationsBlock.isDisplayed();
    }

    public int getLocationsCount() {
        waitForVisibility(driver, locationSlides.get(0));
        return locationSlides.size();
    }

    public String getActiveCity() {
        waitForVisibility(driver, activeCityName);
        return activeCityName.getText().trim();
    }

    public void clickNextLocation() {
        scrollToElementAndWaitVisible(locationsNextArrow, "Next Arrow Button");
        waitForClickability(driver, locationsNextArrow);
        click(locationsNextArrow,"Next Arrow Button");
    }

    public void waitForActiveCityToChange(String oldCity) {
        new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("explicitWait")))
                .until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElement(activeCityName, oldCity)
                ));
    }


    // Actions for Life at Insider Section

    public boolean isLifeAtInsiderSectionVisible() {
        scrollToElementAndWaitVisible(lifeSection, "Life at Insider Section");
        return lifeSection.isDisplayed();
    }

    public boolean isLifeAtInsiderTitleVisible() {
        scrollToElementAndWaitVisible(lifeTitle, "Life at Insider Title");
        return lifeTitle.isDisplayed();
    }

    public boolean isLifeAtInsiderDescriptionVisible() {
        scrollToElementAndWaitVisible(lifeDescription, "Life at Insider Description");
        return lifeDescription.isDisplayed();
    }

    public int getLifeSlidesCount() {
        scrollToElementAndWaitVisible(lifeSwiperContainer, "Life at Insider Swiper Size");
        return lifeSlides.size();
    }

    public boolean hasActiveLifeSlide() {
        scrollToElementAndWaitVisible(lifeSwiperContainer, "Life at Insider Swiper");
        waitForVisibility(driver, lifeActiveSlideImage);
        return lifeActiveSlideImage.isDisplayed();
    }

}