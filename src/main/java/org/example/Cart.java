package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cart {
    private Map<String, Integer> cartItems;
    private static final Logger logger = LogManager.getLogger(PharmacyBranch.class);
    public Cart() {
        this.cartItems = new HashMap<>();
        logger.info("Кошик створено.");
    }
    public void addToCart(String medicine, int quantity, List<PharmacyBranch> branches) {
        String normalizedMedicine = medicine.toLowerCase(); // Приводимо назву до нижнього регістру

        // Перевірка, чи існує препарат взагалі в аптеках
        boolean medicineExists = branches.stream()
                .anyMatch(branch -> branch.getInventory().keySet()
                        .stream()
                        .anyMatch(key -> key.toLowerCase().equals(normalizedMedicine)));

        if (!medicineExists) {
            logger.warn("Medicine with this name was not found: {}", medicine);
            System.out.println("Medicine with this name was not found");
            return;
        }

        // Перевірка наявності достатньої кількості препарату
        Optional<PharmacyBranch> pharmacyWithStock = branches.stream()
                .filter(branch -> branch.getInventory().entrySet()
                        .stream()
                        .anyMatch(entry -> entry.getKey().toLowerCase().equals(normalizedMedicine) && entry.getValue() >= quantity))
                .findFirst();

        if (pharmacyWithStock.isEmpty()) {
            logger.warn("Not enough this medicine in stock: {}", medicine);
            System.out.println("Not enough this medicine in stock");
            return;
        }

        // Якщо препарат існує і його достатньо, додаємо до кошика
        cartItems.put(medicine.toLowerCase(), cartItems.getOrDefault(medicine, 0) + quantity);
        logger.info("Додано до кошика: {} (кількість: {}).", medicine.toLowerCase(), quantity);
    }

    public void removeFromCart(String medicine) {
        if (cartItems.remove(medicine) != null) {
            logger.info("Видалено з кошика: {}.", medicine);
        } else {
            logger.warn("Спроба видалити ліки, яких немає в кошику: {}.", medicine);
        }
    }

    public void clearCart() {

        cartItems.clear();
        logger.info("Кошик очищено.");
    }

    public Map<String, Integer> getCartItems() {
        logger.debug("Отримано вміст кошика: {}.", cartItems);
        return cartItems;
    }
}
