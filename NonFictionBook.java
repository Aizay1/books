public class NonFictionBook extends Book {
    public NonFictionBook(String title, String author) {
        super(title, author);
    }

    @Override
    public String getType() {
        return "Non-Fiction";
    }
}