package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompositeNode implements Node {
    private String name;
    private List<Node> children;
    private boolean isMainMenu;

    public CompositeNode(String name, boolean isMainMenu) {
        this.name = name;
        this.isMainMenu = isMainMenu;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Виведення заголовка меню та його пунктів
            System.out.println("=== " + name + " ===");
            for (int i = 0; i < children.size(); i++) {
                System.out.println((i + 1) + ". " + children.get(i).getName());
            }
            if (!isMainMenu) {
                System.out.println((children.size() + 1) + ". Back");
            }

            // Обробка вибору користувача
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            if (choice == children.size() + 1) {
                // Якщо обрано "Back", виходимо з меню
                return;
            } else if (choice >= 1 && choice <= children.size()) {
                // Виконати вибраний пункт меню
                children.get(choice - 1).execute();
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }
}