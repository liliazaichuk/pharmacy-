package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PharmacyService {

    private Map<String, Integer> inventory;
    private Map<String, Integer> cart;

    public List<PharmacyBranch> loadPharmaciesFromFile(String fileName) {
        List<PharmacyBranch> branches = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                String name = parts[0];
                double xCoordinate = Double.parseDouble(parts[1]);
                double yCoordinate = Double.parseDouble(parts[2]);

                Map<String, Integer> inventory = new HashMap<>();
                String[] items = parts[3].split(",");
                for (String item : items) {
                    String[] medicineData = item.split("=");
                    String medicineName = medicineData[0].toLowerCase();
                    int quantity = Integer.parseInt(medicineData[1]);
                    inventory.put(medicineName, quantity);
                }

                // Додавання аптеки до списку
                branches.add(new PharmacyBranch(name, xCoordinate, yCoordinate, inventory));

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return branches;
    }
    public PharmacyBranch findNearestPharmacy(double userX, double userY, List<PharmacyBranch> branches) {
        PharmacyBranch nearest = null;
        double shortestDistance = Double.MAX_VALUE;

        for (PharmacyBranch branch : branches) {
            double distance = DistanceCalculator.calculateDistance(userX, userY, branch.getXCoordinate(), branch.getYCoordinate());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearest = branch;
            }
        }
        return nearest;
    }

    public PharmacyBranch findNearestPharmacyWithMedicines(double userX, double userY, List<PharmacyBranch> branches, Map<String, Integer> cartItems) {
        PharmacyBranch nearest = null;
        double shortestDistance = Double.MAX_VALUE;

        for (PharmacyBranch branch : branches) {
            boolean hasAllMedicines = cartItems.entrySet().stream()
                    .allMatch(entry -> branch.hasMedicine(entry.getKey(), entry.getValue()));

            if (hasAllMedicines) {
                double distance = DistanceCalculator.calculateDistance(userX, userY, branch.getXCoordinate(), branch.getYCoordinate());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearest = branch;
                }
            }
        }
        return nearest;
    }
}