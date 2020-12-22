package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import driver.DriverSingleton;
import org.testng.annotations.Listeners;
import util.TestListener;

@Listeners({TestListener.class})
public abstract class CommonConditions {

    protected WebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void setup() {
        driver = DriverSingleton.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCookies() {
        DriverSingleton.deleteAllCookies();
    }

    @AfterTest(alwaysRun = true)
    public void shutdown() {
        DriverSingleton.closeDriver();
    }
}
