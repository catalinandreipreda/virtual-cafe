package Helpers;

public enum DrinkType {
    COFFEE("Coffee", 45), TEA("Tea", 30);
    public final String label;
    public final int secondsToBrew;

    DrinkType(final String label, final int brewTime) {
        this.label = label;
        this.secondsToBrew = brewTime;
    }
}
