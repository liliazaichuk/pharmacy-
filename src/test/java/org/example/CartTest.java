package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    private Cart cart;
    private List<PharmacyBranch> branches;

    @BeforeEach
    public void setUp() {
        cart = new Cart();

        Map<String, Integer> inventory1 = new HashMap<>();
        inventory1.put("aspirin", 10);
        inventory1.put("paracetamol", 5);

        Map<String, Integer> inventory2 = new HashMap<>();
        inventory2.put("ibuprofen", 20);
        inventory2.put("paracetamol", 15);

        PharmacyBranch branch1 = new PharmacyBranch("Pharmacy A", 0, 0, inventory1);
        PharmacyBranch branch2 = new PharmacyBranch("Pharmacy B", 1, 1, inventory2);

        branches = List.of(branch1, branch2);
    }

    @Test
    public void testAddToCart_Success() {
        cart.addToCart("aspirin", 5, branches);
        assertEquals(5, cart.getCartItems().get("aspirin"));
    }

    @Test
    public void testAddToCart_InsufficientStock() {
        cart.addToCart("aspirin", 15, branches);
        assertNull(cart.getCartItems().get("aspirin"));
    }

    @Test
    public void testAddToCart_MedicineNotFound() {
        cart.addToCart("unknown", 1, branches);
        assertNull(cart.getCartItems().get("unknown"));
    }

    @Test
    public void testRemoveFromCart_ItemExists() {
        cart.addToCart("aspirin", 5, branches);
        cart.removeFromCart("aspirin");
        assertFalse(cart.getCartItems().containsKey("aspirin"));
    }

    @Test
    public void testRemoveFromCart_ItemDoesNotExist() {
        cart.removeFromCart("nonexistent");
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void testClearCart() {
        cart.addToCart("aspirin", 5, branches);
        cart.clearCart();
        assertTrue(cart.getCartItems().isEmpty());
    }
}