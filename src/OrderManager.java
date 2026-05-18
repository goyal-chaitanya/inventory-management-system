import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class OrderManager {
    private final Inventory inventory;
    private final Queue<Order> pendingOrders;
    private final List<Order> orderHistory;
    private int nextOrderId;

    public OrderManager(Inventory inventory) {
        this.inventory = inventory;
        this.pendingOrders = new ArrayDeque<>();
        this.orderHistory = new ArrayList<>();
        this.nextOrderId = 1001;
    }

    public Order createOrder(String customerName) {
        Order order = new Order(nextOrderId++, customerName);
        pendingOrders.offer(order);
        orderHistory.add(order);
        return order;
    }

    public void processPendingOrders() {
        int ordersToProcess = pendingOrders.size();

        for (int i = 0; i < ordersToProcess; i++) {
            Order order = pendingOrders.poll();
            if (order == null) {
                continue;
            }

            if (inventory.canFulfill(order)) {
                double total = inventory.fulfill(order);
                order.markFulfilled("Fulfilled successfully. Total: $" + String.format("%.2f", total));
            } else {
                order.markRejected("Rejected because one or more products are unavailable.");
            }
        }
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }
}
