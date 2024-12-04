package org.example;

import java.util.List;
import java.util.Map;

public class PharmacyBranch {
    private String name;
    private double xCoordinate;
    private double yCoordinate;
    private Map<String, Integer> inventory;

    public PharmacyBranch(String name, double xCoordinate, double yCoordinate, Map<String, Integer> inventory) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.inventory = inventory;
    }

    public boolean hasMedicine(String medicine, int quantity) {
        return inventory.getOrDefault(medicine, 0) >= quantity;
    }

    public String getName() {

        return name;
    }

    public double getXCoordinate() {

        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public Map<String, Integer> getInventory() {

        return inventory;
    }
}
