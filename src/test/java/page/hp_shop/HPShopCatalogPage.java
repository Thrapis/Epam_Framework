package page.hp_shop;

import model.info.CatalogProductInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HPShopCatalogPage extends HPShopPage {

    private final static By PRODUCT_NAMES_LOCATOR = By.xpath("//div[@class='tab-pane active']//div[@class='h2 product-name']/a");
    private final static By PRODUCT_OLD_PRICES_LOCATOR = By.xpath("//div[@class='tab-pane active']//div[@class='product-price']/p/span");
    private final static By PRODUCT_NEW_PRICES_LOCATOR = By.xpath("//div[@class='tab-pane active']//div[@class='product-price']/p");

    public HPShopCatalogPage() {
        super();
    }

    public List<CatalogProductInfo> getProductsFromCatalogPage() {
        List<WebElement> productNames = driver.findElements(PRODUCT_NAMES_LOCATOR);
        List<WebElement> productOldPrices = driver.findElements(PRODUCT_OLD_PRICES_LOCATOR);
        List<WebElement> productNewPrices = driver.findElements(PRODUCT_NEW_PRICES_LOCATOR);
        List<CatalogProductInfo> products = new ArrayList<CatalogProductInfo>();

        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            String oldPriceString = productOldPrices.get(i).getText();
            String newPriceString = productNewPrices.get(i).getText();
            double oldPrice = Double.parseDouble(oldPriceString
                    .substring(0, oldPriceString.length() - 5));
            double newPrice = Double.parseDouble(newPriceString
                    .substring(newPriceString.indexOf("\n") + 1, newPriceString.length() - 5));
            products.add(new CatalogProductInfo(name, oldPrice, newPrice));
        }

        return products;
    }
}
