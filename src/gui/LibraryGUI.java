package src.gui;

import src.models.Library;
import src.models.Book;
import src.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    public LibraryGUI(Library library) {
        setTitle("Library Management System");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("./images/pexels-technobulka-2908984.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(new BorderLayout());
        add(background);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Image scaledResizedImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                background.setIcon(new ImageIcon(scaledResizedImage));
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 3));
        buttonPanel.setOpaque(false);

        JButton addUserButton = new JButton("Add User");
        JButton addBookButton = new JButton("Add Book");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton viewUsersButton = new JButton("View Users");

        buttonPanel.add(addUserButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(issueBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(viewBooksButton);
        buttonPanel.add(viewUsersButton);

        background.add(buttonPanel, BorderLayout.WEST);

        addUserButton.addActionListener(new ActionListener() {//button handling when user is added
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter User Name:");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User name cannot be empty!");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                    if (library.getUserById(id) != null) {
                        JOptionPane.showMessageDialog(null, "User ID already exists! Please enter a unique ID.");
                        return;
                    }
                    library.addUser(new User(name, id));
                    JOptionPane.showMessageDialog(null, "User added successfully!");
                } catch (NumberFormatException ex) { //handles if string is added in userID
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a number.");
                }
            }
        });

        addBookButton.addActionListener(new ActionListener() {//button handling book adding
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title:");
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Book title cannot be empty!");
                    return;
                }
                String author = JOptionPane.showInputDialog("Enter Author Name:");
                if (author == null || author.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Author name cannot be empty!");
                    return;
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter Quantity:"));
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Quantity must be a positive number.");
                        return;
                    }
                    library.addBook(new Book(title, author, quantity));
                    JOptionPane.showMessageDialog(null, "Book added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity! Please enter a number.");
                }
            }
        });

        issueBookButton.addActionListener(new ActionListener() {//
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Issue:");
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Book title cannot be empty!");
                    return;
                }
                int userId;
                try {
                    userId = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                    User user = library.getUserById(userId);
                    if (user == null) {
                        JOptionPane.showMessageDialog(null, "User not found!");
                        return;
                    }
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equalsIgnoreCase(title) && book.getQuantityAvailable() > 0) {
                            book.issue();
                            user.issueBook(book);
                            JOptionPane.showMessageDialog(null, "Book issued successfully to " + user.getName() + "!");
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Book not available!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a number.");
                }
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Return:");
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Book title cannot be empty!");
                    return;
                }
                int userId;
                try {
                    userId = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                    User user = library.getUserById(userId);
                    if (user == null) {
                        JOptionPane.showMessageDialog(null, "User not found!");
                        return;
                    }
                    Book bookToReturn = null;
                    for (Book book : user.getIssuedBooks()) {
                        if (book.getTitle().equalsIgnoreCase(title)) {
                            bookToReturn = book;
                            break;
                        }
                    }
                    if (bookToReturn != null) {
                        bookToReturn.returnBook();
                        user.getIssuedBooks().remove(bookToReturn);
                        JOptionPane.showMessageDialog(null, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "This book was not issued to the user.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a number.");
                }
            }
        });

        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder booksList = new StringBuilder();
                for (Book book : library.getBooks()) {
                    booksList.append(book.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, booksList.length() > 0 ? booksList.toString() : "No books available!");
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder usersList = new StringBuilder();
                for (User user : library.getUsers()) {
                    usersList.append(user.toString()).append("\nIssued Books:\n");
                    for (Book book : user.getIssuedBooks()) {
                        usersList.append("  - ").append(book.getTitle()).append("\n");
                    }
                    usersList.append("\n");
                }
                JOptionPane.showMessageDialog(null, usersList.length() > 0 ? usersList.toString() : "No users found!");
            }
        });
    }
}
