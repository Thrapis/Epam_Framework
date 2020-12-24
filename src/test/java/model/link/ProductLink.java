package model.link;

import util.Transliterator;

public class ProductLink {

    private final static String PRODUCT_LINK_URL_TEMPLATE = "https://hp-shop.by/katalog/%s/%s/%s.html";

    private final CatalogCategory category;
    private final CatalogSubcategory subcategory;
    private final String name;

    public ProductLink(CatalogCategory category, CatalogSubcategory subcategory, String name) {
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
    }

    public String getLink() {
        String transliterateName = Transliterator.transliterate(name);

        return String.format(PRODUCT_LINK_URL_TEMPLATE, category.getCategory(), subcategory.getSubcategory(), transliterateName);
    }
}
