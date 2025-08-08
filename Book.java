public abstract class Book {
    public static int nextId = 1;
    private final int id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String title, String author) {
        this.id = nextId++;
        this.title = capitalize(title);
        this.author = capitalize(author);
        this.isBorrowed = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }
    public void setTitle(String title) { this.title = capitalize(title); }
    public void setAuthor(String author) { this.author = capitalize(author); }

    public abstract String getType();

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}