//Just testing by using chatGPT, not functioning (Chun Jia)
import java.util.*;

public class Order {
    private String orderID;
    private Date orderDate;
    private double totalPrice;

    private static List<Customer> customers = new ArrayList<>();
    private static List<Book> orderItems = new ArrayList<>();

    // Constructors
    public Order() {
    }

    public Order(String orderID, Date orderDate, double totalPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(List<Customer> customers) {
        Order.customers = customers;
    }

    public static List<Book> getOrderItems() {
        return orderItems;
    }

    public static void setOrderItems(List<Book> orderItems) {
        Order.orderItems = orderItems;
    }

    // Method to add a book to the order
    public void addBook(Book book) {
        orderItems.add(book);
        totalPrice += book.getPrice();
    } 

    // Method to remove a book from the order
    public void removeBook(Book book) {
        if (orderItems.remove(book)) {
            totalPrice -= book.getPrice();
        }
    }

    // Method to display order details
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Ordered Books:");
        for (Book book : orderItems) {
            System.out.println(" - " + book.getTitle() + " by " + book.getAuthor() + ", $" + book.getPrice());
        }
    }

    // Method to find a customer by ID (assuming customers are already populated)
    public static Customer findCustomerByID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    // Method to add a customer to the list
    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Method to remove a customer from the list
    public static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    // Example main method to test the class
    public static void main(String[] args) {
        // Example usage
        Order order = new Order("ORD001", new Date(), 0.0);

        // Create and add books
        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setAuthor("Author A");
        book1.setPrice(19.99);
        book1.setISBN("ISBN001");

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setAuthor("Author B");
        book2.setPrice(29.99);
        book2.setISBN("ISBN002");

        // Add books to order
        order.addBook(book1);
        order.addBook(book2);

        // Display order details
        order.displayOrderDetails();

        // Example usage of customers
        Customer customer = new Customer();
        customer.setCustomerID("CUST001");
        customer.setName("John Doe");
        customer.setAddress("123 Elm St");
        customer.setContactNo("555-1234");

        addCustomer(customer);
        System.out.println("Customer added: " + findCustomerByID("CUST001").getName());

        // Remove a book from the order
        order.removeBook(book1);
        order.displayOrderDetails();
    }
}
