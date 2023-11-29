package Helpers;

public class Cafe {

    public static Order parseOrder(String order) {
        String[] words = order.split("\\s+");
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
