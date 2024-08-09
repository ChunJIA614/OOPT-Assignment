import java.io.*;
import java.util.*;

//junkang lanjiao 18cm

public class Customer {
    private String customerID;
    private String name;
    private String address;
    private String contactNo;

    private static List<Customer> customerList = new ArrayList<>();

    // Getters and setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    // Method to add a customer
    public static void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        String customerID;

        System.out.println("");
        System.out.println("\n--- Add New Customer ---");
        // Ensure the customer ID is unique
        while (true) {
            System.out.print("Enter Customer ID: ");
            customerID = scanner.nextLine();
            if (findCustomerByID(customerID) != null) {
                System.out.println("This Customer ID already exists. Please enter a unique ID.");
            } else {
                break;
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Contact No: ");
        String contactNo = scanner.nextLine();

        Customer newCustomer = new Customer();
        newCustomer.setCustomerID(customerID);
        newCustomer.setName(name);
        newCustomer.setAddress(address);
        newCustomer.setContactNo(contactNo);

        customerList.add(newCustomer);

        // Save to file
        appendCustomerToFile(newCustomer, "customers.txt");

        System.out.println("Customer added successfully.");
        returnToMainMenu();
    }

    private static void appendCustomerToFile(Customer customer, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getAddress() + ","
                    + customer.getContactNo());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update a customer
    public static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = null;

        System.out.println("");
        System.out.println("\n--- Update Customer ---");
        // Loop until a valid customer is found
        while (customer == null) {
            System.out.print("Enter Customer ID of the customer to update: ");
            String customerID = scanner.nextLine();
            customer = findCustomerByID(customerID);

            if (customer == null) {
                System.out.println("No customer found with the given ID. Please try again.");
            }
        }

        System.out.println("Current information for customer: " + customer.getName());
        System.out.println("ID: " + customer.getCustomerID());
        System.out.println("Name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Contact No: " + customer.getContactNo());

        boolean continueUpdating = true;
        while (continueUpdating) {
            System.out.println("\nSelect the information to update:");
            System.out.println("[1] Update Name");
            System.out.println("[2] Update Address");
            System.out.println("[3] Update Contact No");
            System.out.println("[4] Done");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    customer.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new Address: ");
                    String newAddress = scanner.nextLine();
                    customer.setAddress(newAddress);
                    break;
                case 3:
                    System.out.print("Enter new Contact No: ");
                    String newContactNo = scanner.nextLine();
                    customer.setContactNo(newContactNo);
                    break;
                case 4:
                    continueUpdating = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Updated information for customer: " + customer.getName());
        System.out.println("ID: " + customer.getCustomerID());
        System.out.println("Name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Contact No: " + customer.getContactNo());

        // Save updated customer list to file
        updateCustomerInFile(customer, "customers.txt");
        System.out.println("Customer information updated successfully.");
        returnToMainMenu();
    }

    private static void updateCustomerInFile(Customer updatedCustomer, String fileName) {
        List<Customer> updatedCustomerList = new ArrayList<>();

        // Read the file and update the customer information
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] customerData = line.split(",");
                if (customerData[0].equals(updatedCustomer.getCustomerID())) {
                    updatedCustomerList.add(updatedCustomer); // Add the updated customer
                } else {
                    Customer customer = new Customer();
                    customer.setCustomerID(customerData[0]);
                    customer.setName(customerData[1]);
                    customer.setAddress(customerData[2]);
                    customer.setContactNo(customerData[3]);
                    updatedCustomerList.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated list back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : updatedCustomerList) {
                writer.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getAddress() + ","
                        + customer.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to view all customers
    public static void viewCustomers() {
        System.out.println("");
        System.out.println("\n--- View All Customers ---");
        try (Scanner fileScanner = new Scanner(new File("customers.txt"))) {
            System.out.printf("%-20s%-20s%-30s%-20s\n","CustomerID","Name","Address","Contact No");
            System.out.println("----------------------------------------------------------------------------------------");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] customerData = line.split(",");
                System.out.printf("%-20s%-20s%-30s%-20s\n",customerData[0],customerData[1],customerData[2],customerData[3]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No customers found. Please add customers first.");
        }
        returnToMainMenu();
    }

    // Method to search a customer by ID
    public static void searchCustomerByID() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Search Customer ---");
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();

        Customer customer = findCustomerByID(customerID);
        if (customer != null) {
            System.out.println("Customer found:");
            System.out.println("ID: " + customer.getCustomerID());
            System.out.println("Name: " + customer.getName());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("Contact No: " + customer.getContactNo());
        } else {
            System.out.println("No customer found with the given ID.");
        }
        returnToMainMenu();
    }

    private static Customer findCustomerByID(String customerID) {
        for (Customer customer : customerList) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    // Method to remove a customer
    public static void removeCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("\n--- Remove Customer ---");
        System.out.print("Enter Customer ID of the customer to remove: ");
        String customerID = scanner.nextLine();

        Customer customer = findCustomerByID(customerID);
        if (customer != null) {
            System.out.print("Are you sure you want to remove this customer? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                customerList.remove(customer);
                removeCustomerFromFile(customerID, "customers.txt");
                System.out.println("Customer removed successfully.");
            } else {
                System.out.println("Customer removal canceled.");
            }
        } else {
            System.out.println("No customer found with the given ID.");
        }
        returnToMainMenu();
    }

    private static void removeCustomerFromFile(String customerID, String fileName) {
        List<Customer> updatedCustomerList = new ArrayList<>();

        // Read the file and remove the customer information
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] customerData = line.split(",");
                if (!customerData[0].equals(customerID)) {
                    Customer customer = new Customer();
                    customer.setCustomerID(customerData[0]);
                    customer.setName(customerData[1]);
                    customer.setAddress(customerData[2]);
                    customer.setContactNo(customerData[3]);
                    updatedCustomerList.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated list back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : updatedCustomerList) {
                writer.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getAddress() + ","
                        + customer.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to return to the main menu
    private static void returnToMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to return to the main menu...");
        scanner.nextLine();
    }

    private static void loadCustomersFromFile(String fileName) {
        customerList.clear(); // Clear the existing list
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] customerData = line.split(",");
                Customer customer = new Customer();
                customer.setCustomerID(customerData[0]);
                customer.setName(customerData[1]);
                customer.setAddress(customerData[2]);
                customer.setContactNo(customerData[3]);
                customerList.add(customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No customers found. Please add customers first.");
        }
    }
    
    // Main menu page for Customer operations
    public static void customerMainPage() {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;
        loadCustomersFromFile("customers.txt");

        while (continueProgram) {
            System.out.println("");
            System.out.println("\n--- Customer Management System ---");
            System.out.println("[1] Add New Customer");
            System.out.println("[2] Update Existing Customer");
            System.out.println("[3] View All Customers");
            System.out.println("[4] Search Customer by ID");
            System.out.println("[5] Remove Customer");
            System.out.println("[6] Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    viewCustomers();
                    break;
                case 4:
                    searchCustomerByID();
                    break;
                case 5:
                    removeCustomer();
                    break;
                case 6:
                    continueProgram = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        scanner.close();
    }

    // Main method to run the program
    public static void main(String[] args) {
        customerMainPage();
    }
}
