package model.navigation;

public enum SearchAttribute {
    ALL("Весь католог"),
    NOTEBOOKS("Ноутбуки"),
    TABLETS("Планшеты"),
    COMPUTERS("Компьютеры"),
    MONITORS("Мониторы"),
    OFFICE_EQUIPMENT("Оргтехника"),
    ACCESSORIES("Аксессуары");

    private final String attribute;

    SearchAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
