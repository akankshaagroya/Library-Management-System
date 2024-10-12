package src.models;

import java.io.Serializable;

public class Book implements Serializable {
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

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void issue() {
        if (quantityAvailable > 0) {
            quantityAvailable--;
        } else {
            System.out.println("No copies available!");
        }
    }

    public void returnBook() {
        quantityAvailable++;
    }
}
