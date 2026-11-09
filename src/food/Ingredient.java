package food;

public enum Ingredient{
    CANDY("конфет", true, false),
    MARMALADE("мармелада", true, false),
    CHOCOLATE("шоколада", true, false),
    STRAWBERRY("земляники", true, false),
    APPLE("яблок", true, false),
    PEAR("груш", true, false),
    MUSHROOM("грибов", false, true),
    POTATO("картошки", false, true),
    MEAT("мяса", false, true),
    ONION("лука", false, true);

    private final String title;
    private final boolean sweet;
    private final boolean solid;

    Ingredient(String title, boolean sweet, boolean solid) {
        this.title = title;
        this.sweet = sweet;
        this.solid = solid;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSweet() {
        return sweet;
    }

    public boolean isSolid() {
        return solid;
    }
}
