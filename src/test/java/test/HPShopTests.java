package test;

import model.*;
import org.testng.annotations.Test;
import page.HPShopCartPage;
import page.HPShopHomePage;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class HPShopTests extends CommonConditions {

    @Test
    public void verifyCartAfterAdditionOfProductTest() {
        double expectedCartTotalCost = 3504.0;
        int expectedProductCount = 1;
        String productName = "EliteDisplay S430c";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .searchForTerms(productName)
                .selectProductLink(productName)
                .addToCart()
                .openCart();
        List<ProductInfo> products = cartPage.getProductsFromCart();
        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
        assertThat(products.get(0).getName(), containsString(productName));
        assertThat(products.get(0).getCount(), is(equalTo(expectedProductCount)));
    }

    @Test
    public void verifyCartAfterPurgeTest() {
        String firstProductPageUrl = "https://hp-shop.by/katalog/monitory/hp/monitor-elitedisplay-s430c-5fw74aa.html";
        String secondProductPageUrl = "https://hp-shop.by/katalog/aksessuary/ryukzaki/ryukzak-hp-odyssey-redblack-backpack-x0r83aa.html";
        String thirdProductPageUrl = "https://hp-shop.by/katalog/aksessuary/myshi/mysh-hp-x1500-h4k66aa.html";

        ProductLink firstProductLink = new ProductLink(CatalogCategory.MONITORS, CatalogSubcategory.HP, "Монитор EliteDisplay S430c (5FW74AA)");
        ProductLink secondProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.BACKPACKS, "Рюкзак HP Odyssey Red/Black Backpack (X0R83AA)");
        ProductLink thirdProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.MOUSES, "Мышь HP X1500 (H4K66AA)");
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .jumpToProductPage(secondProductLink)
                .addToCart()
                .jumpToProductPage(thirdProductLink)
                .addToCart()
                .openCart()
                .purgeCart();
        assertThat(cartPage.getCartStatus(), is(equalTo(CartStatus.EMPTY)));
    }
}
