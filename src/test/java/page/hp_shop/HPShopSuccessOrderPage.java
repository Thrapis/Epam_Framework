package page.hp_shop;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.web_pay.WebPayPaymentPage;
import wait.WaitElementMethods;

import java.util.ArrayList;

public class HPShopSuccessOrderPage extends HPShopPage {

    private final static By PAY_NOW_LOCATOR = By.xpath("//button[@type='submit' and @class='button btn-cart']");

    public HPShopSuccessOrderPage() {
        super();
    }

    public WebPayPaymentPage payNow() {
        int tabsBefore = driver.getWindowHandles().size();

        WaitElementMethods.waitForElementLocatedBy(driver, PAY_NOW_LOCATOR, WAIT_TIME_SECONDS).click();

        new WebDriverWait(driver, WAIT_TIME_SECONDS).until(ExpectedConditions.numberOfWindowsToBe(tabsBefore + 1));
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        LOGGER.info("The WebPay page is opened");
        return new WebPayPaymentPage();
    }
}
