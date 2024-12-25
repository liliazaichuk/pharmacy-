package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PharmacyService pharmacyService = new PharmacyService();
        Cart cart = new Cart();
        List<PharmacyBranch> branches = pharmacyService.loadPharmaciesFromFile("pharmacies.txt");

        if (branches.isEmpty()) {
            System.out.println("Failed to load pharmacy information. Check the file.");
            return;
        }

        CompositeNode mainMenu = new CompositeNode("Online Pharmacy", true);

        // Команда: Пошук найближчої аптеки
        mainMenu.addChild(new LeafNode("Find nearest pharmacy", () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your coordinates (X Y): ");
            double userX = scanner.nextDouble();
            double userY = scanner.nextDouble();
            PharmacyBranch nearest = pharmacyService.findNearestPharmacy(userX, userY, branches);
            if (nearest != null) {
                System.out.println("Nearest pharmacy: " + nearest.getName());
            } else {
                System.out.println("Pharmacy not found.");
            }
        }));

        // Підменю для замовлення
        CompositeNode orderMenu = new CompositeNode("Place an order", false);

        orderMenu.addChild(new LeafNode("Add medicine to cart", () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of medicine: ");
            String medicine = scanner.next();
            System.out.print("Enter the quantity of medicine: ");
            int quantity = scanner.nextInt();
            cart.addToCart(medicine, quantity, branches);
        }));

        orderMenu.addChild(new LeafNode("View cart", () -> {
            System.out.println("Cart: " + cart.getCartItems());
        }));

        orderMenu.addChild(new LeafNode("Clear the cart", cart::clearCart));

        orderMenu.addChild(new LeafNode("Delete medicine from cart", () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of medicine to delete: ");
            String medicine = scanner.next();
            cart.removeFromCart(medicine);
        }));

        orderMenu.addChild(new LeafNode("Confirm order", () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your coordinates (X Y): ");
            double userX = scanner.nextDouble();
            double userY = scanner.nextDouble();
            PharmacyBranch nearest = pharmacyService.findNearestPharmacyWithMedicines(userX, userY, branches, cart.getCartItems());

            if (nearest != null) {
                System.out.println("Order can be picked up at the pharmacy: " + nearest.getName());
                pharmacyService.updateFileWithOrder(cart, nearest);
                cart.clearCart();
                System.out.println("Order successfully placed and inventory updated.");
            } else {
                System.out.println("The order cannot be executed. Either medicines are not available or insufficient stock.");
            }
        }));

        mainMenu.addChild(orderMenu);

        // Команда: Вихід
        mainMenu.addChild(new LeafNode("Exit", () -> System.exit(0)));

        // Запуск головного меню
        while (true) {
            mainMenu.execute();
        }
    }
}