package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class DriverSingleton {

    private static WebDriver driver;

    private final static String GOOGLE_VERSION = "87.0.4280.88";

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (System.getProperty("browser")){
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                }
                default: {
                    WebDriverManager.chromedriver().version(GOOGLE_VERSION).setup();
                    driver = new ChromeDriver();
                    break;
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void deleteAllCookies() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}