
package src.gui;

import src.models.Book;
import src.models.Library;
import src.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    public LibraryGUI(Library library) {
        // Set up the GUI
        setTitle("Library Management System");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load and scale background image
        ImageIcon backgroundImage = new ImageIcon("./images/pexels-technobulka-2908984.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(new BorderLayout());
        add(background);

        // Add a component listener to dynamically resize the background if the window size changes
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Image scaledResizedImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                background.setIcon(new ImageIcon(scaledResizedImage));
            }
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 3)); // 6 rows, 1 column, vertical spacing
        buttonPanel.setOpaque(false); // Make the panel transparent

        // Create buttons
        JButton addUserButton = new JButton("Add User");
        JButton addBookButton = new JButton("Add Book");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton viewUsersButton = new JButton("View Users");

        // Add buttons to the panel
        buttonPanel.add(addUserButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(issueBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(viewBooksButton);
        buttonPanel.add(viewUsersButton);

        // Add button panel to the left side
        background.add(buttonPanel, BorderLayout.WEST);

        // Action listener for adding a user
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter User Name:");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User name cannot be empty!");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                    
                    // Check for unique user ID
                    if (library.getUserById(id) != null) {
                        JOptionPane.showMessageDialog(null, "User ID already exists! Please enter a unique ID.");
                        return;
                    }
                    
                    library.addUser(new User(name, id));
                    JOptionPane.showMessageDialog(null, "User added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a number.");
                }
            }
        });

        // Action listener for adding a book
        addBookButton.addActionListener(new ActionListener() {
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

        // Action listener for issuing a book
        issueBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Book Title to Issue:");
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Book title cannot be empty!");
                    return;
                }
                int userId;
                try {
                    userId = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
                    
                    // Check if the user exists
                    User user = library.getUserById(userId);
                    if (user == null) {
                        JOptionPane.showMessageDialog(null, "User not found!");
                        return;
                    }
                    
                    // Check if the book is available
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equalsIgnoreCase(title) && book.getQuantityAvailable() > 0) {
                            book.issue(); // Decrease the available quantity
                            user.issueBook(book); // Link the book to the user
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

        // Action listener for returning a book
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

                    // Check if the user exists
                    User user = library.getUserById(userId);
                    if (user == null) {
                        JOptionPane.showMessageDialog(null, "User not found!");
                        return;
                    }

                    // Check if the user has issued this book
                    Book bookToReturn = null;
                    for (Book book : user.getIssuedBooks()) {
                        if (book.getTitle().equalsIgnoreCase(title)) {
                            bookToReturn = book;
                            break;
                        }
                    }

                    if (bookToReturn != null) {
                        bookToReturn.returnBook(); // Increase the available quantity
                        user.getIssuedBooks().remove(bookToReturn); // Remove the book from the user's issued list
                        JOptionPane.showMessageDialog(null, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "This book was not issued to the user.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid User ID! Please enter a number.");
                }
            }
        });

        // Action listener for viewing books
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder booksList = new StringBuilder();
                for (Book book : library.getBooks()) {
                    booksList.append(book.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, booksList.length() > 0 ? booksList.toString() : "No books available!");
            }
        });

        // Action listener for viewing users and their issued books
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