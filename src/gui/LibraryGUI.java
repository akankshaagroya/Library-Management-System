package src.gui;

import src.models.Book;
import src.models.Library;
import src.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    private Library library;

    public LibraryGUI(Library library) {
        this.library = library;

        // Set up the GUI
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addUserButton = new JButton("Add User");
        JButton issueBookButton = new JButton("Issue Book");
        JButton viewBooksButton = new JButton("View Books");

        add(addUserButton);
        add(issueBookButton);
        add(viewBooksButton);

        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter User Name:");
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                library.addUser(new User(name, id));
                JOptionPane.showMessageDialog(null, "User added successfully!");
            }
        });

        issueBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Issue:");
                for (Book book : library.getBooks()) {
                    if (book.getTitle().equals(title) && book.getQuantityAvailable() > 0) {
                        book.issue();
                        JOptionPane.showMessageDialog(null, "Book issued successfully!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Book not available!");
            }
        });

        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder booksList = new StringBuilder();
                for (Book book : library.getBooks()) {
                    booksList.append(book.getTitle()).append(" - ")
                             .append(book.getQuantityAvailable()).append(" copies available\n");
                }
                JOptionPane.showMessageDialog(null, booksList.toString());
            }
        });
    }
}
