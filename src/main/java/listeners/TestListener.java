package listeners;

import driver.DriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.logging.log4j.LogManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Logger logger = LogManager.getLogger(
                result.getTestClass().getName() + "." + result.getName()
        );
        logger.info("RUNNING TEST <---");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logger logger = LogManager.getLogger(
                result.getTestClass().getName() + "." + result.getName()
        );
        logger.info("PASS\n");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getName();

        Logger logger = LogManager.getLogger(result.getTestClass().getName() + "." + testName);

        logger.error("TEST FAILED: " + testName, result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            logger.warn("Driver is null, screenshot skipped for test: " + testName);
            return;
        }

        try
        {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Path destPath = Paths.get("reports", "screenshots",
                    testName + "_" + System.currentTimeMillis() + ".png"
            );

            Files.createDirectories(destPath.getParent());
            Files.write(destPath, screenshot);

            logger.error("Screenshot saved: " + destPath.toAbsolutePath());
        }

        catch (Exception e)
        {
            logger.error("Screenshot capture/save failed for test: " + testName, e);
        }
    }

    @Override public void onTestSkipped(ITestResult result) {}

    @Override public void onFinish(ITestContext context)
    {
        System.out.println("\nLOG DETAILS: reports/test.log" );
        System.out.println("FAILURE SCREENSHOTS: reports/screenshots");
    }
}