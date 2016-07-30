package greg.phoenix.com.simpleshoppingcartgregtidd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataProvider {

    public static List<Product> productList = new ArrayList<>();
    public static Map<String, Product> productMap = new HashMap<>();

    static {

        addProduct("milk",
                "2% Reduced Fat Milk",
                "Packed full of Calcium will build strong bones",
                2.99);

        addProduct("Bread",
                "Whole Wheat Bread",
                "Great for making sandwhiches and doubles as a hotdog/hamburger bun",4.25);

        addProduct("chips",
                "Potato Chips",
                "Junk food at it's finest delicious salty chips",2.49);

        addProduct("cookies",
                "Chocolate Chip Cookies",
                "Can't forget these gooey morsels of sugar packed goodness", 3.99);

        addProduct("dogfood",
                "Bits and Kibble",
                "Can't say it tastes good but we don't have to", 11.99);

        addProduct("eggs",
                "Unscrambled Eggs",
                "Get these eggs before they are cooked priced to sell",.99);

        addProduct("hamburger",
                "Ground Beef",
                "Nothing says summer like hamburgers on the grill",3.69);

        addProduct("hotdog",
                "Ballpark Franks",
                "Kick off baseball season right with some delicious ball park franks",
                2.99);

        addProduct("yogurt",
                "Flavored Yogurt",
                "Creamy Greek yogurt for when you need to get back to your new years resolution",85);

    }

    private static void addProduct(String itemId, String name,
                                   String description, double price) {
        Product item = new Product(itemId, name, description, price);
        productList.add(item);
        productMap.put(itemId, item);
    }

    public static List<String> getProductNames() {
        List<String> list = new ArrayList<>();
        for (Product product : productList) {
            list.add(product.getName());
        }
        return list;
    }

    public static List<Product> getFilteredList(String searchString) {

        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductId().contains(searchString)) {
                filteredList.add(product);
            }
        }

        return filteredList;

    }

}
