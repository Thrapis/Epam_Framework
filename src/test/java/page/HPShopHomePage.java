package page;

import org.openqa.selenium.WebDriver;

public class HPShopHomePage extends HPShopPage {

    public HPShopHomePage(WebDriver driver) {
        super(driver);
        driver.get(HOMEPAGE_URL);
    }
}
