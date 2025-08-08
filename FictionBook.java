public class FictionBook extends Book {
    public FictionBook(String title, String author) {
        super(title, author);
    }

    @Override
    public String getType() {
        return "Fiction";
    }
}