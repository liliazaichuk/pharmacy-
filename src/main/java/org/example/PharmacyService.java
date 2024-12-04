package org.example;
import java.io.*;
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
    public void updateFileWithOrder(Cart cart, PharmacyBranch pharmacy) {
        // Створюємо копію оригінального файлу
        File inputFile = new File("pharmacies.txt");
        File tempFile = new File("pharmacies_temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            // Проходимо через кожен рядок файлу
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String branchName = parts[0];

                // Якщо це аптека, яка обробляє замовлення
                if (branchName.equalsIgnoreCase(pharmacy.getName())) {
                    Map<String, Integer> inventory = pharmacy.getInventory();

                    // Зменшуємо кількість ліків на основі замовлення
                    for (Map.Entry<String, Integer> cartItem : cart.getCartItems().entrySet()) {
                        String medicine = cartItem.getKey().toLowerCase();
                        int quantityToRemove = cartItem.getValue();

                        if (inventory.containsKey(medicine)) {
                            int newQuantity = inventory.get(medicine) - quantityToRemove;
                            inventory.put(medicine, newQuantity);
                        }
                    }

                    // Формуємо оновлений рядок для цієї аптеки
                    StringBuilder updatedInventory = new StringBuilder();
                    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                        updatedInventory.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                    }

                    // Видаляємо зайву кому в кінці
                    if (updatedInventory.length() > 0) {
                        updatedInventory.setLength(updatedInventory.length() - 1);
                    }

                    // Записуємо оновлену інформацію про аптеку у файл
                    writer.write(branchName + ";" + parts[1] + ";" + updatedInventory.toString());
                    writer.newLine();
                } else {
                    // Якщо це не потрібна аптека, просто копіюємо рядок
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
        }

        // Замінюємо старий файл новим
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
            }
        } else {
        }

    }
}