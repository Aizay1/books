import java.util.ArrayList;
import java.util.List;

public class Library implements LibraryOperations {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isBorrowed()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    @Override
    public void borrowBook(int bookId) throws BookNotFoundException {
        Book book = findBook(bookId);
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed!");
        }
        book.setBorrowed(true);
    }

    @Override
    public void returnBook(int bookId) throws BookNotFoundException {
        Book book = findBook(bookId);
        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book wasn't borrowed!");
        }
        book.setBorrowed(false);
    }

    @Override
    public void deleteBook(int bookId) throws BookNotFoundException {
        Book book = findBook(bookId);
        books.remove(book);
    }

    @Override
    public void updateBook(int bookId, String title, String author, String type) throws BookNotFoundException {
        Book book = findBook(bookId);
        book.setTitle(title);
        book.setAuthor(author);
        
        if ((type.equals("Fiction") && book instanceof NonFictionBook) ||
            (type.equals("Non-Fiction") && book instanceof FictionBook)) {
            books.remove(book);
            Book newBook = type.equals("Fiction") 
                ? new FictionBook(title, author) 
                : new NonFictionBook(title, author);
            newBook.setBorrowed(book.isBorrowed());
            books.add(newBook);
        }
    }

    private Book findBook(int bookId) throws BookNotFoundException {
        return books.stream()
            .filter(b -> b.getId() == bookId)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + bookId));
    }
}