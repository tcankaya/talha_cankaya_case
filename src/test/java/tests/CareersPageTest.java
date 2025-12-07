package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareersPage;
import utils.ElementUtils;

public class CareersPageTest extends BaseTest {

    private CareersPage careersPage;
    private ElementUtils utils;

    @BeforeClass
    public void initPage()
    {
        careersPage = new CareersPage(driver);
        utils = new ElementUtils(driver);
        utils.acceptCookiesIfVisible();
    }

    @Test(priority = 1)
    public void shouldOpenCareersPage()
    {
        careersPage.openCareersFromCompanyMenu();
    }

    @Test(priority = 2)
    public void isCareersPageOpened()
    {
       boolean headPageVisible = careersPage.isPageHeadVisible();
       log.info("Careers Page Head Visible: " + headPageVisible);
       Assert.assertTrue(headPageVisible, "Careers Page Head isn't visible.");
    }

    @Test(priority = 3)
    public void shouldDisplayTeams()
    {
       boolean teamsBlockVisible = careersPage.isTeamsBlockVisible();
       log.info("Teams Section Visible: " + teamsBlockVisible);
       Assert.assertTrue(teamsBlockVisible, "Teams Section isn't visible.");

       boolean defaultTeamsVisible = careersPage.areTeamItemsVisible();
       log.info("Default Teams Visible: " + defaultTeamsVisible);
       Assert.assertTrue(defaultTeamsVisible, "Default teams are not visible.");

       int defaultCount = careersPage.getTeamItemsCount();
       log.info("Default Teams Count: " + defaultCount);
       Assert.assertTrue(defaultCount > 0, "Default teams list is empty.");

       boolean seeAllTeamsBtnVisible = careersPage.seeAllTeamsButtonVisible();
       log.info("See All Teams Button Visible: " + seeAllTeamsBtnVisible);
       Assert.assertTrue(seeAllTeamsBtnVisible, "See All Teams Button isn't visible.");

       careersPage.viewAllTeams();
       int allCount = careersPage.getTeamItemsCount();
       log.info("All Teams Count After Click: " + allCount);
       Assert.assertTrue(allCount > defaultCount, "Expected more teams after clicking See All Teams, but count did not increase.");
    }

    @Test(priority = 4)
    public void shouldValidateLocations()
    {
        Assert.assertTrue(careersPage.isLocationsBlockVisible(), "Locations block isn't visible.");

        int count = careersPage.getLocationsCount();
        log.info("Locations count: " + count);
        Assert.assertTrue(count > 0, "No location slides found.");

        String before = careersPage.getActiveCity();
        log.info("Active city before: " + before);

        careersPage.clickNextLocation();
        careersPage.waitForActiveCityToChange(before);

        String after = careersPage.getActiveCity();
        log.info("Active city after: " + after);

        Assert.assertNotEquals(after, before, "Card did not move.");
    }

    @Test(priority = 5)
    public void shouldValidateLifeAtInsider()
    {
        boolean sectionVisible = careersPage.isLifeAtInsiderSectionVisible();
        log.info("Life at Insider section visible: " + sectionVisible);
        Assert.assertTrue(sectionVisible);

        boolean titleVisible = careersPage.isLifeAtInsiderTitleVisible();
        log.info("Life at Insider title visible: " + titleVisible);
        Assert.assertTrue(titleVisible);

        boolean descVisible = careersPage.isLifeAtInsiderDescriptionVisible();
        log.info("Life at Insider description visible: " + descVisible);
        Assert.assertTrue(descVisible);

        int slidesCount = careersPage.getLifeSlidesCount();
        log.info("Life at Insider slides count: " + slidesCount);
        Assert.assertTrue(slidesCount > 0, " No slides found.");

        boolean hasActive = careersPage.hasActiveLifeSlide();
        log.info("Life at Insider active slide exists: " + hasActive);
        Assert.assertTrue(hasActive, "No active slide found.");
    }
}