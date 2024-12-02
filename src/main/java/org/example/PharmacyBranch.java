package org.example;

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

    public String getName() {
        return name;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
