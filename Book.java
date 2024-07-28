import java.io.*;
import java.util.*;

public class Book {
    // Datatype declaration
    private String ISBN;
    private String title;
    private double price;
    private int stockQuantity;
    private String author;
    private String publisher;

    // List to store books
    private static List<Book> bookList = new ArrayList<>();

    //----------------------------------------------------------Getters and setters-------------------------------------//
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    //-----------------------------------------------------------------METHOD---------------------------------------------------//
    // Method to display main menu
    public static void bookMainPage() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("Book Arrangement System");
            System.out.println("[1] Add New Book");
            System.out.println("[2] Update Existing Book Information");
            System.out.println("[3] View Book List");
            System.out.println("[4] Delete Book");
            System.out.println("[5] Search Book");
            System.out.println("[6] Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    viewBook();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5: 
                    searchBook();
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid Choice, Please Enter again.");
            }
        }
    }

    // Method to add a book
    public static void addBook() {
        Scanner scanner = new Scanner(System.in);
        String ISBN;

        System.out.println("");
        System.out.println("=>Add New Book");
        // Ensure the ISBN is unique
        while (true) {
            System.out.print("Enter ISBN (scan or type): ");
            ISBN = scanner.nextLine();
            if (findBookByISBN(ISBN) != null) {
                System.out.println("This ISBN already exists. Please enter a unique ISBN.");
            } else {
                break;
            }
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume the newline

        System.out.print("Enter Stock Quantity: ");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();

        Book newBook = new Book();
        newBook.setISBN(ISBN);
        newBook.setTitle(title);
        newBook.setPrice(price);
        newBook.setStockQuantity(stockQuantity);
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);

        bookList.add(newBook);

        // Save to file
        appendBookToFile(newBook, "books.txt");
        System.out.println("Book added successfully.");
        returnToMainMenu();
    }

    private static void appendBookToFile(Book book, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(book.getISBN() + "," + book.getTitle() + "," + book.getPrice() + ","
                    + book.getStockQuantity() + "," + book.getAuthor() + "," + book.getPublisher());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update a book
    public static void updateBook() {
        Scanner scanner = new Scanner(System.in);
        Book book = null;
    
        System.out.println("");
        System.out.println("=> Update Existing Book Information");
        // Loop until a valid book is found
        while (book == null) {
            System.out.print("Enter ISBN of the book to update: ");
            String ISBN = scanner.nextLine();
            book = findBookByISBN(ISBN);
    
            if (book == null) {
                System.out.println("No book found with the given ISBN. Please try again.");
            } else {
                // Display current book details
                System.out.println("Current details of the book:");
                displayBookDetails(book);
            }
        }
    
        boolean continueUpdating = true;
        while (continueUpdating) {
            System.out.println("Select the information to update:");
            System.out.println("[1] Update Title");
            System.out.println("[2] Update Price");
            System.out.println("[3] Update Stock Quantity");
            System.out.println("[4] Update Author");
            System.out.println("[5] Update Publisher");
            System.out.println("[6] Done");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter new Title: ");
                    String newTitle = scanner.nextLine();
                    book.setTitle(newTitle);
                    break;
                case 2:
                    System.out.print("Enter new Price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline
                    book.setPrice(newPrice);
                    break;
                case 3:
                    System.out.print("Enter new Stock Quantity: ");
                    int newStockQuantity = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    book.setStockQuantity(newStockQuantity);
                    break;
                case 4:
                    System.out.print("Enter new Author: ");
                    String newAuthor = scanner.nextLine();
                    book.setAuthor(newAuthor);
                    break;
                case 5:
                    System.out.print("Enter new Publisher: ");
                    String newPublisher = scanner.nextLine();
                    book.setPublisher(newPublisher);
                    break;
                case 6:
                    continueUpdating = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    
        // Display updated book details
        System.out.println("Updated details of the book:");
        displayBookDetails(book);
    
        // Save updated book list to file
        updateBookInFile(book, "books.txt");
        System.out.println("Book information updated successfully.");
    
        returnToMainMenu();
    }
    
    // Method to display book details
    private static void displayBookDetails(Book book) {
        System.out.println("");
        System.out.println("ISBN: " + book.getISBN());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Price: " + book.getPrice());
        System.out.println("Stock Quantity: " + book.getStockQuantity());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publisher: " + book.getPublisher());
        System.out.println("");
        
    }

    private static void updateBookInFile(Book updatedBook, String fileName) {
        List<Book> updatedBookList = new ArrayList<>();

        // Read the file and update the book information
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] bookData = line.split(",");
                if (bookData[0].equals(updatedBook.getISBN())) {
                    updatedBookList.add(updatedBook); // Add the updated book
                } else {
                    Book book = new Book();
                    book.setISBN(bookData[0]);
                    book.setTitle(bookData[1]);
                    book.setPrice(Double.parseDouble(bookData[2]));
                    book.setStockQuantity(Integer.parseInt(bookData[3]));
                    book.setAuthor(bookData[4]);
                    book.setPublisher(bookData[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated book list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : updatedBookList) {
                writer.write(book.getISBN() + "," + book.getTitle() + "," + book.getPrice() + ","
                        + book.getStockQuantity() + "," + book.getAuthor() + "," + book.getPublisher());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to find a book by ISBN
    private static Book findBookByISBN(String ISBN) {
        for (Book book : bookList) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    // Method to return to the main menu
    private static void returnToMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to return to the main menu...");
        scanner.nextLine();
    }

     // Method to load books from file into bookList
     private static void loadBooksFromFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] bookData = line.split(",");
                Book book = new Book();
                book.setISBN(bookData[0]);
                book.setTitle(bookData[1]);
                book.setPrice(Double.parseDouble(bookData[2]));
                book.setStockQuantity(Integer.parseInt(bookData[3]));
                book.setAuthor(bookData[4]);
                book.setPublisher(bookData[5]);
                bookList.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // Method to view all books
    public static void viewBook() {
        try (Scanner fileScanner = new Scanner(new File("books.txt"))) {
            System.out.println("");
            System.out.println("=> View Book");
            System.out.printf("%-10s%-30s%-10s%-20s%-15s%-10s\n","ISBN","Title","Price","Stock Quantity","Author","Publisher");
            System.out.println("---------------------------------------------------------------------------------------------------");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] bookData = line.split(",");
                System.out.printf("%-10s%-30s%-10s%-20s%-15s%-10s\n",bookData[0],bookData[1],bookData[2],bookData[3],bookData[4],bookData[5]);
            }
            returnToMainMenu();
        } catch (FileNotFoundException e) {
            System.out.println("No books found. Please add books first.");
        }
    }

    // Method to remove a book
public static void removeBook() {
    Scanner scanner = new Scanner(System.in);
    Book book = null;

    System.out.println("");
    System.out.println("=> Delete Book");
    // Loop until a valid book is found
    while (book == null) {
        System.out.print("Enter ISBN of the book to delete: ");
        String ISBN = scanner.nextLine();
        book = findBookByISBN(ISBN);

        if (book == null) {
            System.out.println("No book found with the given ISBN. Please try again.");
        } else {
            // Display current book details
            System.out.println("Current details of the book:");
            displayBookDetails(book);
        }
    }

    // Confirmation for deletion
    System.out.print("Are you sure you want to delete this book? (Y/N): ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("Y")) {
        bookList.remove(book); // Remove book from the list
        updateFileAfterRemoval("books.txt"); // Update the file to reflect the removal
        System.out.println("Book has been deleted.");
    } else {
        System.out.println("Deletion canceled.");
    }

    returnToMainMenu();
}

// Method to update the file after removing a book
private static void updateFileAfterRemoval(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Book book : bookList) {
            writer.write(book.getISBN() + "," + book.getTitle() + "," + book.getPrice() + ","
                    + book.getStockQuantity() + "," + book.getAuthor() + "," + book.getPublisher());
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// method for searching a book
public static void searchBook(){
    Scanner scanner = new Scanner(System.in);
    Book book = null;

    System.out.println("");
    System.out.println("=> Search Book");
    // Loop until a valid book is found
    while (book == null) {
        System.out.print("Enter ISBN of the book to search: ");
        String ISBN = scanner.nextLine();
        book = findBookByISBN(ISBN);

        if (book == null) {
            System.out.println("No book found with the given ISBN. Please try again.");
        } else {
            // Display current book details
            System.out.println("Current details of the book:");
            displayBookDetails(book);
        }
    }
    returnToMainMenu();
}



    // ---------------------------------------------Main program entry point-------------------------------------//
    public static void main(String[] args) {
        loadBooksFromFile("books.txt");
        bookMainPage();
    }
}
