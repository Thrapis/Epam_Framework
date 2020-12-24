package model.order;

public enum DeliveryMethod {
    PICK_UP_BY_YOURSELF("Забрать самостоятельно"),
    BY_COURIER_IN_MINSK("Курьером по г. Минску"),
    BY_COURIER_IN_BELARUS("Курьером по Беларуси");

    private final String value;

    DeliveryMethod(String value) {
        this.value = value;
    }

    public String getDeliveryMethod() {
        return value;
    }
}
