import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Inventory {
    private final Map<String, Product> productsBySku;
    private final TreeMap<String, Product> productsByName;
    private final PriorityQueue<Product> restockQueue;

    public Inventory() {
        this.productsBySku = new HashMap<>();
        this.productsByName = new TreeMap<>();
        this.restockQueue = new PriorityQueue<>(
                Comparator.comparingInt(this::restockUrgencyScore).reversed()
                        .thenComparing(Product::getSku)
        );
    }

    public void addProduct(Product product) {
        productsBySku.put(product.getSku(), product);
        productsByName.put(product.getName().toLowerCase(), product);
        refreshRestockQueue();
    }

    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(productsBySku.get(sku));
    }

    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(productsByName.get(name.toLowerCase()));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productsBySku.values());
    }

    public boolean canFulfill(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = productsBySku.get(item.getSku());
            if (product == null || !product.hasEnoughStock(item.getQuantity())) {
                return false;
            }
        }
        return true;
    }

    public double fulfill(Order order) {
        if (!canFulfill(order)) {
            throw new IllegalStateException("Order cannot be fulfilled due to missing or insufficient stock.");
        }

        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            Product product = productsBySku.get(item.getSku());
            product.reduceStock(item.getQuantity());
            total += product.getPrice() * item.getQuantity();
        }

        refreshRestockQueue();
        return total;
    }

    public List<String> restockLowInventory() {
        refreshRestockQueue();
        List<String> restockEvents = new ArrayList<>();

        while (!restockQueue.isEmpty()) {
            Product product = restockQueue.poll();
            if (product.getStock() > product.getReorderLevel()) {
                continue;
            }

            int restockAmount = calculateRestockAmount(product);
            product.addStock(restockAmount);
            restockEvents.add(product.getSku() + " restocked by " + restockAmount
                    + " units. New stock: " + product.getStock());
        }

        refreshRestockQueue();
        return restockEvents;

    }

    private int calculateRestockAmount(Product product) {
        int demandBuffer = Math.max(10, product.getTotalDemand() / 2);
        return product.getReorderLevel() * 2 + demandBuffer - product.getStock();
    }

    private int restockUrgencyScore(Product product) {
        int shortage = Math.max(0, product.getReorderLevel() - product.getStock());
            return shortage * 10 + product.getTotalDemand();
        }

    private void refreshRestockQueue() {
        restockQueue.clear();
        for (Product product : productsBySku.values()) {
            if (product.getStock() <= product.getReorderLevel()) {
                restockQueue.offer(product);
            }
        }
    }
}
