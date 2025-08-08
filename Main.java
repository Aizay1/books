import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load data
        try {
            List<Book> loadedBooks = FileHandler.loadBooks();
            for (Book book : loadedBooks) {
                library.addBook(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Borrowed Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Edit Book");
            System.out.println("7. Delete Book");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> displayBooks(library.getBooks());
                    case 3 -> displayBooks(library.getBorrowedBooks());
                    case 4 -> borrowBook();
                    case 5 -> returnBook();
                    case 6 -> editBook();
                    case 7 -> deleteBook();
                    case 8 -> exitProgram();
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Is it Fiction? (y/n): ");
        String isFiction = scanner.nextLine();

        Book book = isFiction.equalsIgnoreCase("y")
            ? new FictionBook(title, author)
            : new NonFictionBook(title, author);
        
        library.addBook(book);
        try {
            FileHandler.saveBooks(library.getBooks());
            System.out.println("Book added successfully! ID: " + book.getId());
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private static void displayBooks(List<Book> books) {
        System.out.printf("\n%-4s | %-30s | %-20s | %-12s | %s%n",
            "ID", "Title", "Author", "Type", "Status");
        System.out.println("----------------------------------------------------------------------------");
        
        if (books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        for (Book book : books) {
            System.out.printf("%-4d | %-30s | %-20s | %-12s | %s%n",
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getType(),
                book.isBorrowed() ? "BORROWED" : "AVAILABLE");
        }
    }

    private static void borrowBook() throws BookNotFoundException, IOException {
        System.out.print("Enter book ID to borrow: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        library.borrowBook(bookId);
        FileHandler.saveBooks(library.getBooks());
        System.out.println("Book borrowed successfully!");
    }

    private static void returnBook() throws BookNotFoundException, IOException {
        System.out.print("Enter book ID to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        library.returnBook(bookId);
        FileHandler.saveBooks(library.getBooks());
        System.out.println("Book returned successfully!");
    }

    private static void editBook() throws BookNotFoundException, IOException {
        System.out.print("Enter book ID to edit: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new type (Fiction/Non-Fiction): ");
        String type = scanner.nextLine();
        
        library.updateBook(bookId, title, author, type);
        FileHandler.saveBooks(library.getBooks());
        System.out.println("Book updated successfully!");
    }

    private static void deleteBook() throws BookNotFoundException, IOException {
        System.out.print("Enter book ID to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        library.deleteBook(bookId);
        FileHandler.saveBooks(library.getBooks());
        System.out.println("Book deleted successfully!");
    }

    private static void exitProgram() {
        try {
            FileHandler.saveBooks(library.getBooks());
            System.out.println("Data saved. Exiting...");
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
        System.exit(0);
    }
}