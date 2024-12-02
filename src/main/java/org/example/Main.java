package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PharmacyService service = new PharmacyService();
        Cart cart = new Cart();

        while (true) {
            System.out.println("1. Find the nearest pharmacy");
            System.out.println("2. Place an order");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter your coordinates (x y): ");
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    // Example: service.findNearestPharmacy
                    break;

                case 2:
                    // TODO: Implement cart interaction and order placement
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}


