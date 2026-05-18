# Order and Inventory Management System

A Java console project that simulates inventory lookup, order placement, order fulfillment, and demand-based restocking for a small supply chain workflow.

## Features

- Add and manage products using object-oriented design.
- Search products by SKU or name.
- Place customer orders with multiple items.
- Process pending orders using available inventory.
- Reject orders when stock is unavailable.
- Track order history and fulfillment status.
- Dynamically restock low inventory based on reorder level and demand.

## Data Structures Used

- `HashMap<String, Product>` for fast SKU lookup.
- `TreeMap<String, Product>` for name-based lookup.
- `ArrayDeque<Order>` as a queue for pending orders.
- `ArrayList<Order>` for order history.
- `PriorityQueue<Product>` as a heap for urgent restocking decisions.

## OOP Concepts Used

- Encapsulation: product stock and order status are modified through methods.
- Abstraction: `Inventory` hides stock and restocking logic.
- Modularity: products, orders, inventory, and order processing are separate classes.
- Composition: orders contain order items, and order management uses inventory.

## Build and Run

```bash
make run
```

Or manually:

```bash
javac -d out src/*.java
java -cp out App
```

To run a quick automated demo:

```bash
make demo
```

## Project Flow

1. The system starts with sample products.
2. The user places orders using product SKUs.
3. Pending orders are processed in FIFO order.
4. Stock is reduced for fulfilled orders.
5. Low-stock products enter a priority queue for restocking.
6. The restock amount is adjusted using past demand.

## Resume Description

Order and Inventory Management System - Java and Data Structures

- Built a console-based inventory and order management system simulating real-world supply chain workflows like order placement and fulfillment.
- Applied object-oriented design principles including encapsulation, modularity, and abstraction to structure product, inventory, and order components.
- Implemented dynamic restocking logic based on demand patterns to reduce stockouts.
- Used `HashMap`, `TreeMap`, `Queue`, `ArrayList`, and `PriorityQueue` for efficient lookup, processing, and restocking decisions.
