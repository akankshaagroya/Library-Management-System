package src.main;

import src.models.Library;
import src.gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new src.models.Book("1984", "George Orwell", 3));
        library.addBook(new src.models.Book("To Kill a Mockingbird", "Harper Lee", 2));

        LibraryGUI gui = new LibraryGUI(library);
        gui.setVisible(true);
    }
}
