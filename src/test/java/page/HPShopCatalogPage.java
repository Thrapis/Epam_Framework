package page;

import model.CatalogProductInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HPShopCatalogPage extends HPShopPage {

    public HPShopCatalogPage(WebDriver driver) {
        super(driver);
    }

    public List<CatalogProductInfo> getProductsFromCatalogPage() {
        By productNamesLocator = By.xpath("//div[@class='tab-pane active']//div[@class='h2 product-name']/a");
        By productOldPricesLocator = By.xpath("//div[@class='tab-pane active']//div[@class='product-price']/p/span");
        By productNewPricesLocator = By.xpath("//div[@class='tab-pane active']//div[@class='product-price']/p");

        List<WebElement> productNames = driver.findElements(productNamesLocator);
        List<WebElement> productOldPrices = driver.findElements(productOldPricesLocator);
        List<WebElement> productNewPrices = driver.findElements(productNewPricesLocator);

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
