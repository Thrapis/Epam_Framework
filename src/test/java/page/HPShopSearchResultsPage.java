package page;

import model.FullnessStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wait.WaitElementMethods;

public class HPShopSearchResultsPage extends HPShopPage{

    private static final String productLinkTemplate = "//a[contains(text(), '%s')]";
    private static final String addToCartButtonTemplate = "//a[contains(text(), '%s')]/../..//button[@title='В корзину']";

    public HPShopSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public HPShopProductPage selectProductLink(String name) {
        By productLinkLocator = By.xpath(String.format(productLinkTemplate, name));
        WaitElementMethods.waitForElementLocatedBy(driver, productLinkLocator, WAIT_TIME_SECONDS).click();
        return new HPShopProductPage(driver);
    }

    public HPShopSearchResultsPage addToCart(String name) {
        By addToCartButtonLocator = By.xpath(String.format(addToCartButtonTemplate, name));
        WaitElementMethods.waitForElementLocatedBy(driver, addToCartButtonLocator, WAIT_TIME_SECONDS).click();
        return this;
    }

    public FullnessStatus getSearchStatus() {
        try {
            By searchStatusLocator = By.xpath("//div[@class='search_list']/p[text()='По запросу ничего не найдено']");
            WebElement statusMessage = WaitElementMethods.waitForElementLocatedBy(driver, searchStatusLocator, WAIT_TIME_SECONDS);
            if (statusMessage.isDisplayed()) {
                return FullnessStatus.EMPTY;
            }
        } catch (Exception exception) { }

        return FullnessStatus.NOT_EMPTY;
    }
}
