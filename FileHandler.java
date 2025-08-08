import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String BOOKS_FILE = "all_books.txt";

    public static void saveBooks(List<Book> books) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            // Write header
            writer.write(String.format("%-4s | %-30s | %-20s | %-12s | %s",
                "ID", "Title", "Author", "Type", "Status"));
            writer.newLine();
            writer.write("----------------------------------------------------------------------------");
            writer.newLine();
            
            // Write data
            for (Book book : books) {
                writer.write(String.format("%-4d | %-30s | %-20s | %-12s | %s",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getType(),
                    book.isBorrowed() ? "BORROWED" : "AVAILABLE"));
                writer.newLine();
            }
        }
    }

    public static List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            // Skip header lines
            reader.readLine();
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length < 5) continue;
                    
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    String type = parts[3].trim();
                    boolean isBorrowed = parts[4].trim().equals("BORROWED");
                    
                    Book book = type.equals("Fiction") 
                        ? new FictionBook(title, author)
                        : new NonFictionBook(title, author);
                    
                    book.setBorrowed(isBorrowed);
                    books.add(book);
                    
                    // Update nextId to maintain sequential IDs
                    if (Book.nextId <= id) {
                        Book.nextId = id + 1;
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                }
            }
        }
        return books;
    }
}