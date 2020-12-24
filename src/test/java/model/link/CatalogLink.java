package model.link;

public class CatalogLink {

    private final static String CATALOG_LINK_URL_TEMPLATE = "https://hp-shop.by/katalog/%s/%s/";

    private final CatalogCategory category;
    private final CatalogSubcategory subcategory;

    public CatalogLink(CatalogCategory category, CatalogSubcategory subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getLink() {
        return String.format(CATALOG_LINK_URL_TEMPLATE, category.getCategory(), subcategory.getSubcategory());
    }
}
