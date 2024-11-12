package org.example;
import java.util.HashMap;
import java.util.Map;

public class PharmacyService {

    private Map<String, Integer> inventory;
    private Map<String, Integer> cart;

    public PharmacyService() {
        inventory = new HashMap<>();
        cart = new HashMap<>();

        inventory.put("Paracetamol", 20);
        inventory.put("Aspirin", 15);
        inventory.put("Ibuprofen", 10);
    }

    public boolean checkAvailability(String medicine, int quantity) {
        return inventory.getOrDefault(medicine, 0) >= quantity;
    }

    public String addToCart(String medicine, int quantity) {
        if (checkAvailability(medicine, quantity)) {
            cart.put(medicine, cart.getOrDefault(medicine, 0) + quantity);
            inventory.put(medicine, inventory.get(medicine) - quantity);
            return quantity + " units of " + medicine + " have been added to the cart.";
        } else {
            return "Unfortunately, there is insufficient stock of " + medicine + ".";
        }
    }

    public String placeOrder() {
        if (cart.isEmpty()) {
            return "Your cart is empty. Add medicines to place an order.";
        } else {
            cart.clear();
            return "Your order has been successfully placed!";
        }
    }
}