package org.example;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PharmacyService pharmacyService = new PharmacyService();
        Cart cart = new Cart();

        List<PharmacyBranch> branches = pharmacyService.loadPharmaciesFromFile("pharmacies.txt");

        if (branches.isEmpty()) {
            System.out.println("Failed to load pharmacy information. Check the file.");
            return;
        }

        while (true) {
            System.out.println("=== Online Pharmacy ===");
            System.out.println("1. Find the nearest pharmacy");
            System.out.println("2. Place an order");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter your coordinetes (X Y): ");
                    double userX = scanner.nextDouble();
                    double userY = scanner.nextDouble();
                    PharmacyBranch nearest = pharmacyService.findNearestPharmacy(userX, userY, branches);
                    if (nearest != null) {
                        System.out.println("Nearest pharmacy: " + nearest.getName());
                    } else {
                        System.out.println("Pharmacy not found.");
                    }
                    break;

                case 2:
                    handleOrder(scanner, cart, pharmacyService, branches);
                    break;

                case 3:
                    System.out.println("Thank you for using Pharmacy!");
                    return;

                default:
                    System.out.println("Uncorrect choice. Try again.");
            }
        }
    }

    private static void handleOrder(Scanner scanner, Cart cart, PharmacyService pharmacyService, List<PharmacyBranch> branches) {
        while (true) {
            System.out.println("=== Place an order ===");
            System.out.println("1. Add medicine to cart");
            System.out.println("2. View cart");
            System.out.println("3. Clean the cart");
            System.out.println("4. Delete smth from cart");
            System.out.println("5. Confirm order");
            System.out.println("6. Back");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the name of medicine ");
                    String medicine = scanner.next();
                    System.out.print("Enter the quantity of medicine ");
                    int quantity = scanner.nextInt();
                    cart.addToCart(medicine, quantity, branches);
                    break;

                case 2:
                    System.out.println("Cart: " + cart.getCartItems());
                    break;

                case 3:
                    cart.clearCart();
                    break;
                case 4:
                    System.out.print("Enter the name of medicine you want to delete: ");
                    medicine = scanner.next();
                    cart.removeFromCart(medicine);
                    break;

                case 5:
                    System.out.print("Enter your coordinates (X Y): ");
                    double userX = scanner.nextDouble();
                    double userY = scanner.nextDouble();
                    // Знайти найближчу аптеку, яка має всі необхідні ліки
                    PharmacyBranch nearest = pharmacyService.findNearestPharmacyWithMedicines(userX, userY, branches, cart.getCartItems());

                    if (nearest != null) {
                        System.out.println("Order can be picked up at the pharmacy: " + nearest.getName());

                        pharmacyService.updateFileWithOrder(cart, nearest);

                        cart.clearCart();

                        System.out.println("Order successfully placed and inventory updated.");
                    } else {
                        System.out.println("The order cannot be executed. Either medicines are not available or insufficient stock.");
                    }
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Uncorrect choice. Try again.");
            }
        }
    }
}