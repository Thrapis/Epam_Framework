package test;

import org.testng.annotations.Test;
import page.hp_shop.HPShopCartPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MultipleAdditionTests extends CommonConditions {

    private final static int countOfProduct = 2_000;

    @Test(enabled = true)
    public void verifyAddingALargeNumberOfProductsTest() {
        double expectedCartTotalCost = 1_026_000.0;

        HPShopCartPage cartPage = new HPShopCartPage()
                .jumpToProductPage(firstProductLink)
                .setCountOfProduct(countOfProduct)
                .addToCart()
                .openCart();;

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
    }

    @Test(enabled = true, dependsOnMethods={"verifyAddingALargeNumberOfProductsTest"})
    public void verifyChangeTheQuantityOfProductsTest() {
        int newCountOfProduct = 500;
        double expectedCartTotalCost = 256_500.0;
        String productName = "Монитор HP EliteDisplay E223 (1FH45AA)";

        HPShopCartPage cartPage = new HPShopCartPage().setProductCountFromCart(productName, newCountOfProduct);

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
    }
}
