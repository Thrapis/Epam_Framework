package page.hp_shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import wait.WaitElementMethods;

public class HPShopProductPage extends HPShopPage{

    private static final By ADD_TO_CART_BUTTON_LOCATOR = By.xpath("//button[@title='Добавить в корзину']");
    private static final By INPUT_PRODUCT_COUNT_LOCATOR = By.xpath("//input[@id='qty']");;

    public HPShopProductPage() {
        super();
    }

    public HPShopProductPage addToCart() {
        WaitElementMethods.waitForElementLocatedBy(driver, ADD_TO_CART_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();

        LOGGER.info("Product added to cart on own page");
        return this;
    }

    public HPShopProductPage setCountOfProduct(Integer count) {
        WebElement inputProductCount = WaitElementMethods.waitForElementLocatedBy(driver, INPUT_PRODUCT_COUNT_LOCATOR, WAIT_TIME_SECONDS);
        inputProductCount.clear();
        inputProductCount.sendKeys(count.toString());

        LOGGER.info("Count of product was set at '" + count.toString() + "'  on own page");
        return this;
    }
}
