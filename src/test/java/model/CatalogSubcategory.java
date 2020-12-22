package model;

public enum CatalogSubcategory {
    HP("hp"),
    BACKPACKS("ryukzaki"),
    MOUSES("myshi");

    private final String attribute;

    CatalogSubcategory(String attribute) {
        this.attribute = attribute;
    }

    public String getSubcategory() {
        return attribute;
    }
}
