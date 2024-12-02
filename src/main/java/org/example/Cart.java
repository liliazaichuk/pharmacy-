package org.example;
import java.util.HashMap;
import java.util.Map;
public class Cart {
    private Map<String, Integer> cartItems = new HashMap<>();

    public void addToCart(String medicine, int quantity) {
        cartItems.put(medicine, cartItems.getOrDefault(medicine, 0) + quantity);
    }

    public void removeFromCart(String medicine) {
        cartItems.remove(medicine);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Map<String, Integer> getCartItems() {
        return cartItems;
    }
}
