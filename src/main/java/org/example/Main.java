package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello! It is a new project!");
        System.out.println("Welcome to the Internet Pharmacy!");

        Scanner scanner = new Scanner(System.in);

        PharmacyService pharmacyService = new PharmacyService();

        while (true) {
            System.out.println("\nEnter a command: check (check availability), add (add to cart), order (place order), or exit (exit)");

            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "check":
                    System.out.print("Enter the name of the medicine: ");
                    String medicineToCheck = scanner.nextLine().trim();
                    System.out.print("Enter the quantity: ");
                    int quantityToCheck = scanner.nextInt();
                    scanner.nextLine();

                    boolean isAvailable = pharmacyService.checkAvailability(medicineToCheck, quantityToCheck);
                    System.out.println(isAvailable ? "The medicine is available." : "Insufficient stock.");
                    break;

                case "add":
                    System.out.print("Enter the name of the medicine: ");
                    String medicineToAdd = scanner.nextLine().trim();
                    System.out.print("Enter the quantity: ");
                    int quantityToAdd = scanner.nextInt();
                    scanner.nextLine();

                    String addToCartResult = pharmacyService.addToCart(medicineToAdd, quantityToAdd);
                    System.out.println(addToCartResult);
                    break;

                case "order":
                    String orderResult = pharmacyService.placeOrder();
                    System.out.println(orderResult);
                    break;

                case "exit":
                    System.out.println("Thank you for using the Internet Pharmacy!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }
}


