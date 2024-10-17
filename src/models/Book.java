package src.models;

public class Book {
    private String title;
    private String author;
    private int quantityAvailable;

    public Book(String title, String author, int quantityAvailable) {
        this.title = title;
        this.author = author;
        this.quantityAvailable = quantityAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void issue() {
        if (quantityAvailable > 0) {
            quantityAvailable--;
        }
    }

    public void returnBook() {
        quantityAvailable++;
    }

    @Override
    public String toString() {
        return title + " by " + author + " - " + quantityAvailable + " copies available";
    }
}
