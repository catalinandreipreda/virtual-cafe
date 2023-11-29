package Helpers;

public class Order {
    public Integer teaCount = 0;
    public Integer coffeeCount = 0;

    public Order(Integer teaCount, Integer coffeeCount) {
        this.teaCount = teaCount;
        this.coffeeCount = coffeeCount;
    }

    public boolean isEmpty(){
        return teaCount == 0 && coffeeCount == 0;
    }
}
