package test;

import org.testng.annotations.Test;
import model.ProductInfo;
import page.HPShopCartPage;
import page.HPShopHomePage;
import org.testng.Assert;
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
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductPageUrl)
                .addToCart()
                .jumpToProductPage(secondProductPageUrl)
                .addToCart()
                .jumpToProductPage(thirdProductPageUrl)
                .addToCart()
                .openCart()
                .purgeCart();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }
}
