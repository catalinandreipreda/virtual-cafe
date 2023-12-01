package Helpers;

public class Drink {
    public DrinkType type;

    public String customerName;

    public Drink(DrinkType type, String customerName) {
        this.type = type;
        this.customerName = customerName;
    }

    @Override
    public String toString(){
        return this.type.label + " for " + this.customerName;
    }
}
