package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wait.WaitElementMethods;

public class HPShopProductPage extends HPShopPage{

    public HPShopProductPage(WebDriver driver) {
        super(driver);
    }

    public HPShopProductPage addToCart() {
        WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//button[@title='Добавить в корзину']"), WAIT_TIME_SECONDS).click();

        logger.info("Product added to cart on own page");
        return this;
    }

    public HPShopProductPage setCountOfProduct(Integer count) {
        By inputProductCountLocator = By.xpath("//input[@id='qty']");
        WebElement inputProductCount = WaitElementMethods.waitForElementLocatedBy(driver,
                inputProductCountLocator, WAIT_TIME_SECONDS);
        inputProductCount.clear();
        inputProductCount.sendKeys(count.toString());

        logger.info("Count of product was set at '" + count.toString() + "'  on own page");
        return this;
    }
}
