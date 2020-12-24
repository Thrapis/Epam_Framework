package model.order;

public enum PaymentMethod {
    PAYMENT_UPON_PICKUP("Оплата при самовывозе"),
    PAYMENT_BY_BANK_TRANSFER("Оплата по безналичному расчету для юридических лиц и ИП."),
    CASH_PAYMENT_TO_THE_COURIER("Оплата наличными курьеру"),
    ONLINE_PAYMENT_BY_CREDIT_CARD_ON_THE_WEBSITE("Онлайн оплата банковской картой на сайте"),
    PAYMENT_BY_CREDIT("Оформление в кредит"),
    PAYMENT_BY_INSTALLMENT_CARD("Оплата картой рассрочки (Халва, Черепаха, Карта Покупок, КартаFun)"),
    PAYMENT_BY_INSTALLMENTS("Оформление в рассрочку");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getPaymentMethod() {
        return value;
    }
}
