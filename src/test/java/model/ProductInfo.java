package model;

public class ProductInfo {
    private final String name;
    private final int count;
    private final double price;

    public ProductInfo(String name, int count, double price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }
}
