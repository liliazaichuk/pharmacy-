package org.example;


import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PharmacyBranch {
    private static final Logger logger = LogManager.getLogger(PharmacyBranch.class);
    @Getter
    private String name;
    private double xCoordinate;
    private double yCoordinate;
    @Getter
    private Map<String, Integer> inventory;

    public PharmacyBranch(String name, double xCoordinate, double yCoordinate, Map<String, Integer> inventory) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.inventory = inventory;
        logger.info("Створено аптеку: {}, координати: ({}, {})", name, xCoordinate, yCoordinate);
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public boolean hasMedicine(String medicine, int quantity) {
        boolean result = inventory.containsKey(medicine) && inventory.get(medicine) >= quantity;
        logger.debug("Перевірка наявності ліків {} (кількість: {}): {}", medicine, quantity, result);
        return result;
    }
}
