package Helpers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Cafe {

    private static final int MAX_BREW_CAPACITY = 2;
    private static final Map<String, Order> orders = new HashMap<>();
    private Logger logger;
//ReentrantLock
    private BlockingQueue<Drink> teaWaitingArea = new LinkedBlockingQueue<>();
    private BlockingQueue<Drink> coffeeWaitingArea = new LinkedBlockingQueue<>();
    private BlockingQueue<Drink> teaBrewingArea = new LinkedBlockingQueue<>(MAX_BREW_CAPACITY);
    private BlockingQueue<Drink> coffeeBrewingArea = new LinkedBlockingQueue<>(MAX_BREW_CAPACITY);
    private BlockingQueue<Drink> trayArea = new LinkedBlockingQueue<>();

    public Cafe(Logger logger) {
        this.logger = logger;
        new Thread(() -> brewDrink(teaWaitingArea, teaBrewingArea, DrinkType.TEA.secondsToBrew)).start();
        new Thread(() -> brewDrink(coffeeWaitingArea, coffeeBrewingArea, DrinkType.COFFEE.secondsToBrew)).start();
    }

    private void brewDrink(BlockingQueue<Drink> waitingArea, BlockingQueue<Drink> brewingArea, int brewTime) {
        while (true) {
            try {
                Drink drinkToBrew = waitingArea.take();
                brewingArea.put(drinkToBrew);
                logger.log("Start Brewing: " + drinkToBrew);
                TimeUnit.SECONDS.sleep(brewTime);
                brewingArea.remove(drinkToBrew);
                logger.log("Finished Brewing: " + drinkToBrew);
                trayArea.put(drinkToBrew);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void addDrinksToWaitingArea(Order order, String customerName) throws InterruptedException {
        for (int i = 0; i < order.teaCount; i++) {
            teaWaitingArea.put(new Drink(DrinkType.TEA, customerName));
        }
        for (int i = 0; i < order.coffeeCount; i++) {
            coffeeWaitingArea.put(new Drink(DrinkType.COFFEE, customerName));
        }
    }


    public String getOrderStatus(String customerName){
        Order order = orders.get(customerName);
        return order != null ? "Order for " + customerName + ": " + order
                             : "No order currently";
    }

    public void removeOrder(String customerName){
        orders.remove(customerName);
    }

    public void handleOrder(String orderMessage, String customerName, PrintWriter writer){
        Order thisCustomersOrder = orders.computeIfAbsent(customerName, k -> new Order());
        try {
            var newOrder = Cafe.parseOrderMessage(orderMessage);

            if(newOrder.isValid() && !newOrder.isEmpty()){
                thisCustomersOrder.append(newOrder);
                orders.put(customerName, thisCustomersOrder);
                addDrinksToWaitingArea(newOrder, customerName);
                writer.println("Order received from " + customerName + thisCustomersOrder);

            } else{
                writer.println("We couldn't place your order, please provide valid quantities for tea or coffee");
            }
        } catch (Exception e){
            writer.println("We couldn't process your order, please make sure it is correctly formatted and try again" + "\n Example: " + " order 1 tea and 3 coffees");
        }
    }



    private static Order parseOrderMessage(String orderMessage) {
        String[] words = orderMessage.split("\\s+");
        int totalCoffees = 0;
        int totalTeas = 0;

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (word.contains("coffee")) {
                String previousToken = words[i - 1];
                var count = Integer.parseInt(previousToken);
                totalCoffees += count;
            } else if (word.contains("tea")) {
                String previousToken = words[i - 1];
                var count = Integer.parseInt(previousToken);
                totalTeas += count;
            }
        }

        return new Order(totalTeas, totalCoffees);
    }
}
