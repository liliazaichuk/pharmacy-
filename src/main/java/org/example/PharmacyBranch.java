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
    public PharmacyBranch findNearestPharmacy(double userX, double userY, List<PharmacyBranch> pharmacies) {
        PharmacyBranch nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (PharmacyBranch pharmacy : pharmacies) {
            double distance = DistanceCalculator.calculateDistance(
                    userX, userY, pharmacy.getxCoordinate(), pharmacy.getyCoordinate()
            );
            if (distance < minDistance) {
                minDistance = distance;
                nearest = pharmacy;
            }
        }
        return nearest;
    }
    public PharmacyBranch findNearestPharmacyWithMedicines(double userX, double userY, Map<String, Integer> cartItems, List<PharmacyBranch> pharmacies) {
        for (PharmacyBranch pharmacy : pharmacies) {
            boolean hasAllMedicines = true;
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                if (!pharmacy.hasMedicine(entry.getKey(), entry.getValue())) {
                    hasAllMedicines = false;
                    break;
                }
            }
            if (hasAllMedicines) {
                return pharmacy;
            }
        }
        return null;
    }

    public boolean hasMedicine(String medicine, int quantity) {
        return inventory.getOrDefault(medicine, 0) >= quantity;
    }

    public void updateInventory(String medicine, int quantity) {
        inventory.put(medicine, inventory.get(medicine) - quantity);
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
