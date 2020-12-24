package model.link;

public enum CatalogCategory {
    NOTEBOOKS("notebook"),
    TABLETS("planshety"),
    COMPUTERS("kompyutery"),
    MONITORS("monitory"),
    OFFICE_EQUIPMENT("orgtehnika"),
    ACCESSORIES("aksessuary"),
    CONSUMABLES("rashodnye-materialy"),
    NETWORK_HARDWARE("setevoe-oborudovanie"),
    SERVERS("servery"),
    SERVICES("uslugi"),
    RENT_AND_USED_EQUIPMENT("arenda-tehniki");

    private final String attribute;

    CatalogCategory(String attribute) {
        this.attribute = attribute;
    }

    public String getCategory() {
        return attribute;
    }
}
