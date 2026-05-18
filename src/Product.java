public class Product {
    private final String sku;
    private final String name;
    private final double price;
    private final int reorderLevel;
    private int stock;
    private int totalDemand;

    public Product(String sku, String name, double price, int stock, int reorderLevel) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reorderLevel = reorderLevel;
        this.totalDemand = 0;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public int getTotalDemand() {
        return totalDemand;
    }

    public boolean hasEnoughStock(int quantity) {
        return stock >= quantity;
    }

    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (!hasEnoughStock(quantity)) {
            throw new IllegalArgumentException("Insufficient stock for " + sku + ".");
        }
        stock -= quantity;
        totalDemand += quantity;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be positive.");
        }
        stock += quantity;
    }

    @Override
    public String toString() {
        return sku + " | " + name + " | price: $" + String.format("%.2f", price)
                + " | stock: " + stock
                + " | reorder level: " + reorderLevel
                + " | demand: " + totalDemand;
    }
}
