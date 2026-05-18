import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    private final Inventory inventory;
    private final OrderManager orderManager;
    private final Scanner scanner;

    public App() {
        this.inventory = new Inventory();
        this.orderManager = new OrderManager(inventory);
        this.scanner = new Scanner(System.in);
        seedInventory();
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    showInventory();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 4:
                    processOrders();
                    break;
                case 5:
                    restockInventory();
                    break;
                case 6:
                    showOrderHistory();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting system. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Order and Inventory Management System ===");
        System.out.println("1. View inventory");
        System.out.println("2. Search product");
        System.out.println("3. Place order");
        System.out.println("4. Process pending orders");
        System.out.println("5. Dynamic restock low inventory");
        System.out.println("6. View order history");
        System.out.println("0. Exit");
    }

    private void showInventory() {
        System.out.println("\nCurrent Inventory");
        for (Product product : inventory.getAllProducts()) {
            System.out.println(product);
        }
    }

    private void searchProduct() {
        System.out.print("Enter SKU or product name: ");
        String query = scanner.nextLine().trim();

        Optional<Product> bySku = inventory.findBySku(query);
        Optional<Product> byName = inventory.findByName(query);

        if (bySku.isPresent()) {
            System.out.println(bySku.get());
        } else if (byName.isPresent()) {
            System.out.println(byName.get());
        } else {
            System.out.println("Product not found.");
        }
    }

    private void placeOrder() {
        System.out.print("Customer name: ");
        String customerName = scanner.nextLine().trim();
        Order order = orderManager.createOrder(customerName);

        while (true) {
            System.out.print("Enter SKU or DONE: ");
            String sku = scanner.nextLine().trim();
            if (sku.equalsIgnoreCase("DONE")) {
                break;
            }

            int quantity = readInt("Quantity: ");
            try {
                order.addItem(sku, quantity);
            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
            }
        }

        System.out.println("Created " + order);
    }

    private void processOrders() {
        orderManager.processPendingOrders();
        System.out.println("Pending orders processed.");
    }

    private void restockInventory() {
        List<String> events = inventory.restockLowInventory();
        if (events.isEmpty()) {
            System.out.println("No products need restocking.");
            return;
        }

        System.out.println("Restock Events");
        for (String event : events) {
            System.out.println(event);
        }
    }

    private void showOrderHistory() {
        System.out.println("\nOrder History");
        for (Order order : orderManager.getOrderHistory()) {
            System.out.println(order);
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException error) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void seedInventory() {
        inventory.addProduct(new Product("P100", "Laptop", 799.99, 8, 5));
        inventory.addProduct(new Product("P101", "Keyboard", 49.99, 20, 8));
        inventory.addProduct(new Product("P102", "Mouse", 24.99, 15, 10));
        inventory.addProduct(new Product("P103", "Monitor", 179.99, 7, 4));
        inventory.addProduct(new Product("P104", "USB-C-Cable", 9.99, 30, 12));
    }
}
