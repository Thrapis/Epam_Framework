package page;

import model.CatalogLink;
import model.ProductLink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import model.SearchAttribute;
import wait.WaitElementMethods;

public abstract class HPShopPage {

    protected WebDriver driver;

    protected static final String HOMEPAGE_URL = "https://hp-shop.by/";
    protected static final long WAIT_TIME_SECONDS = 10;
    protected static final Logger logger = LogManager.getRootLogger();

    private static final String attributeSelectionTemplate = "//label[text()='%s']/..";

    public HPShopPage(WebDriver driver) {
        this.driver = driver;
    }

    public HPShopPage setSearchAttribute(SearchAttribute attribute) {
        By attributeComboBoxLocator = By.xpath("//div[@id='product-categori']");

        WaitElementMethods.waitForElementLocatedBy(driver, attributeComboBoxLocator,
                WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitForElementLocatedBy(driver, By.xpath(String.format(attributeSelectionTemplate,
                attribute.getAttribute())), WAIT_TIME_SECONDS).click();

        logger.info("The attribute '" + attribute.getAttribute() + "' has been set");
        return this;
    }

    public HPShopSearchResultsPage searchForTerms(String term) {
        WebElement searchInputField = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//input[@name='search']"), WAIT_TIME_SECONDS);
        searchInputField.sendKeys(term);
        searchInputField.submit();

        logger.info("Searched for term '" + term + "'");
        return new HPShopSearchResultsPage(driver);
    }

    public HPShopCartPage openCart() {
        WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@class='header-cart-area']"), WAIT_TIME_SECONDS).click();

        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        logger.info("Cart opened");
        return new HPShopCartPage(driver);
    }

    public HPShopCatalogPage jumpToCatalogPage(CatalogLink catalogLink) {
        driver.get(catalogLink.getLink());

        logger.info("There was a jump to the catalog page '" + catalogLink.getLink() + "'");
        return new HPShopCatalogPage(driver);
    }

    public HPShopProductPage jumpToProductPage(ProductLink productLink) {
        driver.get(productLink.getLink());

        logger.info("There was a jump to the product page '" + productLink.getLink() + "'");
        return new HPShopProductPage(driver);
    }
}
