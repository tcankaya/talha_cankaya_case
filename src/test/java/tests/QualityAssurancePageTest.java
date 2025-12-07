package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.QualityAssurancePage;
import utils.ElementUtils;

public class QualityAssurancePageTest extends BaseTest {

    private QualityAssurancePage qaPage;
    private ElementUtils utils;

    @Override
    protected String startURL()
    {
        return ConfigReader.getProperty("qaPageUrl");
    }

    @BeforeClass
    public void initPage()
    {
        qaPage = new QualityAssurancePage(driver);
        utils = new ElementUtils(driver);
        utils.acceptCookiesIfVisible();
    }

    @Test
    public void shouldValidateQaPage()
    {
        log.info("Opening QA Page: " + ConfigReader.getProperty("qaPageUrl"));

        Assert.assertTrue(qaPage.isPageHeadVisible(), "QA Page Head isn't visible.");
        log.info("QA page head is visible.");

        qaPage.clickToSeeAllJobs();
        log.info("'See all QA jobs' clicked.");

        String department = ConfigReader.getProperty("qaJobDepartment");
        String location = ConfigReader.getProperty("qaJobLocation");

        log.info("Filtering by department: " + department);
        qaPage.selectDepartment(department);

        log.info("Filtering by location: " + location);
        qaPage.selectLocation(location);

        log.info("Waiting filtered job list to stabilize for: " + department + " / " + location);
        qaPage.waitForFilteredJobs(department, location);

        log.info("Validating filtered job list for: " + department + " / " + location);
        qaPage.validateFilteredJobsVisible(department, location);
        log.info("Filtered job list validated successfully.");

        String expectedHref = qaPage.getFirstViewRoleHref();
        log.info("Expected View Role href: " + expectedHref);

        Assert.assertTrue(expectedHref.contains("lever.co"), "Expected View Role href to point to Lever. Found: " + expectedHref);
        String openedUrl = qaPage.openFirstViewRoleAndGetOpenedUrl();
        log.info("Opened View Role URL: " + openedUrl);

        Assert.assertNotNull(openedUrl, "Opened URL should not be null.");
        Assert.assertTrue(openedUrl.contains("lever.co"),
                "Expected redirect to Lever. Current URL: " + openedUrl);
        Assert.assertTrue(openedUrl.contains("useinsider"),
                "Expected Lever job to belong to Insider. Current URL: " + openedUrl);
    }
}
