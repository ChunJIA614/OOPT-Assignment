//done by Chun Jia 29/7/2024 11:02AM
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {

    //-----------------------------variable declaration---------------------------------------------//
    private String employeeID;
    private String password;
  
    //-----------------------------setter and getter-----------------------------------------------//
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //---------------------------------method-----------------------------------------------------//
    public static void loginPage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.println("=> Login Page");
        System.out.print("Enter Employee ID: ");
        String employeeID = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Read employee data from file and validate
        boolean loginSuccessful = false;
        try (Scanner fileScanner = new Scanner(new File("employeesE0.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] employeeData = line.split(",");
                if (employeeData.length < 5) {
                    continue; // Skip lines with insufficient data
                }

                String storedEmployeeID = employeeData[0];
                String storedPassword = employeeData[4];

                if (storedEmployeeID.equals(employeeID) && storedPassword.equals(password)) {
                    loginSuccessful = true;
                    break;
                }
            }

            if (loginSuccessful) {
                System.out.println("Login successful. Welcome!");
            } else {
                System.out.println("Invalid Employee ID or Password. Please try again.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Employee data file not found.");
        }

        scanner.close();
    }

    //------------------------------main program run here-----------------------------------------//
    public static void main(String[] args) {
        loginPage();
    }
}
