package base;

import config.ConfigReader;
import driver.DriverFactory;
import listeners.RetryAnnotationTransformer;
import listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.lang.reflect.Method;

@Listeners({TestListener.class, RetryAnnotationTransformer.class})
public abstract class BaseTest {

    public WebDriver driver;
    protected Logger log;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        String browser = ConfigReader.getProperty("browser");

        driver = DriverFactory.initializeDriver(browser);
        driver.manage().window().maximize();
        driver.get(startURL());
    }

    protected String startURL() {
        return ConfigReader.getProperty("baseUrl");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        log = LogManager.getLogger(this.getClass().getName() + "." + method.getName());
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        DriverFactory.quitDriver();
        if (log != null) log.info("Browser closed.");
    }
}