public interface LibraryOperations {
    void borrowBook(int bookId) throws BookNotFoundException;
    void returnBook(int bookId) throws BookNotFoundException;
    void deleteBook(int bookId) throws BookNotFoundException;
    void updateBook(int bookId, String title, String author, String type) throws BookNotFoundException;
}