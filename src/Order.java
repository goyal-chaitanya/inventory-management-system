import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final int orderId;
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private String message;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.message = "Waiting for fulfillment.";
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addItem(String sku, int quantity) {
        items.add(new OrderItem(sku, quantity));
    }

    public void markFulfilled(String message) {
        this.status = OrderStatus.FULFILLED;
        this.message = message;
    }

    public void markRejected(String message) {
        this.status = OrderStatus.REJECTED;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Order #" + orderId + " | customer: " + customerName
                + " | status: " + status + " | " + message;
    }
}
