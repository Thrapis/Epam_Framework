package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.WaitElementMethods;

import java.util.ArrayList;

public class HPShopSuccessOrderPage extends HPShopPage {

    public HPShopSuccessOrderPage(WebDriver driver) {
        super(driver);
    }

    public WebPayPaymentPage payNow() {
        int tabsBefore = driver.getWindowHandles().size();

        WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//span[text()='Оплатить сейчас']/../..//button"), WAIT_TIME_SECONDS).click();

        new WebDriverWait(driver, WAIT_TIME_SECONDS).until(
                ExpectedConditions.numberOfWindowsToBe(tabsBefore + 1));

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        logger.info("The WebPay page is opened");
        return new WebPayPaymentPage(driver);
    }
}
