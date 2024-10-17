package src.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int id;
    private List<Book> issuedBooks;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.issuedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    @Override
    public String toString() {
        return "User: " + name + " (ID: " + id + ")";
    }
}
