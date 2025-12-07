package pages;

import base.BasePage;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static utils.ElementUtils.moveToElement;
import static utils.WaitUtils.*;

public class QualityAssurancePage extends BasePage
{
    public QualityAssurancePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // QA Page Locators

    @FindBy(id="page-head")
    private WebElement qaPageHead;

    @FindBy(css = "a[href*='/careers/open-positions/?department=qualityassurance']")
    private WebElement seeAllQaJobsBtn;

    @FindBy(xpath = "//h3[contains(text(),'Browse')]")
    private WebElement browseText;

    private final By jobItems = By.cssSelector(".position-list-item");

    // Department Filter
    @FindBy(css = "span[aria-labelledby='select2-filter-by-department-container']")
    private WebElement departmentSelect2Container;

    // Location Filter
    @FindBy(css = "span[aria-labelledby='select2-filter-by-location-container']")
    private WebElement locationSelect2Container;

    // Common Filter Options Locator
    @FindBy(css = ".select2-results__options")
    private WebElement filterOptions;

    // Actions

    public boolean isPageHeadVisible()
    {
        waitForDocumentReadyState();
        waitForVisibility(driver, qaPageHead);
        return qaPageHead.isDisplayed();
    }

    public void clickToSeeAllJobs()
    {
        scrollToElementAndWaitVisible(seeAllQaJobsBtn, "See All QA Jobs Button");
        click(seeAllQaJobsBtn, "See All QA Jobs Button");
    }

    private void selectFromFilter(WebElement filterContainer, String filterName, String value) {

        waitForAnyVisible(driver, jobItems);

        By optionLocator = By.xpath("//li[contains(@class,'select2-results__option') and normalize-space(.)='" + value + "']");

        int attempt = 0;

        while (attempt < 2)
        {
            try
            {
                waitForClickability(driver, filterContainer);
                click(filterContainer, filterName);

                waitForVisibility(driver, filterOptions);
                waitForAnyVisible(driver, By.cssSelector("li.select2-results__option"));
                waitForVisibility(driver, optionLocator);

                WebElement option = driver.findElement(optionLocator);
                click(option, filterName + " Option: " + value);
                return;
            }
            catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e)
            {
                if (attempt == 0)
                {
                    click(browseText, "Browse Text");
                }
                else
                {
                    throw e;
                }
            }
            finally
            {
                attempt++;
            }
        }
    }

    public void selectDepartment(String department) {
        scrollToElementAndWaitVisible(browseText, "Browse Text");
        selectFromFilter(departmentSelect2Container, "Department Filter", department);
    }

    public void selectLocation(String location) {
        selectFromFilter(locationSelect2Container, "Location Filter", location);
    }

    public void waitForFilteredJobs(String expectedDepartment, String expectedLocation) {

        int waitSec = Integer.parseInt(ConfigReader.getProperty("pageLoadWait"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSec));

        final By deptBy = By.cssSelector(".position-department");
        final By locBy  = By.cssSelector(".position-location");

        wait.until(d -> {
            List<WebElement> jobs = d.findElements(jobItems);
            if (jobs.isEmpty()) return false;

            for (WebElement job : jobs) {
                try {
                    String deptText = job.findElement(deptBy).getText().trim();
                    String locText  = job.findElement(locBy).getText().trim();

                    if (!expectedDepartment.equals(deptText)) return false;
                    if (!expectedLocation.equals(locText)) return false;

                } catch (org.openqa.selenium.StaleElementReferenceException |
                         org.openqa.selenium.NoSuchElementException ex) {
                    return false;
                }
            }
            return true;
        });
    }

    public void validateFilteredJobsVisible(String expectedDepartment, String expectedLocation) {

        waitForAnyVisible(driver, jobItems);

        for (WebElement job : driver.findElements(jobItems)) {

            String deptText = job.findElement(By.cssSelector(".position-department")).getText().trim();
            String locText  = job.findElement(By.cssSelector(".position-location")).getText().trim();

            if (!deptText.equals(expectedDepartment) || !locText.equals(expectedLocation)) {
                throw new AssertionError(
                        "Job mismatch. Expected: " + expectedDepartment + " / " + expectedLocation +
                                " but found: " + deptText + " / " + locText
                );
            }
        }
    }

    public String getFirstViewRoleHref() {

        By jobItem = By.cssSelector(".position-list-item");
        waitForAnyVisible(driver, jobItem);

        WebElement firstJob = driver.findElements(jobItem).get(0);

        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(firstJob)
                .perform();

        WebElement viewRole = firstJob.findElement(By.linkText("View Role"));
        return viewRole.getAttribute("href");
    }

    public String openFirstViewRoleAndGetOpenedUrl() {

        By jobItem = By.cssSelector(".position-list-item");
        waitForAnyVisible(driver, jobItem);

        WebElement firstJob = driver.findElements(jobItem).get(0);

        // Hover first so View Role becomes visible
        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(firstJob)
                .perform();

        WebElement viewRole = firstJob.findElement(By.linkText("View Role"));

        // Capture windows before click
        java.util.Set<String> before = driver.getWindowHandles();

        scrollToElementAndWaitVisible(viewRole, "View Role Button");
        click(viewRole, "View Role Button");

        // Identify new window
        java.util.Set<String> after = driver.getWindowHandles();
        String newWindow = null;

        for (String handle : after) {
            if (!before.contains(handle)) {
                newWindow = handle;
                break;
            }
        }

        if (newWindow == null) {
            // If site sometimes opens same tab, fallback to current URL
            return driver.getCurrentUrl();
        }

        driver.switchTo().window(newWindow);

        // Optional: ensure the new page is ready-ish
        waitForDocumentReadyState();

        return driver.getCurrentUrl();
    }
}