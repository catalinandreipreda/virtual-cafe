package Helpers;

public class Cafe {

    public static void parseOrder(String order) {
        String[] words = order.split("\\s+");
        int totalCoffees = 0;
        int totalTeas = 0;

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (word.contains("coffee")) {
                totalCoffees++;
            } else if (word.contains("tea")) {
                totalTeas++;
            }
        }

        System.out.println("Total coffees: " + totalCoffees);
        System.out.println("Total teas: " + totalTeas);
    }
}
