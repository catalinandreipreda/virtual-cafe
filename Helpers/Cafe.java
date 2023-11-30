package Helpers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Cafe {

    private static final Map<String, Order> orders = new HashMap<>();


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

            if(newOrder.isValid()){
                thisCustomersOrder.append(newOrder);
                orders.put(customerName, thisCustomersOrder);
                writer.println("Order received from " + customerName + thisCustomersOrder);
            }else{
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
