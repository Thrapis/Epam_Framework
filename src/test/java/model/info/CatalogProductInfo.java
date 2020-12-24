package model.info;

public class CatalogProductInfo {
    private final String name;
    private final double oldPrice;
    private final double actualPrice;

    public CatalogProductInfo(String name, double oldPrice, double price) {
        this.name = name;
        this.oldPrice = oldPrice;
        this.actualPrice = price;
    }

    public String getName() {
        return name;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }
}
