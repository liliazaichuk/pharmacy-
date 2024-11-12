package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PharmacyServiceTest {
    private PharmacyService pharmacyService;

    @BeforeEach
    void setUp() {
        pharmacyService = new PharmacyService();
    }

    @Test
    void testCheckAvailability() {
        assertTrue(pharmacyService.checkAvailability("Paracetamol", 5));
        assertFalse(pharmacyService.checkAvailability("Ibuprofen", 20));
    }

    @Test
    void testAddToCart() {
        String result = pharmacyService.addToCart("Paracetamol", 5);
        assertEquals("5 units of Paracetamol have been added to the cart.", result);

        String resultNotAvailable = pharmacyService.addToCart("Aspirin", 20);
        assertEquals("Unfortunately, there is insufficient stock of Aspirin.", resultNotAvailable);
    }

    @Test
    void testPlaceOrder() {
        pharmacyService.addToCart("Paracetamol", 5);
        String result = pharmacyService.placeOrder();
        assertEquals("Your order has been successfully placed!", result);

        String resultEmptyCart = pharmacyService.placeOrder();
        assertEquals("Your cart is empty. Add medicines to place an order.", resultEmptyCart);
    }
}