package Helpers;

public class Order {
    public Integer teaCount = 0;
    public Integer coffeeCount = 0;

    public Order(){
        this.teaCount = 0;
        this.coffeeCount = 0;
    }

    public Order(Integer teaCount, Integer coffeeCount) {
        this.teaCount = teaCount;
        this.coffeeCount = coffeeCount;
    }

    public void append(Order other){
        this.teaCount += other.teaCount;
        this.coffeeCount += other.coffeeCount;
    }

    public boolean isEmpty(){
        return teaCount == 0 && coffeeCount == 0;
    }

    public boolean isValid() { return teaCount > 0 || coffeeCount > 0;  }

    @Override
    public String toString(){
        return " ( " + this.coffeeCount + " X coffee, " + this.teaCount + " X tea )";
    }
}
