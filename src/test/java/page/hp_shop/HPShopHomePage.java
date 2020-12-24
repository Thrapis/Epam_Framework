package page.hp_shop;

public class HPShopHomePage extends HPShopPage {

    public HPShopHomePage() {
        super();
    }

    public HPShopHomePage openPage() {
        driver.get(HOMEPAGE_URL);

        LOGGER.info("Home page opened");
        return this;
    }
}
