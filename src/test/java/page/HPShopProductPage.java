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
        WebElement addToCartButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//button[@title='Добавить в корзину']"), WAIT_TIME_SECONDS);
        addToCartButton.click();
        logger.info("Product added to cart");
        return this;
    }

    public HPShopProductPage setCountOfProduct(Integer count) {
        By inputProductCountLocator = By.xpath("//input[@id='qty']");
        WebElement inputProductCount = WaitElementMethods.waitForElementLocatedBy(driver, inputProductCountLocator,
                WAIT_TIME_SECONDS);
        inputProductCount.clear();
        inputProductCount.sendKeys(count.toString());
        return this;
    }
}
