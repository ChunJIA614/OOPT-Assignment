import java.util.*;
import java.io.*;

public class Employee {
    // Variable declaration
    private String employeeID;
    private String name;
    private String position;
    private String contactNo;

    // List to store employees
    private static List<Employee> employeeList = new ArrayList<>();

    // Setter and Getter methods
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    // Methods for the employee management system

    // Main menu method
    public static void employeeMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("");
            System.out.println("Employee Management System");
            System.out.println("[1] Add New Employee");
            System.out.println("[2] Update Employee Information");
            System.out.println("[3] Remove Employee");
            System.out.println("[4] View All Employees");
            System.out.println("[5] Search Employee");
            System.out.println("[6] Exit");
            System.out.print("Enter your choice: ");
            int selection = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (selection) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    viewAllEmployees();
                    break;
                case 5:
                    searchEmployee();
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid Choice, Please Enter again.");
            }
        }
    }

    // Method to add a new employee
    public static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        String employeeID;

        System.out.println("");
        System.out.println("=> Add New Employee");
        // Ensure the employee ID is unique
        while (true) {
            System.out.print("Enter Employee ID: ");
            employeeID = scanner.nextLine();
            if (findEmployeeByID(employeeID) != null) {
                System.out.println("This Employee ID already exists. Please enter a unique ID.");
            } else {
                break;
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Position: ");
        String position = scanner.nextLine();

        System.out.print("Enter Contact No: ");
        String contactNo = scanner.nextLine();

        Employee newEmployee = new Employee();
        newEmployee.setEmployeeID(employeeID);
        newEmployee.setName(name);
        newEmployee.setPosition(position);
        newEmployee.setContactNo(contactNo);

        employeeList.add(newEmployee);

        // Save to file
        appendEmployeeToFile(newEmployee, "employees.txt");
        System.out.println("Employee added successfully.");
        returnToMainMenu();
    }

    // Method to append a new employee to the file
    private static void appendEmployeeToFile(Employee employee, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getPosition() + "," + employee.getContactNo());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update an employee
    public static void updateEmployee() {
        Scanner scanner = new Scanner(System.in);
        Employee employee = null;

        System.out.println("");
        System.out.println("=> Update Employee Information");
        // Loop until a valid employee is found
        while (employee == null) {
            System.out.print("Enter Employee ID of the employee to update: ");
            String employeeID = scanner.nextLine();
            employee = findEmployeeByID(employeeID);

            if (employee == null) {
                System.out.println("No employee found with the given ID. Please try again.");
            } else {
                // Display current employee details
                System.out.println("Current details of the employee:");
                displayEmployeeDetails(employee);
            }
        }

        boolean continueUpdating = true;
        while (continueUpdating) {
            System.out.println("Select the information to update:");
            System.out.println("[1] Update Name");
            System.out.println("[2] Update Position");
            System.out.println("[3] Update Contact No");
            System.out.println("[4] Done");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    employee.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new Position: ");
                    String newPosition = scanner.nextLine();
                    employee.setPosition(newPosition);
                    break;
                case 3:
                    System.out.print("Enter new Contact No: ");
                    String newContactNo = scanner.nextLine();
                    employee.setContactNo(newContactNo);
                    break;
                case 4:
                    continueUpdating = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Display updated employee details
        System.out.println("Updated details of the employee:");
        displayEmployeeDetails(employee);

        // Save updated employee list to file
        updateEmployeeInFile(employee, "employees.txt");
        System.out.println("Employee information updated successfully.");

        returnToMainMenu();
    }

    // Method to display employee details
    private static void displayEmployeeDetails(Employee employee) {
        System.out.println("");
        System.out.println("Employee ID: " + employee.getEmployeeID());
        System.out.println("Name: " + employee.getName());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Contact No: " + employee.getContactNo());
        System.out.println("");
    }

    // Method to update the employee in the file
    private static void updateEmployeeInFile(Employee updatedEmployee, String fileName) {
        List<Employee> updatedEmployeeList = new ArrayList<>();

        // Read the file and update the employee information
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] employeeData = line.split(",");
                if (employeeData[0].equals(updatedEmployee.getEmployeeID())) {
                    updatedEmployeeList.add(updatedEmployee); // Add the updated employee
                } else {
                    Employee employee = new Employee();
                    employee.setEmployeeID(employeeData[0]);
                    employee.setName(employeeData[1]);
                    employee.setPosition(employeeData[2]);
                    employee.setContactNo(employeeData[3]);
                    updatedEmployeeList.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated employee list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee employee : updatedEmployeeList) {
                writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getPosition() + "," + employee.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to find an employee by ID
    private static Employee findEmployeeByID(String employeeID) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeID().equals(employeeID)) {
                return employee;
            }
        }
        return null;
    }

    // Method to remove an employee
    public static void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        Employee employee = null;

        System.out.println("");
        System.out.println("=> Remove Employee");
        // Loop until a valid employee is found
        while (employee == null) {
            System.out.print("Enter Employee ID of the employee to remove: ");
            String employeeID = scanner.nextLine();
            employee = findEmployeeByID(employeeID);

            if (employee == null) {
                System.out.println("No employee found with the given ID. Please try again.");
            } else {
                // Display current employee details
                System.out.println("Current details of the employee:");
                displayEmployeeDetails(employee);
            }
        }

        // Confirmation for deletion
        System.out.print("Are you sure you want to remove this employee? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            employeeList.remove(employee); // Remove employee from the list
            updateFileAfterRemoval("employees.txt"); // Update the file to reflect the removal
            System.out.println("Employee has been removed.");
        } else {
            System.out.println("Removal canceled.");
        }

        returnToMainMenu();
    }

    // Method to update the file after removing an employee
    private static void updateFileAfterRemoval(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee employee : employeeList) {
                writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getPosition() + "," + employee.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to view all employees
    public static void viewAllEmployees() {
        try (Scanner fileScanner = new Scanner(new File("employees.txt"))) {
            System.out.println("");
            System.out.println("=> View All Employees");
            System.out.printf("%-15s%-20s%-20s%-15s\n", "Employee ID", "Name", "Position", "Contact No");
            System.out.println("--------------------------------------------------------------------------");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] employeeData = line.split(",");
                System.out.printf("%-15s%-20s%-20s%-15s\n", employeeData[0], employeeData[1], employeeData[2], employeeData[3]);
            }
            returnToMainMenu();
        } catch (FileNotFoundException e) {
            System.out.println("No employees found. Please add employees first.");
        }
    }

    // Method to search for an employee
    public static void searchEmployee() {
        Scanner scanner = new Scanner(System.in);
        Employee employee = null;

        System.out.println("");
        System.out.println("=> Search Employee");
        // Loop until a valid employee is found
        while (employee == null) {
            System.out.print("Enter Employee ID of the employee to search: ");
            String employeeID = scanner.nextLine();
            employee = findEmployeeByID(employeeID);

            if (employee == null) {
                System.out.println("No employee found with the given ID. Please try again.");
            } else {
                // Display current employee details
                System.out.println("Current details of the employee:");
                displayEmployeeDetails(employee);
            }
        }
        returnToMainMenu();
    }

    // Method to return to the main menu
    private static void returnToMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to return to the main menu...");
        scanner.nextLine();
    }

    // Main program entry point
    public static void main(String[] args) {
        loadEmployeesFromFile("employees.txt");
        employeeMenu();
    }

    // Method to load employees from file into employeeList
    private static void loadEmployeesFromFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] employeeData = line.split(",");
                Employee employee = new Employee();
                employee.setEmployeeID(employeeData[0]);
                employee.setName(employeeData[1]);
                employee.setPosition(employeeData[2]);
                employee.setContactNo(employeeData[3]);
                employeeList.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 
