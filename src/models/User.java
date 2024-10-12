package src.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private int id;
    private List<Book> issuedBooks;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.issuedBooks = new ArrayList<>();
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public String getName() {
        return name;
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }
}
