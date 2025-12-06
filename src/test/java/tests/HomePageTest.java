package tests;

import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;

public class HomePageTest extends BaseTest {

    private HomePage homePage;
    String expectedTitle = ConfigReader.getProperty("expectedHomePageTitle");


    @BeforeMethod
    public void initPage() {
        homePage = new HomePage(driver);
    }

    @Test
    public void shouldOpenHomePageUrl()
    {
        boolean urlMatches = homePage.HomePageURL();
        log.info("Home Page URL Matches Base URL: " + urlMatches);
        Assert.assertTrue(urlMatches, "Home Page URL doesn't match.");
    }

    @Test
    public void shouldContainInsiderInTitle()
    {
        boolean titleContainsInsider = homePage.HomePageTitle();
        log.info("Home Page Title Contains '" + expectedTitle + "': " + titleContainsInsider);
        Assert.assertTrue(titleContainsInsider, "Home Page title doesn't contain 'Insider'");
    }

    @Test
    public void shouldDisplayInsiderLogo()
    {
        boolean homePageLogoVisible = homePage.isHomePageLogoVisible();
        log.info("Home Page Logo Visible: " + homePageLogoVisible);
        Assert.assertTrue(homePageLogoVisible, "Home Page Logo isn't Visible.");
    }

    @Test
    public void shouldDisplayHeroSection()
    {
        boolean heroSectionVisible = homePage.isHeroVisible();
        log.info("Hero Section on Home Page Visible: " + heroSectionVisible);
        Assert.assertTrue(heroSectionVisible, "Hero Section on Home Page isn't Visible.");
    }
}